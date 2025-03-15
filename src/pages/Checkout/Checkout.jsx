import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Checkout.scss";
import {
  getCartDetails,
  fetchProductDetails,
  updateCart,
  fetchUserDetails,
  placeUserOrder,
} from "../../config/api";
import DeleteIcon from "../../assets/delete.png";
import Ticker from "../../components/Ticker/Ticker";
import Button from "../../components/Button/Button";
import Counter from "../../components/Counter/Counter";

const Checkout = () => {
  const paymentDetails = JSON.parse(sessionStorage.getItem("paymentDetails"));
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState({});
  const [cart, setCart] = useState({});
  const [cartItems, setCartItems] = useState([]);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [subTotalAmount, setSubtotaAmount] = useState(0);
  const [itemCount, setItemCount] = useState(0);

  useEffect(() => {
    loadUserDetails();
    loadCartDetails();
  }, []);

  const loadUserDetails = async () => {
    try {
      const tempUserDetails = await fetchUserDetails(userId);
      setUserDetails(tempUserDetails.body[0]);
    } catch (err) {
      toggleTicker(true, err.message);
    } finally {
      // setLoading(false);
    }
  };
  const loadCartDetails = async () => {
    try {
      const tempCart = await getCartDetails(userId);
      let tempSubTotal = 0;
      let tempItemCount = 0;
      const tempCartItems = await Promise.all(
        tempCart.body[0].cartItemList.map(async (item) => {
          const tempPartialCartItem = await loadDetails(item.productId);
          tempSubTotal += item.totalPrice;
          tempItemCount += item.quantity;
          return { ...tempPartialCartItem, ...item };
        })
      );
      setCart(tempCart.body[0]);
      setCartItems(tempCartItems);
      setSubtotaAmount(tempSubTotal);
      setItemCount(tempItemCount);
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
  const addToCart = async (request) => {
    const requestBody = {
      userId: userId,
      productId: request.productId,
      quantity: request.quantity,
      unitPrice: request.unitPrice,
      currencyCode: request.currencyCode,
    };
    try {
      // TODO set cart quantity in session storage so that cart icon can display that
      await updateCart(requestBody);
    } catch (err) {
      toggleTicker(true, err.message);
    } finally {
      // setLoading(false);
      loadCartDetails();
    }
  };
  const deleteItem = async (productId) => {
    try {
      await removeItemFromCart(userId, productId);
    } catch (err) {
      toggleTicker(true, err.message);
      return {};
    } finally {
      // setLoading(false);
      loadCartDetails();
    }
  };
  const placeOrder = async () => {
    let requestBody = {
      userId: userId,
      userEmail: userDetails.email,
      totalAmount: cart.totalAmount,
      currencyCode: cart.currencyCode,
      customerName: `${userDetails.firstName} ${userDetails.lastName}`,
      orderItemList: [],
    };
    cartItems.map((item) => {
      const itemRequest = {
        orderItemId: "",
        orderId: "",
        productId: item.productId,
        merchantId: item.merchantId,
        quantity: item.quantity,
        unitPrice: item.unitPrice,
        totalPrice: item.totalPrice,
        createdAt: 0,
      };
      requestBody.orderItemList.push(itemRequest);
    });
    try {
      // TODO set cart quantity in session storage so that cart icon can display that
      await placeUserOrder(requestBody);
    } catch (err) {
      toggleTicker(true, err.message);
    } finally {
      // setLoading(false);
      navigate("/");
    }
  };

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };
  const onCounterChange = (value, productId) => {
    setCartItems((prevCartItems) =>
      prevCartItems.map((item) =>
        item.productId === productId ? { ...item, quantity: value } : item
      )
    );
    const foundItem = cartItems.find((item) => item.productId === productId);
    const request = {
      productId: foundItem.productId,
      quantity: value,
      unitPrice: foundItem.unitPrice,
      currencyCode: foundItem.currencyCode,
    };
    addToCart(request);
  };

  return (
    <div className="checkout">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      <h1>Checkout</h1>
      {cartItems.length ? (
        <div className="checkout__container">
          <div className="checkout__container__left">
            {cartItems.map((item, index) => (
              <div className="checkout__container__left__row" key={index}>
                <div className="checkout__container__left__row__first">
                  <img src={item.images[0]} alt="Product Image" />
                  <div className="checkout__container__left__row__first__details">
                    <h2>{item.name}</h2>
                    <div>
                      {item.currencyCode} {item.unitPrice}
                    </div>
                  </div>
                  <Counter
                    quantity={item.quantity}
                    maxQuantity={item.quantityAvailable}
                    onChangeEventHandler={(value) => {
                      onCounterChange(value, item.productId);
                    }}
                  />
                </div>
                <div className="checkout__container__left__row__second">
                  {item.currencyCode} {item.totalPrice}
                </div>
                <img
                  onClick={() => {
                    deleteItem(item.productId);
                  }}
                  src={DeleteIcon}
                  alt="Remove Product"
                  className="checkout__container__left__row__third"
                />
              </div>
            ))}
          </div>
          <div className="checkout__container__right">
            <div className="checkout__container__right__container">
              <h3>
                Delivering to {userDetails.firstName} {userDetails.lastName}
              </h3>
              <div>{userDetails.address}</div>
            </div>
            <div className="checkout__container__right__container">
              {paymentDetails.paymentType == "Cash" ? (
                <div>
                  <h3>Paying with Cash on Delivery</h3>
                  <div>
                    Please ensure you have the exact amount at the time of
                    delivery.
                  </div>
                </div>
              ) : (
                <div>
                  <h3>
                    Paying with VISA{" "}
                    {paymentDetails.visaNumber.substring(
                      paymentDetails.visaNumber.length - 4
                    )}
                  </h3>
                </div>
              )}
            </div>
            <div className="checkout__container__right__total">
              <div className="checkout__container__right__total__row">
                <div>
                  Sub-Total Amount ({itemCount} Item
                  {itemCount == 1 ? "" : "s"}):
                </div>
                <div>
                  {cart.currencyCode} {subTotalAmount}
                </div>
              </div>
              <div className="checkout__container__right__total__row">
                <div>Estimated Tax:</div>
                <div>
                  {cart.currencyCode}{" "}
                  {(cart.totalAmount - subTotalAmount).toFixed(1)}
                </div>
              </div>
              <div className="checkout__container__right__total__row">
                <h3>Total Amount:</h3>
                <h3>
                  {cart.currencyCode} {cart.totalAmount}
                </h3>
              </div>
            </div>
            <Button
              text="Place Order"
              onClickHandler={placeOrder}
              width="100%"
              margin="0.25rem"
              height="1.5rem"
            />
          </div>
        </div>
      ) : (
        <div className="checkout__empty">
          <h2>No Items Present for Checkout</h2>
          <h3>
            Please go to our
            <Button
              text="Dashboard"
              onClickHandler={() => {
                navigate("/");
              }}
              width="40%"
              margin="0.25rem"
              height="1.5rem"
            />
            and explore our wide range of products to add to Cart!!
          </h3>
        </div>
      )}
    </div>
  );
};

export default Checkout;
