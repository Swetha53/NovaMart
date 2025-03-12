import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import {
  createHashRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";
import App from "./App.jsx";
import Dashboard from "./pages/Dashboard/Dashboard.jsx";
import Login from "./pages/Login/Login.jsx";
import Cart from "./pages/Cart/Cart.jsx";
import Product from "./pages/Product/Product.jsx";
import Profile from "./pages/Profile/Profile.jsx";
import Register from "./pages/Register/Register.jsx";

const router = createHashRouter(
  createRoutesFromElements(
    <Route path="/" element={<App />}>
      <Route index element={<Dashboard />} />
      <Route path="login" element={<Login />} />
      <Route path="cart" element={<Cart />} />
      <Route path="product" element={<Product />}>
        <Route path=":productId" element={<Product />} />
      </Route>
      <Route path="profile" element={<Profile />} />
      <Route path="register" element={<Register />}>
        <Route path=":userType" element={<Register />} />
      </Route>
    </Route>
  )
);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
