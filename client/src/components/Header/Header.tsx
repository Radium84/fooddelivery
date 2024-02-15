import React, { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";
import "./header.scss";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import { useDispatch } from "react-redux";
import { fetchCategory } from "../../redux/actionCreators/categoryAction";
import HeaderNavList from "./HeaderNavList/HeaderNavList";
import { logOutUser } from "../../redux/slices/userSlice";

type HeaderProps = {
  isLoggedIn: boolean;
  onModal: React.Dispatch<React.SetStateAction<boolean>>;
};

function Header({ onModal }: HeaderProps) {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { user } = useAppSelector((state) => state.user);

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
        <HeaderNavList onModal={onModal} />
        <div className='header__user-panel user-panel'>
          {user ? (
            <>
              <div
                className='user-lk'
                onClick={() => handleNavigate(`/lk/${user.id}`)}>
                {user.firstname}
              </div>
              <div
                className='cart-icon user-panel__icon'
                onClick={() => handleNavigate(`/${user.id}/cart`)}></div>
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
