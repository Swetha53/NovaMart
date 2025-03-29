import "./Search.scss";
import search from "./../../assets/search.svg";
import { useState } from "react";

function Search(props) {
  const { searchInputValue } = props;
  const [searchInput, setSearchInput] = useState("");

  return (
    <div className="search flex">
      <input
        type="text"
        className="search__input"
        value={searchInput}
        onChange={(e) => setSearchInput(e.target.value)}
        placeholder="Search product"
      />
      <div
        className="search__icon flex"
        onClick={() => searchInputValue(searchInput)}
      >
        <img src={search} alt="Search product" />
      </div>
    </div>
  );
}

export default Search;
