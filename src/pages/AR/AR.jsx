import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import "./AR.scss";
import "@google/model-viewer";
import { fetchProductModel } from "../../config/api";
import Ticker from "../../components/Ticker/Ticker";
import Button from "../../components/Button/Button";

const AR = () => {
  const { productId } = useParams();
  const [modelUrl, setModelUrl] = useState("");
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const loadProductModel = async () => {
      try {
        const tempModelData = await fetchProductModel(productId);
        tempModelData.data[0].asset_url.replace(/(\.glb).*$/, ".glb");
        setModelUrl(tempModelData.data[0].asset_url);
      } catch (err) {
        toggleTicker(true, err.message);
      } finally {
        // setLoading(false);
      }
    };
    loadProductModel();
  }, []);

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };

  return (
    <div className="ar">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      <model-viewer
        className="ar__model"
        id="product-demo"
        ar
        ar-modes="webxr scene-viewer quick-look"
        camera-controls
        touch-action="pan-y"
        max-camera-orbit="auto 90deg auto"
        src={modelUrl}
        xr-environment
        alt="Product"
      >
        <button slot="ar-button" id="ar-button">
          👋 Activate AR
        </button>
      </model-viewer>
    </div>
  );
};

export default AR;
