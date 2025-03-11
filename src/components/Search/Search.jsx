import "./Search.scss";
import search from "./../../assets/search.svg";
import { useState } from "react";

function Search() {
  const [searchInput, setSearchInput] = useState("");

  function handleClickEvent() {
    console.log(searchInput);
  }

  return (
    <div className="search flex">
      <input
        type="text"
        className="search__input"
        value={searchInput}
        onChange={(e) => setSearchInput(e.target.value)}
        placeholder="Search product"
      />
      <div className="search__icon flex" onClick={handleClickEvent}>
        <img src={search} alt="Search product" />
      </div>
    </div>
  );
}

export default Search;
