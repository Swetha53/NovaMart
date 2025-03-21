import axios from "axios";

const IP_ADDRESS = "192.168.2.108";
// 192.168.2.108
// 10.200.67.24
const PRODUCT_URL = `https://${IP_ADDRESS}:8080/api/products`;
const USER_URL = `https://${IP_ADDRESS}:8085/api/users`;
const REVIEW_URL = `https://${IP_ADDRESS}:8080/api/reviews`;
const ORDER_URL = `https://${IP_ADDRESS}:8081/api/orders`;
const CART_URL = `https://${IP_ADDRESS}:8082/api/cart`;
const REALITY_URL = `https://${IP_ADDRESS}:8443/reality`;

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

const addProductReview = async (requestBody) => {
  try {
    const response = await axios.post(`${REVIEW_URL}/add`, requestBody);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error adding product review: ${error}`;
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

const placeUserOrder = async (requestBody) => {
  try {
    const response = await axios.post(`${ORDER_URL}/create`, requestBody);
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error placing order: ${error}`;
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

const removeItemFromCart = async (userId, productId) => {
  try {
    const response = await axios.delete(
      `${CART_URL}/delete?userId=${userId}&productId=${productId}`
    );
    if (response.data && response.data.status == 200) {
      return response.data;
    } else {
      throw response.message;
    }
  } catch (error) {
    throw `Error updating cart: ${error}`;
  }
};

const fetchProductModel = async (productId) => {
  try {
    const response = await axios.get(`${REALITY_URL}/${productId}`);
    if (response && response.data) {
      return response.data;
    } else {
      throw response;
    }
  } catch (error) {
    throw `Error fetching product model : ${error}`;
  }
};

export {
  checkLoginCredentials,
  registerUser,
  fetchAllProducts,
  fetchProductDetails,
  fetchUserDetails,
  addProductReview,
  fetchUserReviews,
  fetchUserOrders,
  placeUserOrder,
  updateCart,
  getCartDetails,
  removeItemFromCart,
  fetchProductModel,
};
