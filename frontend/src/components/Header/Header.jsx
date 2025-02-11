import { NavLink } from "react-router-dom";
import "./Header.scss";
import reverseLogo from "./../../assets/reverse_logo.svg";
import cart from "./../../assets/cart.svg";
import profile from "./../../assets/profile.svg";
import Search from "./../Search/Search";

function Header(props) {
  return (
    <div className="header">
      <NavLink to="">
        <img src={reverseLogo} alt="Nova Mart" className="header__logo" />
      </NavLink>
      <div className="header__search">
        <Search />
      </div>
      <div className="header__profile flex">
        {props.user.imageUrl ? (
          <img
            src={props.user.imageUrl}
            alt="Profile Image"
            className="header__img"
          />
        ) : (
          <img src={profile} alt="Profile Image" className="header__img" />
        )}
        {props.user.firstName ? (
          <p>Hi, {props.user.firstName}</p>
        ) : (
          <NavLink to="login">Log In</NavLink>
        )}
      </div>
      <NavLink to="cart">
        <img src={cart} alt="Cart" className="header__img" />
      </NavLink>
    </div>
  );
}

export default Header;
