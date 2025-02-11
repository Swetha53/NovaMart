import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { fetchProductDetails } from "../../api";
import "./Product.scss";
import ImageBlock from "../../components/ImageBlock/ImageBlock";
import Button from "../../components/Button/Button";
import Counter from "../../components/Counter/Counter";
import PlaceholderImage from "./../../assets/placeholder.jpg";

function Product() {
  const { productId } = useParams();
  const [productDetails, setProductDetails] = useState([]);
  const [selectedImages, setSelectedImages] = useState([
    PlaceholderImage,
    PlaceholderImage,
    PlaceholderImage,
  ]);
  const [attributes, setAttributes] = useState({});
  const [quantity, setQuanity] = useState(0)

  const onClickHandler = () => {
    console.log("AR Implementation");
  };

  const onCounterChange = (value) => {
    setQuanity(value)
  };

  const addToCart = () => {
    console.log("Add to cart:" + quantity);
  }

  useEffect(() => {
    const loadDetails = async () => {
      try {
        const tempProductDetails = await fetchProductDetails(productId);
        setProductDetails(tempProductDetails[0]);
        setAttributes(tempProductDetails[0].attributes);
        if (tempProductDetails[0].images.length == 0) {
          return;
        }
        setSelectedImages(tempProductDetails[0].images);
      } catch (err) {
        // setError(err.message);
      } finally {
        // setLoading(false);
      }
    };

    loadDetails();
  }, []);

  return (
    <div className="product">
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
            <div className="product__details__section__container">
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
        <Button text="Add to Cart" onClickHandler={addToCart} width="50%" margin="0.5rem 0rem"/>
      </div>
    </div>
  );
}

export default Product;
