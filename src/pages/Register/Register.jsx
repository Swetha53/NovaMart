import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./Register.scss";
import Logo from "../../assets/logo.svg";
import Input from "../../components/Input/Input";
import Button from "../../components/Button/Button";
import {
  isEmpty,
  isInvalidPattern,
  isUnequal,
  isNotGreaterThan,
} from "../../config/validation";
import { registerUser } from "../../config/api";
import Ticker from "../../components/Ticker/Ticker";

function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [age, setAge] = useState(0);
  const [gender, setGender] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [address, setAddress] = useState("");
  const [avatar, setAvatar] = useState("");
  const [forceUpdate, setForceUpdate] = useState(0);
  const [showTicker, setShowTicker] = useState(false);
  const [tickerMessage, setTickerMessage] = useState("");
  const [formValidations, setFormValidations] = useState({
    email: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty", "isInvalidPattern"],
      pattern: "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+.[a-zA-Z]$",
    },
    password: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty", "isInvalidPattern"],
      pattern: "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$",
    },
    confirmPassword: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty", "isUnequal"],
      value: "password",
    },
    firstName: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty"],
    },
    lastName: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty"],
    },
    age: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty", "isNotGreaterThan"],
      pattern: 0,
    },
    phoneNumber: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty", "isInvalidPattern"],
      pattern: "^[+]?[(]?[0-9]{3}[)]?[-s.]?[0-9]{3}[-s.]?[0-9]{4,6}$",
    },
    address: {
      isValid: true,
      errorMessage: "",
      validationFun: ["isEmpty"],
    },
  });
  const validationFunList = {
    isEmpty,
    isUnequal,
    isInvalidPattern,
    isNotGreaterThan,
  };
  const formInputList = {
    email,
    password,
    confirmPassword,
    firstName,
    lastName,
    age,
    gender,
    phoneNumber,
    address,
    avatar,
  };
  const errorMessageList = {
    isEmpty: "This is a mandetory field please fill this and try again!",
    isInvalidPattern: "Invalid Value!",
    isNotGreaterThan: "Invalid Value!",
  };
  const accountType =
    useParams().userType == "merchant" ? "MERCHANT" : "CUSTOMER";
  const navigate = useNavigate();

  const toggleTicker = (value, message) => {
    setShowTicker(value);
    setTickerMessage(message);
  };
  const validateInput = (value, fieldName) => {
    let invalid = false;
    let tempFormValidations = formValidations;
    for (const element of tempFormValidations[fieldName].validationFun) {
      const value2 =
        element == "isUnequal"
          ? formInputList[tempFormValidations[fieldName].value]
          : tempFormValidations[fieldName].pattern;
      invalid = validationFunList[element](value, value2);
      if (invalid) {
        tempFormValidations[fieldName].errorMessage = errorMessageList[element];
        break;
      }
    }
    tempFormValidations[fieldName].isValid = !invalid;
    setFormValidations(tempFormValidations);
    setForceUpdate(forceUpdate + 1);
  };
  const validateFormAndSubmit = async () => {
    let isFormValid = true;
    Object.keys(formValidations).forEach((key) => {
      validateInput(formInputList[key], key);
      isFormValid = isFormValid && formValidations[key].isValid;
    });
    if (isFormValid) {
      try {
        const requestBody = {
          email: email,
          password: password,
          firstName: firstName,
          lastName: lastName,
          age: age,
          gender: gender,
          phone: phoneNumber,
          address: address,
          avatar: avatar,
          accountType: accountType,
          preferences: [],
        };
        await registerUser(requestBody);
      } catch (err) {
        toggleTicker(true, err.message);
      } finally {
        // setLoading(false);
        navigate("/");
      }
    }
  };

  return (
    <div className="register">
      {showTicker && (
        <Ticker
          type="error"
          message={tickerMessage}
          closeTickerHandler={() => {
            toggleTicker(false, "");
          }}
        />
      )}
      <img src={Logo} alt="Logo of website" />
      <h1>Register</h1>
      <div className="register__container">
        <div className="register__container__note">
          Please Fill all the fields having * as they are mandetory fields.
        </div>
        <div className="register__container__label">Email ID *</div>
        <Input
          inputType="email"
          error={formValidations["email"]}
          placeholder="Enter your Email ID"
          onChangeHandler={(value) => {
            setEmail(value);
            validateInput(value, "email");
          }}
        />
        <div className="register__container__label">Password *</div>
        <Input
          inputType="password"
          error={formValidations["password"]}
          placeholder="Enter your Password"
          onChangeHandler={(value) => {
            setPassword(value);
            validateInput(value, "password");
          }}
        />
        <div className="register__container__label">Confirm Password *</div>
        <Input
          inputType="password"
          error={formValidations["confirmPassword"]}
          placeholder="Re-enter your Password"
          onChangeHandler={(value) => {
            setConfirmPassword(value);
            validateInput(value, "confirmPassword");
          }}
        />
        <div className="register__container__label">First Name *</div>
        <Input
          inputType="text"
          error={formValidations["firstName"]}
          placeholder="Enter your First Name"
          onChangeHandler={(value) => {
            setFirstName(value);
            validateInput(value, "firstName");
          }}
        />
        <div className="register__container__label">Last Name *</div>
        <Input
          inputType="text"
          error={formValidations["lastName"]}
          placeholder="Enter your Last Name"
          onChangeHandler={(value) => {
            setLastName(value);
            validateInput(value, "lastName");
          }}
        />
        <div className="register__container__row">
          <div>Age *</div>
          <Input
            inputType="number"
            error={formValidations["age"]}
            placeholder="0"
            minValue="0"
            width="40%"
            onChangeHandler={(value) => {
              setAge(value);
              validateInput(value, "age");
            }}
          />
          <div>Gender *</div>
          <Input
            inputType="radio"
            name="Gender"
            placeholder={["F", "M", "Cannot Answer"]}
            onChangeHandler={(value) => {
              setGender(value);
            }}
          />
        </div>
        <div className="register__container__label">Phone Number *</div>
        <Input
          inputType="text"
          error={formValidations["phoneNumber"]}
          placeholder="Enter your Phone Number"
          onChangeHandler={(value) => {
            setPhoneNumber(value);
            validateInput(value, "phoneNumber");
          }}
        />
        <div className="register__container__label">Address *</div>
        <Input
          inputType="text"
          error={formValidations["address"]}
          placeholder="Enter your Address"
          onChangeHandler={(value) => {
            setAddress(value);
            validateInput(value, "address");
          }}
        />
        <div className="register__container__label">Profile Picture</div>
        <Input
          inputType="url"
          placeholder="Enter your Profile picture"
          onChangeHandler={(value) => {
            setAvatar(value);
          }}
        />
        <div className="register__container__footer">
          <Button
            text="Sign Up"
            onClickHandler={validateFormAndSubmit}
            margin="0.5rem 0rem"
          />
        </div>
      </div>
    </div>
  );
}

export default Register;
