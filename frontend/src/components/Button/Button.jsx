import "./Button.scss";

function Button(props) {
  return (
    <button
      className="button"
      style={{ "--width": props.width, "--margin": props.margin }}
      onClick={props.onClickHandler}
    >
      {props.text}
    </button>
  );
}

export default Button;
