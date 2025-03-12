import "./Dashboard.scss";
import { useEffect, useState } from "react";
import { fetchAllProducts } from "../../config/api";
import Photo from "../../components/Photo/Photo";
import Ticker from "../../components/Ticker/Ticker";

const Dashboard = () => {
  const [products, setProducts] = useState([]);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };

  useEffect(() => {
    const loadProducts = async () => {
      try {
        const tempProducts = await fetchAllProducts();
        setProducts(tempProducts.body);
      } catch (err) {
        toggleTicker(true, err.message);
      } finally {
        // setLoading(false);
      }
    };

    loadProducts();
  }, []);
  return (
    <div className="dashboard">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      <div className="dashboard__carousel">
        <h1>No Promotions Ongoing!</h1>
      </div>
      <div className="dashboard__products">
        {products.map((product, index) => (
          <Photo key={index} details={product} />
        ))}
      </div>
    </div>
  );
};

export default Dashboard;
