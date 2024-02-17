import React from "react";

type TNavItemProps = {
  text: string;
  isActive: boolean;
  onSelect: () => {};
};

function NavItem({ text, isActive, onSelect }: TNavItemProps) {
  return (
    <li
      className={`nav-list__item ${isActive ? "active" : ""}`}
      onSelect={onSelect}>
      {text}
    </li>
  );
}

export default NavItem;
