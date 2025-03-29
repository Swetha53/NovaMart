import { useState, useEffect } from "react";
import {
  fetchUserDetails,
  fetchUserReviews,
  fetchUserOrders,
  addProductReview,
} from "../../config/api";
import { isEmpty } from "../../config/validation";
import "./Profile.scss";
import ProfileImage from "./../../assets/reverse_profile.svg";
import FilledStar from "./../../assets/fill_star.png";
import Star from "./../../assets/star.png";
import Image from "./../../assets/image.png";
import OrderTable from "../../components/OrderTable/OrderTable";
import Ticker from "../../components/Ticker/Ticker";
import Modal from "../../components/Modal/Modal";
import Input from "../../components/Input/Input";
import Button from "../../components/Button/Button";

function Profile() {
  const userId = sessionStorage.getItem("userId");
  const [userDetails, setUserDetails] = useState({});
  const [activeTab, setActiveTab] = useState(0);
  const [reviews, setReviews] = useState([]);
  const [orders, setOrders] = useState([]);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [modalDetails, setModalDetails] = useState({});
  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");
  const [rating, setRating] = useState(0);
  const [formValidations, setFormValidations] = useState({
    title: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty"],
      pattern: "^4[0-9]{12}(?:[0-9]{3})?$",
    },
    body: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty"],
      pattern: "^4[0-9]{12}(?:[0-9]{3})?$",
    },
    rating: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty"],
      pattern: "^4[0-9]{12}(?:[0-9]{3})?$",
    },
  });
  const [forceUpdate, setForceUpdate] = useState(0);
  const formInputList = {
    title,
    body,
    rating,
  };

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };

  useEffect(() => {
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

    const loadUserReviews = async () => {
      try {
        const tempUserReviews = await fetchUserReviews(userId);
        setReviews(tempUserReviews.body);
      } catch (err) {
        toggleTicker(true, err.message);
      } finally {
        // setLoading(false);
      }
    };

    loadUserDetails();
    loadUserReviews();
  }, []);

  const loadUserOrders = async () => {
    try {
      const tempUserOrders = await fetchUserOrders(userId);
      setOrders(tempUserOrders.body);
    } catch (err) {
      toggleTicker(true, err.message);
    } finally {
      // setLoading(false);
    }
  };
  const validateFormAndSubmit = async () => {
    let isFormValid = true;
    Object.keys(formValidations).forEach((key) => {
      validateInput(formInputList[key], key);
      isFormValid = isFormValid && formValidations[key].isValid;
    });
    if (isFormValid) {
      try {
        const requestBody = {
          userId: userId,
          merchantId: modalDetails.merchantId,
          productId: modalDetails.productId,
          title: title,
          comment: body,
          imageUrl: [],
          rating: rating,
        };
        await addProductReview(requestBody);
      } catch (err) {
        toggleTicker(true, err.message);
      } finally {
        // setLoading(false);
        setShowModal(false);
        setModalDetails({});
      }
    }
  };

  const tabChange = (value) => {
    setActiveTab(value);
    if (value == 1) {
      loadUserOrders();
    }
  };
  const writeProductReview = (item) => {
    setShowModal(true);
    setModalDetails({
      heading: "Tell us how you feel about this product",
      merchantId: item.merchantId,
      productId: item.productId,
    });
  };
  const closeReviewModal = () => {
    setShowModal(false);
    setModalDetails({});
  };
  const validateInput = (value, fieldName) => {
    let invalid = false;
    let tempFormValidations = formValidations;
    invalid = isEmpty(value, null);
    if (invalid) {
      tempFormValidations[fieldName].errorMessage =
        "This is a mandetory field please fill this and try again!";
    }
    tempFormValidations[fieldName].isValid = !invalid;
    setFormValidations(tempFormValidations);
    setForceUpdate(forceUpdate + 1);
  };

  return (
    <div className={`profile ${showModal && "profile-disable__scroll"}`}>
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
        <Modal closeEventHandler={closeReviewModal} details={modalDetails}>
          <div className="modal-container">
            <div className="modal-container__rows">
              <div>Title: </div>
              <Input
                inputType="text"
                error={formValidations["title"]}
                placeholder="Excellent Product"
                width="100%"
                onChangeHandler={(value) => {
                  setTitle(value);
                  validateInput(value, "title");
                }}
              />
            </div>
            <div className="modal-container__rows">
              <div>Description: </div>
              <Input
                inputType="textarea"
                error={formValidations["body"]}
                placeholder="Excellent Product"
                width="100%"
                height="4rem"
                onChangeHandler={(value) => {
                  setBody(value);
                  validateInput(value, "body");
                }}
              />
            </div>
            <div className="modal-container__rows">
              <div>Rating: </div>
              <div
                className="modal-container__rating modal-container__star-filled"
                key={rating}
              >
                {Array.from({ length: rating }, (_, i) => i + 1).map((num) => (
                  <img
                    className="modal-container__star"
                    src={FilledStar}
                    alt="Filled Star"
                    key={"fill-" + num}
                    onClick={() => {
                      setRating(num);
                      validateInput(rating + num, "rating");
                    }}
                  />
                ))}
                {Array.from({ length: 5 - rating }, (_, i) => i + 1).map(
                  (num) => (
                    <img
                      className="modal-container__star"
                      src={Star}
                      alt="Unfilled Star"
                      key={"unfilled-" + num}
                      onClick={() => {
                        setRating(rating + num);
                        validateInput(rating + num, "rating");
                      }}
                    />
                  )
                )}
              </div>
            </div>
            <Button
              text="Submit"
              onClickHandler={validateFormAndSubmit}
              width="100%"
              margin="0.5rem 0"
              height="1.5rem"
            />
          </div>
        </Modal>
      )}
      <div className="profile__image">
        {userDetails && userDetails.avatar ? (
          <div>
            <img src={userDetails.avatar} alt="Profile Image" />
          </div>
        ) : (
          <img src={ProfileImage} alt="Profile Image" />
        )}
      </div>
      <div className="profile__tabs">
        <div
          onClick={() => {
            tabChange(0);
          }}
          className={
            activeTab == 0
              ? "profile__tabs__tab-active profile__tabs__tab profile__tabs__profile"
              : "profile__tabs__tab profile__tabs__profile"
          }
        >
          Profile
        </div>
        <div
          onClick={() => {
            tabChange(1);
          }}
          className={
            activeTab == 1
              ? "profile__tabs__tab-active profile__tabs__tab profile__tabs__order"
              : "profile__tabs__tab profile__tabs__order"
          }
        >
          Orders
        </div>
        <div
          onClick={() => {
            tabChange(2);
          }}
          className={
            activeTab == 2
              ? "profile__tabs__tab-active profile__tabs__tab profile__tabs__wishlist"
              : "profile__tabs__tab-disabled profile__tabs__tab profile__tabs__wishlist"
          }
        >
          Wishlist
        </div>
        <div className="profile__tabs__main">
          {activeTab == 0 && (
            <>
              <div className="profile__tabs__main__info">
                <div className="firstname">
                  First Name: {userDetails.firstName}
                </div>
                <div className="lastname">
                  Last Name: {userDetails.lastName}
                </div>
                <div className="age">Age: {userDetails.age}</div>
                <div className="gender">Gender: {userDetails.gender}</div>
                <div className="email">Email: {userDetails.email}</div>
                <div className="address">Address: {userDetails.address}</div>
              </div>
              <div className="profile__tabs__main__reviews">
                <h3>Your Reviews</h3>
                <div className="profile__tabs__main__reviews__body">
                  {reviews.map((review, index) => (
                    <div
                      className="profile__tabs__main__reviews__container"
                      key={index}
                    >
                      <div className="profile__tabs__main__reviews__container__rating">
                        {Array.from(
                          { length: review.rating },
                          (_, i) => i + 1
                        ).map((num) => (
                          <img
                            src={FilledStar}
                            alt="Filled Star"
                            key={"fill-" + num}
                          />
                        ))}
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
                      <div className="profile__tabs__main__reviews__container__title">
                        {review.title}
                      </div>
                      <div>{review.comment}</div>
                      <div className="profile__tabs__main__reviews__container__footer">
                        {review.imageUrl && review.imageUrl.length > 0 ? (
                          <img
                            src={review.imageurl[0]}
                            className="profile__tabs__main__reviews__container__image"
                          />
                        ) : (
                          <img
                            src={Image}
                            alt="Image"
                            className="profile__tabs__main__reviews__container__image"
                          />
                        )}
                        <a href={"#/product/" + review.productId}>See Product</a>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </>
          )}
          {activeTab == 1 && (
            <>
              {orders.map((order, index) => (
                <OrderTable
                  order={order}
                  key={index}
                  name={userDetails.firstName + " " + userDetails.lastName}
                  writeProductReview={writeProductReview}
                />
              ))}
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default Profile;
