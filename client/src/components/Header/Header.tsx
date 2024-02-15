import React, { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";
import "./header.scss";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import { useDispatch } from "react-redux";
import { fetchCategory } from "../../redux/actionCreators/categoryAction";
import HeaderNavList from "./HeaderNavList/HeaderNavList";

type HeaderProps = {
  isLoggedIn: boolean;
  onModal: React.Dispatch<React.SetStateAction<boolean>>;
};

function Header({ onModal }: HeaderProps) {
  const navigate = useNavigate();
  const { user } = useAppSelector((state) => state.user);
  const userId = 1;
  const name = user.firstname;
  const roleId = user.firstname;
  const headerNavigate = (path: string) => {
    navigate(path);
  };

  return (
    <header className='header'>
      <div className='container'>
        <div className='header__logo' onClick={() => headerNavigate("/")}></div>
        <HeaderNavList onModal={onModal} />
        <div className='header__user-panel user-panel'>
          <div className='search-icon user-panel__icon'></div>
          <div>
            {roleId ? (
              <div
                className='user-lk'
                onClick={() => headerNavigate(`/lk/${user.id}`)}>
                {name}
              </div>
            ) : (
              <div
                className='user-icon user-panel__icon'
                onClick={() => headerNavigate(`/auth/signIn`)}></div>
            )}
          </div>
          <div
            className='cart-icon user-panel__icon'
            onClick={() => headerNavigate(`/${userId}/cart`)}></div>
          <div
            className='logout-icon user-panel__icon'
            onClick={() => headerNavigate(`/`)}></div>
        </div>
      </div>
    </header>
  );
}

export default Header;
