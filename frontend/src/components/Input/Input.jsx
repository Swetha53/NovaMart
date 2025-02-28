import React from "react";
import "./Input.scss";

function Input(props) {
  const { type, placeholder, onChangeHandler, reverse } = props;
  return (
    <input
      type={type}
      placeholder={placeholder}
      className={reverse ? "input input-reverse" : "input"}
      onChange={(event) => {
        onChangeHandler(event.target.value);
      }}
    />
  );
}

export default Input;
