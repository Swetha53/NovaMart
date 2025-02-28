import { NavLink } from "react-router-dom";
import "./Header.scss";
import reverseLogo from "./../../assets/reverse_logo.svg";
import cart from "./../../assets/cart.svg";
import profile from "./../../assets/profile.svg";
import Search from "./../Search/Search";

function Header() {
  const userName = sessionStorage.getItem("userName")
  const avatar = sessionStorage.getItem("avatar")

  return (
    <div className="header">
      <NavLink to="">
        <img src={reverseLogo} alt="Nova Mart" className="header__logo" />
      </NavLink>
      <div className="header__search">
        <Search />
      </div>
      <div className="header__profile">
        {avatar ? (
          <img
            src={avatar}
            alt="Profile Image"
            className="header__profile__image"
          />
        ) : (
          <img src={profile} alt="Profile Image" className="header__profile__image" />
        )}
        {userName ? (
          <p>Hi, {userName}</p>
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
