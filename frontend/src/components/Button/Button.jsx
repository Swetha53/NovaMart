import "./Button.scss";

function Button(props) {
  return (
    <button
      className={props.reverse ? "button button-reverse" : "button"}
      style={{ "--width": props.width, "--margin": props.margin, "--height": props.height }}
      onClick={props.onClickHandler}
    >
      {props.text}
    </button>
  );
}

export default Button;
