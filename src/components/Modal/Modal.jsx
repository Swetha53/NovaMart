import React from "react";
import "./Modal.scss";

const Modal = (props) => {
  const { closeEventHandler, details, children } = props;
  return (
    <div className="modal">
      <div className="modal__container">
        <div className="modal__container__close" onClick={closeEventHandler}>
          X
        </div>
        <h2>{details.heading}</h2>
        {children}
      </div>
    </div>
  );
};

export default Modal;
