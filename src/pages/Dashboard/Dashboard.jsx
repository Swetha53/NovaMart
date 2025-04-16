import "./Dashboard.scss";
import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchAllProducts, fetchSearchedProducts } from "../../config/api";
import Photo from "../../components/Photo/Photo";
import Ticker from "../../components/Ticker/Ticker";
import { sampleProducts } from "../../config/sample";
import Offline from "../../components/Offline/Offline";

const Dashboard = () => {
  const location = useLocation();
  const data = location.state;
  const [products, setProducts] = useState([]);
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [offlineMode, setOfflineMode] = useState(false);

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };
  const toggleOfflineModel = () => {
    setOfflineMode(true);
    setProducts(sampleProducts);
  };

  useEffect(() => {
    const loadProducts = async () => {
      try {
        const tempProducts = await fetchAllProducts();
        setProducts(tempProducts.body);
      } catch (err) {
        if (typeof err == "string" && err.includes("Network Error")) {
          toggleOfflineModel();
        }
        toggleTicker(true, err && err.message ? err.message : err);
      } finally {
        // setLoading(false);
      }
    };

    const loadSearchedProducts = async () => {
      try {
        const tempProducts = await fetchSearchedProducts(data.keyword);
        setProducts(tempProducts.body);
      } catch (err) {
        if (typeof err == "string" && err.includes("Network Error")) {
          toggleOfflineModel();
        }
        toggleTicker(true, err && err.message ? err.message : err);
      } finally {
        // setLoading(false);
      }
    };
    if (data && data.keyword) {
      loadSearchedProducts();
    } else {
      loadProducts();
    }
  }, [data]);
  return (
    <div className="dashboard">
      {offlineMode && <Offline />}
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
