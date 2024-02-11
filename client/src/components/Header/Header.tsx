import React, { useState } from "react";

import { useNavigate } from "react-router-dom";
import "./header.scss";
import { useAppSelector } from "../../redux/hooks/redux";

function Header() {
  const navigate = useNavigate();
  const { categories } = useAppSelector((state) => state.categories);
  const userId = 1;
  const headerNavigate = (path: string) => {
    navigate(path);
  };

  return (
    <header className='header'>
      <div className='container'>
        <div className='header__logo' onClick={() => headerNavigate("/")}></div>
        <nav className='nav'>
          {categories.map((item) => (
            <div className='nav__item' key={item.id}>
              {item.name}
            </div>
          ))}
        </nav>
        <div className='header__user-panel user-panel'>
          <div className='search-icon user-panel__icon'></div>
          <div
            className='user-icon user-panel__icon'
            onClick={() => headerNavigate(`/lk/${userId}`)}></div>
          <div
            className='cart-icon user-panel__icon'
            onClick={() => headerNavigate(`/${userId}/cart`)}></div>
        </div>
      </div>
    </header>
  );
}

export default Header;
