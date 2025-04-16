import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { fetchProductDetails, updateCart } from "../../config/api";
import "./Product.scss";
import ImageBlock from "../../components/ImageBlock/ImageBlock";
import Button from "../../components/Button/Button";
import Counter from "../../components/Counter/Counter";
import PlaceholderImage from "./../../assets/placeholder.jpg";
import Ticker from "../../components/Ticker/Ticker";
import FilledStar from "./../../assets/fill_star.png";
import Star from "./../../assets/star.png";
import Image from "./../../assets/image.png";

function Product() {
  const { productId } = useParams();
  const userId = sessionStorage.getItem("userId");
  const [productDetails, setProductDetails] = useState([]);
  const [selectedImages, setSelectedImages] = useState([
    PlaceholderImage,
    PlaceholderImage,
    PlaceholderImage,
  ]);
  const [attributes, setAttributes] = useState({});
  const [quantity, setQuanity] = useState(1);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };
  const onClickHandler = () => {
    navigate("/model/" + productId);
  };
  const onCounterChange = (value) => {
    setQuanity(value);
  };

  const addToCart = async () => {
    const requestBody = {
      userId: userId,
      productId: productDetails.productId,
      quantity: quantity,
      unitPrice: productDetails.price,
      currencyCode: productDetails.currencyCode,
    };
    try {
      // TODO set cart quantity in session storage so that cart icon can display that
      await updateCart(requestBody);
    } catch (err) {
      toggleTicker(true, err && err.message ? err.message : err);
    } finally {
      // setLoading(false);
    }
  };

  useEffect(() => {
    const loadDetails = async () => {
      try {
        const tempProductDetails = await fetchProductDetails(productId);
        setProductDetails(tempProductDetails.body[0]);
        setAttributes(tempProductDetails.body[0].attributes);
        if (tempProductDetails.body[0].images.length == 0) {
          return;
        }
        setSelectedImages(tempProductDetails.body[0].images);
      } catch (err) {
        toggleTicker(true, err && err.message ? err.message : err);
      } finally {
        // setLoading(false);
      }
    };

    loadDetails();
  }, []);

  return (
    <div className="product">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      <div className="product__container">
        <div className="product__container__images">
          <ImageBlock productImages={selectedImages} />
          <Button text="View in AR" onClickHandler={onClickHandler} />
        </div>
        <div className="product__container__details">
          <h1>{productDetails.name}</h1>
          <h3>
            {productDetails.currencyCode} {productDetails.price}
          </h3>
          <div className="product__container__details__section">
            Quantity:
            <Counter
              maxQuantity={productDetails.quantityAvailable}
              onChangeEventHandler={onCounterChange}
            />
          </div>
          <div className="product__container__details__section">
            {Object.entries(attributes).map(([key, value], index) => (
              <div
                className="product__container__details__section__container"
                key={index}
              >
                <div>{key}:</div>
                <div>{value}</div>
              </div>
            ))}
          </div>
          <div className="product__container__details__section">
            Description:
            <div className="product__container__details__section__text">
              {productDetails.description}
            </div>
          </div>
          <Button
            text="Add to Cart"
            onClickHandler={addToCart}
            width="50%"
            margin="0.5rem 0rem"
          />
        </div>
      </div>
      <div className="product__reviews">
        <div className="product__reviews__header">
          <h3>Product Reviews</h3>
        </div>
        <div className="product__reviews__container">
          {productDetails &&
            productDetails.reviews &&
            productDetails.reviews.map((review, index) => (
              <div key={index} className="product__reviews__container__review">
                <div className="product__reviews__container__review__rating">
                  {Array.from({ length: review.rating }, (_, i) => i + 1).map(
                    (num) => (
                      <img
                        src={FilledStar}
                        alt="Filled Star"
                        key={"fill-" + num}
                      />
                    )
                  )}
                  {Array.from(
                    { length: 5 - review.rating },
                    (_, i) => i + 1
                  ).map((num) => (
                    <img
                      src={Star}
                      alt="Unfilled Star"
                      key={"unfilled-" + num}
                    />
                  ))}
                </div>
                <div className="product__reviews__container__review__title">
                  {review.title}
                </div>
                <div>{review.comment}</div>
                {review.imageUrl && review.imageUrl.length > 0 ? (
                  <img
                    src={review.imageurl[0]}
                    className="product__reviews__container__review__footer"
                  />
                ) : (
                  <img
                    src={Image}
                    alt="Image"
                    className="product__reviews__container__review__footer"
                  />
                )}
              </div>
            ))}
        </div>
      </div>
    </div>
  );
}

export default Product;
