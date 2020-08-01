import React from "react";

const Header = ({ dark, children, className, pages }) => {
  dark = !!dark ? "dark" : "light";
  return (
    <nav
      className={
        `Header navbar navbar-${dark} bg-${dark}` +
        (className ? " " + className : "")
      }
    >
      <span className="navbar-brand">{children}</span>

      {pages && pages.length > 0 && (
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav mr-auto">
            {pages.map((item, key) => (
              <li className="nav-item" key={key}>
                <a href={item.Path} className="nav-link">
                  {item.Name}
                </a>
              </li>
            ))}
          </ul>
        </div>
      )}
    </nav>
  );
};

export default Header;
