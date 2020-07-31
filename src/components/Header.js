import React from "react";

const Header = ({ dark, children, className, pages }) => {
  return (
    <nav
      className={
        `Header navbar navbar-${dark} bg-${dark}` +
        (className ? " " + className : "") +
        (pages && pages.length ? " navbar-expand-lg" : "")
      }
    >
      <span className="navbar-brand">{children}</span>
     
    </nav>
  );
};

export default Header;
