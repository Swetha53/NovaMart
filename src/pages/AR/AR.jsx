import { useParams } from "react-router-dom";
import React from "react";
import "./AR.scss";
import "@google/model-viewer";

const AR = () => {
  const { productId } = useParams();
  const modelUrl =
    "https://reggvbnnkqmprlkojomx.supabase.co/storage/v1/object/public/Products//8989f64c-0ddc-4416-94f4-cd7cadc32131.glb";
  const iosModel =
    "https://reggvbnnkqmprlkojomx.supabase.co/storage/v1/object/public/Products//object.usdz";
  return (
    <div className="ar">
      <model-viewer
        className="ar__model"
        id="product-demo"
        ar
        ar-scale="fixed"
        ar-modes="webxr scene-viewer quick-look"
        camera-controls
        touch-action="pan-y"
        max-camera-orbit="auto 90deg auto"
        src={modelUrl}
        ios-src={iosModel}
        xr-environment
        alt="Product"
      >
        <button slot="ar-button" id="ar-button">
          View in your space
        </button>
      </model-viewer>
    </div>
  );
};

export default AR;
