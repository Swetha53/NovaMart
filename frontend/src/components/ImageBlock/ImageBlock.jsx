import { useState } from "react";
import "./ImageBlock.scss";

function ImageBlock(props) {
  const [active, setActive] = useState(false);
  const activeEventHandler = (activeState) => {
    setActive(activeState);
  };
  return (
    <div
      className={active ? "image-block image-block-pause" : "image-block"}
      style={{ "--degree": props.productImages.length }}
    >
      {props.productImages.map((productImage, index) => (
        <span
          style={{ "--i": index + 1 }}
          key={index}
          onMouseEnter={() => {
            activeEventHandler(true);
          }}
          onMouseLeave={() => {
            activeEventHandler(false);
          }}
        >
          <img src={productImage} alt={"Image " + index} />
        </span>
      ))}
    </div>
  );
}

export default ImageBlock;
