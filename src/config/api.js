import axios from "axios";

const PRODUCT_URL = "http://localhost:8090/api/products";
const USER_URL = "http://localhost:8095/api/users";
const REVIEW_URL = "http://localhost:8090/api/reviews";
const ORDER_URL = "http://localhost:8091/api/orders";
const CART_URL = "http://localhost:8092/api/cart";

const checkLoginCredentials = async (email, password) => {
  try {
    const response = await axios.post(`${USER_URL}/login`, {
      email: email,
      password: password,
    });
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error checking login credentials: ${error}`;
  }
};

const registerUser = async (requestBody) => {
  try {
    const response = await axios.post(`${USER_URL}/register`, requestBody);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error registering user: ${error}`;
  }
};

const fetchAllProducts = async () => {
  try {
    const response = await axios.get(`${PRODUCT_URL}/all`);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error fetching product details: ${error}`;
  }
};

const fetchProductDetails = async (productId) => {
  try {
    const response = await axios.get(`${PRODUCT_URL}?productId=${productId}`);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error fetching product details: ${error}`;
  }
};

const fetchUserDetails = async (userId) => {
  try {
    const response = await axios.get(`${USER_URL}?userId=${userId}`);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error fetching user details: ${error}`;
  }
};

const fetchUserReviews = async (userId) => {
  try {
    const response = await axios.get(`${REVIEW_URL}?userId=${userId}`);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error fetching user reviews: ${error}`;
  }
};

const fetchUserOrders = async (userId) => {
  try {
    const response = await axios.get(`${ORDER_URL}?userId=${userId}`);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error fetching user orders: ${error}`;
  }
};

const updateCart = async (requestBody) => {
  try {
    const response = await axios.post(`${CART_URL}/update`, requestBody);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error updating cart: ${error}`;
  }
};

const getCartDetails = async (userId) => {
  try {
    const response = await axios.get(`${CART_URL}?userId=${userId}`);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error updating cart: ${error}`;
  }
};

export {
  checkLoginCredentials,
  registerUser,
  fetchAllProducts,
  fetchProductDetails,
  fetchUserDetails,
  fetchUserReviews,
  fetchUserOrders,
  updateCart,
  getCartDetails,
};
