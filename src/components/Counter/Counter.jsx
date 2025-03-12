import { useState, useEffect } from "react";
import "./Counter.scss";
import upArrow from "./../../assets/up_arrow.png";
import downArrow from "./../../assets/down_arrow.png";

function Counter(props) {
  const { quantity, maxQuantity } = props;
  const [value, setValue] = useState(quantity ? quantity : 1);

  const onChangeEventHandler = (tempValue) => {
    if (value + tempValue <= maxQuantity && value + tempValue > 0) {
      props.onChangeEventHandler(value + tempValue);
      setValue(value + tempValue);
    }
  };
  return (
    <div className="counter">
      <div className="counter__display">{value}</div>
      <div className="counter__arrows">
        <button
          className={value == maxQuantity ? "counter__arrows-disabled" : ""}
          disabled={value == maxQuantity}
          onClick={() => {
            onChangeEventHandler(1);
          }}
        >
          <img src={upArrow} alt="Increase" />
        </button>
        <button
          className={value <= 1 ? "counter__arrows-disabled" : ""}
          disabled={value <= 1}
          onClick={() => {
            onChangeEventHandler(-1);
          }}
        >
          <img src={downArrow} alt="Decrease" />
        </button>
      </div>
    </div>
  );
}

export default Counter;
