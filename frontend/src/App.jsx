// import { useState } from 'react'
import "./App.scss";
import { Outlet } from "react-router-dom";
import Header from "./components/Header/Header";

function App() {
  // const [count, setCount] = useState(0)
  const user = {
    imageUrl: null,
    firstName: null,
  };

  return (
    <>
      <Header user={user} />
      <Outlet />
    </>
  );
}

export default App;
