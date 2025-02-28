import React from "react";
import "./Ticker.scss";

function Ticker(props) {
  const { type, message, closeTickerHandler } = props;
  return (
    <div className={type == "error" ? "ticker ticker-error" : "ticker"}>
      <div>{message}</div>
      <div className="ticker__close" onClick={closeTickerHandler}>
        X
      </div>
    </div>
  );
}

export default Ticker;
