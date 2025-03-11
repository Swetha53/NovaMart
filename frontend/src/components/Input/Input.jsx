import React, { useState } from "react";
import "./Input.scss";

function Input(props) {
  const {
    inputType,
    placeholder,
    onChangeHandler,
    reverse,
    width,
    name,
    error,
    margin,
    minValue,
  } = props;
  const [inputValue, setInputValue] = useState(0);

  const onChangeEventHandler = (index) => {
    setInputValue(index);
    onChangeHandler(placeholder[index]);
  };
  return (
    <>
      {inputType !== "radio" && (
        <div
          className={`${reverse ? "input input-reverse" : "input"} ${
            error && !error.isValid ? "input-error" : ""
          }`}
          style={{ "--width": width, "--margin": margin }}
        >
          <input
            type={inputType}
            placeholder={placeholder}
            min={minValue}
            onChange={(event) => {
              onChangeHandler(event.target.value);
            }}
          />
          {error && !error.isValid && (
            <div className="input-error__label">{error.errorMessage}</div>
          )}
        </div>
      )}
      {inputType == "radio" && (
        <div className="input__radio">
          {placeholder.map((text, idx) => (
            <span key={idx} className="input__radio__container">
              <input
                type="radio"
                id={idx}
                name={name}
                checked={inputValue == idx}
                onChange={() => {
                  onChangeEventHandler(idx);
                }}
              />
              <label htmlFor={idx}>
                <div className="symbol"></div>
                {text}
              </label>
              <br />
            </span>
          ))}
        </div>
      )}
    </>
  );
}

export default Input;
