import React, { useState, useEffect } from "react";
import "./Cart.scss";
import { getCartDetails, fetchProductDetails } from "../../config/api";
import Counter from "../../components/Counter/Counter";
import DeleteIcon from "../../assets/delete.png";

const Cart = () => {
  const userId = sessionStorage.getItem("userId");
  const [cart, setCart] = useState({});
  const [cartItems, setCartItems] = useState([]);

  useEffect(() => {
    loadCartDetails();
  }, []);

  const loadCartDetails = async () => {
    try {
      const tempCart = await getCartDetails(userId);
      const tempCartItems = await Promise.all(
        tempCart.body[0].cartItemList.map(async (item) => {
          console.log(item);
          const tempPartialCartItem = await loadDetails(item.productId);
          return { ...tempPartialCartItem, ...item };
        })
      );
      setCart(tempCart.body[0]);
      setCartItems(tempCartItems);
    } catch (err) {
      toggleTicker(true, err.message);
    } finally {
      // setLoading(false);
    }
  };
  const loadDetails = async (productId) => {
    try {
      const tempProductDetails = await fetchProductDetails(productId);
      return tempProductDetails.body[0];
    } catch (err) {
      toggleTicker(true, err.message);
      return {};
    } finally {
      // setLoading(false);
    }
  };

  const onCounterChange = (value, productId) => {
    setCartItems((prevCartItems) =>
      prevCartItems.map((item) =>
        item.productId === productId ? { ...item, quantity: value } : item
      )
    );
  };

  return (
    <div className="cart">
      <h1>Shopping Cart</h1>
      <div className="cart__container">
        <div className="cart__container__left">
          {cartItems.map((item, index) => (
            <div className="cart__container__left__row" key={index}>
              <div className="cart__container__left__row__left">
                <img src={item.images[0]} alt="Product Image" />
                <div className="cart__container__left__row__left__details">
                  <h2>{item.name}</h2>
                  <div
                    className={`cart__container__left__row__left__details__label ${
                      item.quantityAvailable == 0
                        ? "cart__container__left__row__left__details__label-grey"
                        : item.quantityAvailable <= 10
                        ? "cart__container__left__row__left__details__label-red"
                        : item.quantitySold + item.quantityReserved >=
                          item.quantityAvailable
                        ? "cart__container__left__row__left__details__label-orange"
                        : ""
                    }`}
                  >
                    {item.quantityAvailable == 0
                      ? "Out of Stock"
                      : item.quantityAvailable <= 10
                      ? "Limited Quantity Available"
                      : item.quantitySold + item.quantityReserved >=
                        item.quantityAvailable
                      ? "Selling Fast"
                      : "In Stock"}
                  </div>
                  <div className="cart__container__left__row__left__details__price">
                    {item.currencyCode} {item.totalPrice}
                  </div>
                </div>
                <Counter
                  quantity={item.quantity}
                  maxQuantity={item.quantityAvailable}
                  onChangeEventHandler={onCounterChange}
                />
              </div>
              <img
                src={DeleteIcon}
                alt="Remove Product"
                className="cart__container__left__row__delete"
              />
            </div>
          ))}
        </div>
        <div className="cart__container__right"></div>
      </div>
    </div>
  );
};

export default Cart;
