import "./Button.scss";

function Button(props) {
  const { reverse, width, margin, height, onClickHandler, text } = props;
  return (
    <button
      className={reverse ? "button button-reverse" : "button"}
      style={{ "--width": width, "--margin": margin, "--height": height }}
      onClick={onClickHandler}
    >
      {text}
    </button>
  );
}

export default Button;
