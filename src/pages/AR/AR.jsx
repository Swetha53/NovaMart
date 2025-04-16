import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import "./AR.scss";
import "@google/model-viewer";
import { fetchProductModel } from "../../config/api";
import Ticker from "../../components/Ticker/Ticker";
import Offline from "../../components/Offline/Offline";
import { sampleAsset } from "../../config/sample";

const AR = () => {
  const { productId } = useParams();
  const [modelUrl, setModelUrl] = useState("");
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [offlineMode, setOfflineMode] = useState(false);

  const toggleOfflineModel = () => {
    setOfflineMode(true);
    let sampleData = sampleAsset;
    sampleData.data[0].asset_url.replace(/(\.glb).*$/, ".glb");
    setModelUrl(sampleData.data[0].asset_url);
  };

  useEffect(() => {
    const loadProductModel = async () => {
      try {
        const tempModelData = await fetchProductModel(productId);
        tempModelData.data[0].asset_url.replace(/(\.glb).*$/, ".glb");
        setModelUrl(tempModelData.data[0].asset_url);
      } catch (err) {
        if (typeof err == "string" && err.includes("Network Error")) {
          toggleOfflineModel();
        }
        toggleTicker(true, err && err.message ? err.message : err);
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
