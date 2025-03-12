import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { fetchProductDetails, updateCart } from "../../config/api";
import "./Product.scss";
import ImageBlock from "../../components/ImageBlock/ImageBlock";
import Button from "../../components/Button/Button";
import Counter from "../../components/Counter/Counter";
import PlaceholderImage from "./../../assets/placeholder.jpg";
import Ticker from "../../components/Ticker/Ticker";

// TODO add reviews of the product

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

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };

  const onClickHandler = () => {
    console.log("AR Implementation");
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
      toggleTicker(true, err.message);
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
        toggleTicker(true, err.message);
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
      <div className="product__images">
        <ImageBlock productImages={selectedImages} />
        <Button text="View in AR" onClickHandler={onClickHandler} />
      </div>
      <div className="product__details">
        <h1>{productDetails.name}</h1>
        <h3>
          {productDetails.currencyCode} {productDetails.price}
        </h3>
        <div className="product__details__section">
          Quantity:
          <Counter
            maxQuantity={productDetails.quantityAvailable}
            onChangeEventHandler={onCounterChange}
          />
        </div>
        <div className="product__details__section">
          {Object.entries(attributes).map(([key, value], index) => (
            <div className="product__details__section__container" key={index}>
              <div>{key}:</div>
              <div>{value}</div>
            </div>
          ))}
        </div>
        <div className="product__details__section">
          Description:
          <div className="product__details__section__text">
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
  );
}

export default Product;
