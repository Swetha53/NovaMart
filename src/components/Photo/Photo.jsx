import React, { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Photo.scss";
import Cart from "../../assets/cart.svg";
import { updateCart } from "../../config/api";

function Photo(props) {
  const { details } = props;
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();
  const productAvailability =
    details.quantityAvailable == 0
      ? "OUT_OF_STOCK"
      : details.quantityAvailable <= 10
      ? "LIMITED"
      : details.quantitySold + details.quantityReserved >=
        details.quantityAvailable
      ? "SELLING_FAST"
      : "IN_STOCK";
  const [index, setIndex] = useState(0);
  const intervalRef = useRef(null);

  const addToCart = async () => {
    const requestBody = {
      userId: userId,
      productId: details.productId,
      quantity: 1,
      unitPrice: details.price,
      currencyCode: details.currencyCode,
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
  const navigateToProduct = () => {
    navigate("product/" + details.productId);
  };
  const startChangingImages = () => {
    if (!intervalRef.current) {
      intervalRef.current = setInterval(() => {
        setIndex((prevIndex) => (prevIndex + 1) % details.images.length);
      }, 1000);
    }
  };
  const stopChangingImages = () => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
      setIndex(0);
    }
  };
  return (
    <div className="photo">
      {details.images && details.images.length && (
        <img
          onMouseEnter={startChangingImages}
          onMouseLeave={stopChangingImages}
          className="photo__image"
          src={details.images[index]}
          alt={details.name}
          onClick={navigateToProduct}
        />
      )}
      <div className="photo__details" onClick={navigateToProduct}>
        <h3>{details.name}</h3>
        <h2>
          {details.currencyCode} {details.price}
        </h2>
      </div>
      <div className="photo__footer">
        <img src={Cart} alt="Add to cart" onClick={addToCart} />
        <div className="photo__footer__availability">
          <div
            className={`photo__footer__availability__color ${
              productAvailability == "OUT_OF_STOCK"
                ? " photo__footer__availability__color-grey"
                : productAvailability == "LIMITED"
                ? "photo__footer__availability__color-red"
                : productAvailability == "SELLING_FAST"
                ? "photo__footer__availability__color-orange"
                : ""
            }`}
          ></div>
          <div className="photo__footer__availability__label">
            {productAvailability == "OUT_OF_STOCK"
              ? "Out Of Stock"
              : productAvailability == "LIMITED"
              ? "Only Limited Pieces Left"
              : productAvailability == "SELLING_FAST"
              ? "Selling Fast"
              : "In Stock"}
          </div>
        </div>
        <div className="photo__footer__review">
          Reviewed by {details.reviews.length} customer
          {details.reviews.length == 1 ? "s" : ""}
        </div>
      </div>
    </div>
  );
}

export default Photo;
