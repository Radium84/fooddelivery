import "./styles/style.scss";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainAsync from "./pages/Main/MainAsync";
import PersonalAccountAsync from "./pages/Personal account/PersonalAccountAsync";
import { Suspense, useState, useEffect } from "react";
import Header from "./components/Header/Header";
import Cart from "./components/Cart/Cart";
import CategoryModal from "./components/Modals/CategoryModal/CategoryModal";
import SignIn from "./pages/SignIn/SignIn";
import SignUp from "./pages/SignUp/SignUp";
import { useAppDispatch } from "./redux/hooks/redux";
import { fetchUser } from "./redux/actionCreators/userAction";
import UserData from "./components/UserData/UserData";
import Orders from "./components/Orders/Orders";
import Favorites from "./components/Favorites/Favorites";
import CategoriesInfo from "./components/Admin/CategoriesInfo/CategoriesInfo";
import DiscountsInfo from "./components/Admin/DiscountsInfo/DiscountsInfo";
import ProductsInfo from "./components/Admin/ProductsInfo/ProductsInfo";

function App() {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchUser());
  }, []);

  return (
    <BrowserRouter>
      <div className='app'>
        <Header />
        <div className='page'>
          <div className='container'>
            <Suspense fallback={<div>loading...</div>}>
              <Routes>
                <Route path='/' element={<MainAsync />}></Route>
                <Route path='/auth'>
                  <Route path='sign-up' element={<SignUp />}></Route>
                  <Route path='sign-in' element={<SignIn />}></Route>
                </Route>
                <Route path='/:categoryName' element={<MainAsync />}></Route>
                <Route path='/lk/:id' element={<PersonalAccountAsync />}>
                  <Route path='userinfo' element={<UserData />}></Route>
                  <Route path='orders' element={<Orders />}></Route>
                  <Route path='favorites' element={<Favorites />}></Route>
                </Route>
                <Route path='/admin' element={<PersonalAccountAsync />}>
                  <Route path='categories' element={<CategoriesInfo />}></Route>
                  <Route path='discounts' element={<DiscountsInfo />}></Route>
                  <Route path='products' element={<ProductsInfo />}></Route>
                </Route>
                <Route path='/:id/cart' element={<Cart />}></Route>
              </Routes>
            </Suspense>
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
