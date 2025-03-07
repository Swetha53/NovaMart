import axios from "axios";

const PRODUCT_URL = "http://localhost:8080/api/products";
const USER_URL = "http://localhost:8085/api/users";
const REVIEW_URL = "http://localhost:8080/api/reviews";
const ORDER_URL = "http://localhost:8081/api/orders";
const CART_URL = "http://localhost:8082/api/cart";

const checkLoginCredentials = async (email, password) => {
  try {
    const response = await axios.post(`${USER_URL}/login`, {
      email: email,
      password: password,
    });
    return response.data;
  } catch (error) {
    throw `Error checking login credentials: ${error}`;
  }
};

const registerUser = async (requestBody) => {
  try {
    const response = await axios.post(`${USER_URL}/register`, requestBody);
    return response.data;
  } catch (error) {
    throw `Error registering user: ${error}`;
  }
};

const fetchProductDetails = async (productId) => {
  try {
    const response = await axios.get(`${PRODUCT_URL}?productId=${productId}`);
    return response.data;
  } catch (error) {
    throw `Error fetching product details: ${error}`;
  }
};

const fetchUserDetails = async (userId) => {
  try {
    const response = await axios.get(`${USER_URL}?userId=${userId}`);
    return response.data;
  } catch (error) {
    throw `Error fetching user details: ${error}`;
  }
};

const fetchUserReviews = async (userId) => {
  try {
    const response = await axios.get(`${REVIEW_URL}?userId=${userId}`);
    return response.data;
  } catch (error) {
    throw `Error fetching user reviews: ${error}`;
  }
};

const fetchUserOrders = async (userId) => {
  try {
    const response = await axios.get(`${ORDER_URL}?userId=${userId}`);
    return response.data;
  } catch (error) {
    throw `Error fetching user orders: ${error}`;
  }
};

const updateCart = async (requestBody) => {
  try {
    const response = await axios.post(`${CART_URL}/update`, requestBody);
    return response.data;
  } catch (error) {
    throw `Error updating cart: ${error}`;
  }
};

export {
  checkLoginCredentials,
  registerUser,
  fetchProductDetails,
  fetchUserDetails,
  fetchUserReviews,
  fetchUserOrders,
  updateCart,
};
