import axios from "axios";

const PRODUCT_URL = 'http://localhost:8080/api/products';
const USER_URL = 'http://localhost:8085/api/users';
const REVIEW_URL = 'http://localhost:8080/api/reviews';

const fetchProductDetails = async (productId) => {
    try {
      const response = await axios.get(`${PRODUCT_URL}?productId=${productId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching product details:', error);
      throw error;
    }
};
  
const fetchUserDetails = async (userId) => {
  try {
    const response = await axios.get(`${USER_URL}?userId=${userId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching product details:', error);
    throw error;
  }
};

const fetchUserReviews = async (userId) => {
  try {
    const response = await axios.get(`${REVIEW_URL}?userId=${userId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching product details:', error);
    throw error;
  }
};

export {fetchProductDetails, fetchUserDetails, fetchUserReviews}