import React, { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";
import "./header.scss";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import { useDispatch } from "react-redux";
import { fetchCategory } from "../../redux/actionCreators/categoryAction";
import HeaderNavList from "./HeaderNavList/HeaderNavList";
import { logOutUser } from "../../redux/slices/userSlice";
import { TOrderItem } from "types/orderTypes";

function Header() {
  const { orderItemsList } = useAppSelector((state) => state.order);
  const { user, isLoggedIn } = useAppSelector((state) => state.user);
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const total = orderItemsList.reduce(
    (sum, current: TOrderItem) => sum + current.quantity,
    0
  );

  const handleNavigate = (path: string) => {
    navigate(path);
  };

  const handleLogOut = () => {
    dispatch(logOutUser());
    navigate("/");
  };

  return (
    <header className='header'>
      <div className='container'>
        <div className='header__logo' onClick={() => handleNavigate("/")}></div>
        <HeaderNavList />
        <div className='header__user-panel user-panel'>
          {user && isLoggedIn ? (
            <>
              <div
                className='user-lk'
                onClick={() =>
                  user.isAdmin
                    ? handleNavigate(`/admin/categories`)
                    : handleNavigate(`/lk/${user.id}/userinfo`)
                }>
                {user.firstname}
              </div>
              <div
                className='cart-icon user-panel__icon'
                onClick={() => handleNavigate(`/${user.id}/cart`)}>
                <div className='cart-icon__quantity'>{total}</div>
              </div>
              <div
                className='logout-icon user-panel__icon'
                onClick={() => handleLogOut()}></div>
            </>
          ) : (
            <>
              <div
                className='user-lk'
                onClick={() => handleNavigate(`/auth/sign-in`)}></div>
              <div
                className='cart-icon user-panel__icon'
                onClick={() => handleNavigate(`/auth/sign-in`)}></div>
              <div
                className='user-icon user-panel__icon'
                onClick={() => handleNavigate(`/auth/sign-in`)}></div>
            </>
          )}
        </div>
      </div>
    </header>
  );
}

export default Header;
