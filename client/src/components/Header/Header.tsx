import React, { useState } from "react";

import { Link } from "react-router-dom";
import classes from "./header.module.scss";
import "./header.module.scss";
import { useSelector } from "react-redux";
import { RootState } from "src/redux/store";
import { log } from "console";

function Header() {
  const categories = useSelector((state: RootState) => state.categories);

  return (
    <header className={classes.header}>
      <div className={classes.container}>
        <div className={classes.header__logo}>
          <Link to='/'></Link>
        </div>
        <nav className={classes.nav}>
          {categories.map((item) => (
            <div className={classes.nav__item} key={item.id}>
              {item.name}
            </div>
          ))}
        </nav>
        <div className={`${classes["header__user-panel"]}, ${classes["user-panel"]}`}>
          <div
            className={
              (classes["search-icon"], classes["user-panel__icon"])
            }></div>
          <div
            className={
              (classes["user-icon"], classes["user-panel__icon"])
            }></div>
          <div
            className={
              (classes["cart-icon"], classes["user-panel__icon"])
            }></div>
        </div>
      </div>
    </header>
  );
}

export default Header;
