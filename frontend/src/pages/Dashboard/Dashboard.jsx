import "./Dashboard.scss";
import { useEffect, useState } from "react";
import { fetchAllProducts } from "../../config/api";
import Photo from "../../components/Photo/Photo";

const Dashboard = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const loadProducts = async () => {
      try {
        const tempProducts = await fetchAllProducts();
        console.log(tempProducts.body);
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
