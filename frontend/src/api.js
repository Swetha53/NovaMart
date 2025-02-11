import axios from "axios";

const PRODUCT_URL = 'http://localhost:8080/api/products';

export const fetchProductDetails = async (productId) => {
    try {
      const response = await axios.get(`${PRODUCT_URL}?productId=${productId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching product details:', error);
      throw error;
    }
  };