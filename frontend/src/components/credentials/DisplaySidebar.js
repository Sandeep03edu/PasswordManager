import React from "react";
import { Link } from "react-router-dom";
import "./Sidebar.css";

const DisplaySidebar = ({ onClick }) => {
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
          <div
            className="px-1 py-2"
            style={styles.centerElement}
            onClick={(e) => {
              e.preventDefault();
              onClick("Dashboard");
            }}
          >
            <i
              className="fa-solid fa-chart-line"
              style={{ marginRight: "10px", paddingLeft: "5px" }}
            ></i>
            Dashboard
          </div>
        </li>
        <li className="nav-item mb-1">
          <div
            className="px-1 py-2"
            style={styles.centerElement}
            onClick={(e) => {
              e.preventDefault();
              onClick("Cards");
            }}
          >
            <i
              className="fa-regular fa-credit-card"
              style={{ marginRight: "10px", paddingLeft: "5px" }}
            ></i>
            Cards
          </div>
        </li>
        <li className="nav-item mb-1">
          <div
            className="px-1 py-2"
            style={styles.centerElement}
            onClick={(e) => {
              e.preventDefault();
              onClick("Passwords");
            }}
          >
            <i
              className="fa-solid fa-key "
              style={{ marginRight: "10px", paddingLeft: "5px" }}
            ></i>
            Passwords
          </div>
        </li>
        <li className="sidebar-item  nav-item mb-1">
          <div
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
          </div>
          <ul
            id="settings"
            className="sidebar-dropdown list-unstyled collapse"
            data-bs-parent="#sidebar"
          >
            <li className="sidebar-item">
              <div to="/" className="sidebar-link" style={styles.centerElement}>
                <i
                  className="fas fa-user-plus"
                  style={{ marginRight: "10px", paddingLeft: "5px" }}
                ></i>
                <span className="topic">Edit Profile</span>
              </div>
            </li>
            <li className="sidebar-item">
              <div className="sidebar-link" style={styles.centerElement}>
                <i
                  className="fas fa-sign-out-alt"
                  style={{ marginRight: "15px", paddingLeft: "5px" }}
                ></i>
                <span className="topic">Log Out</span>
              </div>
            </li>
          </ul>
        </li>
      </ul>
      <hr />
      <div className="d-flex" style={styles.centerElement}>
        <i className="fa-solid fa-user mx-2"></i>
        <span
          data-bs-toggle="collapse"
          data-bs-target="#settings"
          aria-expanded="false"
          aria-controls="settings"
        >
          <h6 className="mt-1 mb-0">Sandeep Mishra</h6>
        </span>
      </div>
    </div>
  );
};

export default DisplaySidebar;
