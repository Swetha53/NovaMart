import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Cart.scss";
import {
  getCartDetails,
  fetchProductDetails,
  updateCart,
  removeItemFromCart,
} from "../../config/api";
import Counter from "../../components/Counter/Counter";
import DeleteIcon from "../../assets/delete.png";
import Ticker from "../../components/Ticker/Ticker";
import Button from "../../components/Button/Button";
import Modal from "../../components/Modal/Modal";
import Input from "../../components/Input/Input";
import { isEmpty, isInvalidPattern } from "../../config/validation";

const Cart = () => {
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();
  const [cart, setCart] = useState({});
  const [cartItems, setCartItems] = useState([]);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [subTotalAmount, setSubtotaAmount] = useState(0);
  const [showModal, setShowModal] = useState(true);
  const [modalDetails, setModalDetails] = useState({
    heading: "Please select your Payment Method",
  });
  const [paymentType, setPaymentType] = useState("Cash");
  const [visaNumber, setVisaNumber] = useState(0);
  const [visaValidation, setVisaValidation] = useState({
    isValid: true,
    errorMessage: "",
    validationFun: ["isEmpty", "isInvalidPattern"],
    pattern: "^4[0-9]{12}(?:[0-9]{3})?$",
  });
  const [forceUpdate, setForceUpdate] = useState(0);
  const validationFunList = {
    isEmpty,
    isInvalidPattern,
  };
  const errorMessageList = {
    isEmpty: "This is a mandetory field please fill this and try again!",
    isInvalidPattern: "Invalid Value!",
  };

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };

  useEffect(() => {
    loadCartDetails();
  }, []);

  const loadCartDetails = async () => {
    try {
      const tempCart = await getCartDetails(userId);
      let tempSubTotal = 0;
      const tempCartItems = await Promise.all(
        tempCart.body[0].cartItemList.map(async (item) => {
          const tempPartialCartItem = await loadDetails(item.productId);
          tempSubTotal += item.totalPrice;
          return { ...tempPartialCartItem, ...item };
        })
      );
      setCart(tempCart.body[0]);
      setCartItems(tempCartItems);
      setSubtotaAmount(tempSubTotal);
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
  const openPaymentModal = () => {
    setShowModal(true);
  };
  const closePaymentModal = () => {
    setShowModal(false);
  };
  const validateVisaValue = (value) => {
    setVisaNumber(value);
    let invalid = false;
    let tempVisaValidations = visaValidation;
    for (const element of tempVisaValidations.validationFun) {
      invalid = validationFunList[element](value, tempVisaValidations.pattern);
      if (invalid) {
        tempVisaValidations.errorMessage = errorMessageList[element];
        break;
      }
    }
    tempVisaValidations.isValid = !invalid;
    setVisaValidation(tempVisaValidations);
    setForceUpdate(forceUpdate + 1);
  };
  const validateAndProceedToCheckout = () => {
    let formValid = false;
    if (paymentType == "VISA") {
      validateVisaValue(visaNumber);
      formValid = visaValidation.isValid;
    } else {
      formValid = true;
    }
    if (formValid) {
      sessionStorage.setItem(
        "paymentDetails",
        JSON.stringify({
          paymentType,
          visaNumber,
        })
      );
      navigate("/checkout");
    }
  };

  return (
    <div className="cart">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      {showModal && (
        <Modal closeEventHandler={closePaymentModal} details={modalDetails}>
          <div className="cart__modal">
            <div className="cart__modal__row">
              <div className="cart__modal__row__label">Paymemt Type: </div>
              <div className="cart__modal__row__input">
                <Input
                  inputType="radio"
                  name="PaymentType"
                  placeholder={["Cash", "VISA"]}
                  onChangeHandler={(value) => {
                    setPaymentType(value);
                  }}
                />
              </div>
            </div>
            {paymentType == "Cash" && (
              <div className="cart__modal__text">
                <div>
                  You have selected Cash on Delivery as method of Payment.
                </div>
                Please ensure you have the exact amount at the time of delivery.
              </div>
            )}
            {paymentType == "VISA" && (
              <div className="cart__modal__input">
                <div>Please Enter your VISA card number</div>
                <Input
                  inputType="text"
                  error={visaValidation}
                  placeholder="xxxx xxxx xxxx xxxx"
                  width="80%"
                  onChangeHandler={(value) => {
                    validateVisaValue(value);
                  }}
                />
              </div>
            )}
            <div className="cart__modal__footer">
              <Button
                text="Save Payment Method"
                onClickHandler={validateAndProceedToCheckout}
                width="60%"
                margin="0.25rem"
                height="1.5rem"
              />
            </div>
          </div>
        </Modal>
      )}
      <h1>Shopping Cart</h1>
      {!cartItems.length && (
        <div className="cart__empty-state">
          <h2>No Items Added to Cart</h2>
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
            and explore our wide range of products!!
          </h3>
        </div>
      )}
      {cartItems.length && (
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
                    onChangeEventHandler={(value) => {
                      onCounterChange(value, item.productId);
                    }}
                  />
                </div>
                <img
                  onClick={() => {
                    deleteItem(item.productId);
                  }}
                  src={DeleteIcon}
                  alt="Remove Product"
                  className="cart__container__left__row__delete"
                />
              </div>
            ))}
          </div>
          <div className="cart__container__right">
            <div className="cart__container__right__container">
              <div className="cart__container__right__container__row">
                <div>
                  Sub-Total Amount ({cartItems.length} Item
                  {cartItems.length == 1 ? "" : "s"}):
                </div>
                <div>
                  {cart.currencyCode} {subTotalAmount}
                </div>
              </div>
              <div className="cart__container__right__container__row">
                <div>Estimated Tax:</div>
                <div>
                  {cart.currencyCode}{" "}
                  {(cart.totalAmount - subTotalAmount).toFixed(1)}
                </div>
              </div>
              <div className="cart__container__right__container__row">
                <h3>Total Amount:</h3>
                <h3>
                  {cart.currencyCode} {cart.totalAmount}
                </h3>
              </div>
              <Button
                text="Proceed to Checkout"
                onClickHandler={openPaymentModal}
                width="40%"
                margin="0.25rem"
                height="1.5rem"
                reverse={true}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Cart;
