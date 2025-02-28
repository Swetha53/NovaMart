// TODO loader
import "./Login.scss";
import Logo from "../../assets/logo.svg";
import Input from "../../components/Input/Input";
import Button from "../../components/Button/Button";
import { checkLoginCredentials } from "../../config/api";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { NavLink } from "react-router-dom";
import Ticker from "../../components/Ticker/Ticker";

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showTicker, setShowTicker] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setErrorMessage(message);
  };

  const checkCredentials = async () => {
    try {
      const userData = await checkLoginCredentials(email, password);
      sessionStorage.setItem("userId", userData.userId);
      sessionStorage.setItem("userName", userData.firstName);
      sessionStorage.setItem("avatar", userData.avatar);
      navigate("/");
    } catch (err) {
      toggleTicker(true, err.message);
    } finally {
      // setLoading(false);
    }
  };

  return (
    <div className="login">
      {showTicker && (
        <Ticker
          type="error"
          message={errorMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      <div className="login__user">
        <div className="login__user__header">
          <img src={Logo} alt="Nova Mart Logo" />
        </div>
        <div className="login__user__body">
          <h1>Sign In or Create New Account</h1>
          <div className="login__user__body__container">
            <div>Email ID</div>
            <Input
              type="email"
              placeholder="Enter your Email ID"
              onChangeHandler={(value) => {
                setEmail(value);
              }}
            />
            <div>Password</div>
            <Input
              type="password"
              placeholder="Enter your Password"
              onChangeHandler={(value) => {
                setPassword(value);
              }}
            />
            <a>Forgot Password?</a>
            <Button
              text="Log In"
              onClickHandler={checkCredentials}
              margin="1rem 2rem"
            />
            <div className="login__user__body__container__alternative">
              <hr />
              <span>OR</span>
              <hr />
            </div>
            <div className="login__user__body__container__footer">
              New Here? <NavLink to="/register">Sign Up!</NavLink>
            </div>
          </div>
        </div>
      </div>
      <div className="login__merchant">
        <h1>Our Partner?</h1>
        <h2>Sign In Here</h2>
        <div className="login__merchant__container">
          <div>Email ID</div>
          <Input
            reverse={true}
            type="email"
            placeholder="Enter your Email ID"
            onChangeHandler={(value) => {
              setEmail(value);
            }}
          />
          <div>Password</div>
          <Input
            reverse={true}
            type="password"
            placeholder="Enter your Password"
            onChangeHandler={(value) => {
              setPassword(value);
            }}
          />
          <a>Forgot Password?</a>
          <Button
            reverse={true}
            text="Log In"
            onClickHandler={checkCredentials}
            margin="1rem 2rem"
          />
          <div className="login__merchant__container__alternative">
            <hr />
            <span>OR</span>
            <hr />
          </div>
          <div className="login__merchant__container__footer">
            Want to be our Partner?{" "}
            <NavLink to="/register/merchant">Sign Up!</NavLink>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
