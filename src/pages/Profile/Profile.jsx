import { useState, useEffect } from "react";
import {
  fetchUserDetails,
  fetchUserReviews,
  fetchUserOrders,
} from "../../config/api";
import "./Profile.scss";
import ProfileImage from "./../../assets/reverse_profile.svg";
import FilledStar from "./../../assets/fill_star.png";
import Star from "./../../assets/star.png";
import Image from "./../../assets/image.png";
import OrderTable from "../../components/OrderTable/OrderTable";
import Ticker from "../../components/Ticker/Ticker";

function Profile() {
  const userId = sessionStorage.getItem("userId");
  const [userDetails, setUserDetails] = useState({});
  const [activeTab, setActiveTab] = useState(0);
  const [reviews, setReviews] = useState([]);
  const [orders, setOrders] = useState([]);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

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

  const tabChange = (value) => {
    setActiveTab(value);
    if (value == 1) {
      loadUserOrders();
    }
  };
  return (
    <div className="profile">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
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
          Wishlist [Coming Soon!!]
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
                      <a href={"/product/" + review.productId}>See Product</a>
                    </div>
                  </div>
                ))}
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
