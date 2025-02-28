// import { useState } from 'react'
import "./App.scss";
import { Outlet, useLocation } from "react-router-dom";
import Header from "./components/Header/Header";

function App() {
  const path = useLocation().pathname;

  return (
    <>
      {path !== "/login" && <Header />}
      <Outlet />
    </>
  );
}

export default App;
