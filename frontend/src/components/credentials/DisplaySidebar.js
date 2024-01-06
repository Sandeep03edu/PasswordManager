import React from "react";
import { Link } from "react-router-dom";
import "./Sidebar.css";

const DisplaySidebar = () => {
  const styles = {
    centerElement: {
      display: "flex",
      alignItems: "center",
    },
  };
  return (
    <div
      id="bdSidebar"
      className="d-flex flex-column  
                    flex-shrink-0  
                    p-3 bg-success 
                    text-white offcanvas-md offcanvas-start"
    >
      <Link to="/" className="navbar-brand"></Link>
      <p className="mx-2 my-0 p-0">Password Manager</p>
      <hr />
      <ul className="mynav nav nav-pills flex-column mb-auto">
        <li className="nav-item mb-1">
          <Link to="/credential/display/dashboard" style={styles.centerElement}>
            <i className="fa-solid fa-chart-line mx-1"></i>
            Dashboard
          </Link>
        </li>
        <li className="nav-item mb-1">
          <Link to="/credential/display/cards" style={styles.centerElement}>
            <i className="fa-regular fa-credit-card mx-1"></i>
            Cards
          </Link>
        </li>
        <li className="nav-item mb-1">
          <Link to="/credential/display/passwords" style={styles.centerElement}>
            <i className="fa-solid fa-key mx-1"></i>
            Passwords
          </Link>
        </li>
        <li className="sidebar-item  nav-item mb-1">
          <Link
            to="/"
            className="sidebar-link collapsed"
            data-bs-toggle="collapse"
            data-bs-target="#settings"
            aria-expanded="false"
            aria-controls="settings"
            style={styles.centerElement}
          >
            <i className="fas fa-cog pe-2 mx-1"></i>
            <span className="topic">Settings </span>
          </Link>
          <ul
            id="settings"
            className="sidebar-dropdown list-unstyled collapse"
            data-bs-parent="#sidebar"
          >
            <li className="sidebar-item">
              <Link
                to="/"
                className="sidebar-link"
                style={styles.centerElement}
              >
                <i className="fas fa-user-plus pe-2 mx-1"></i>
                <span className="topic">Edit Profile</span>
              </Link>
            </li>
            <li className="sidebar-item">
              <Link
                to="/"
                className="sidebar-link"
                style={styles.centerElement}
              >
                <i className="fas fa-sign-out-alt pe-2 mx-1"></i>
                <span className="topic">Log Out</span>
              </Link>
            </li>
          </ul>
        </li>
      </ul>
      <hr />
      <div className="d-flex" style={styles.centerElement}>
        <i className="fa-solid fa-user mx-2"></i>
        <span>
          <h6 className="mt-1 mb-0">Sandeep Mishra</h6>
        </span>
      </div>
    </div>
  );
};

export default DisplaySidebar;
