import React from "react";
import "./Sidebar.css";

const DisplaySidebar = () => {
  const styles = {
    centerElement: {
      display: "flex",
      alignItems: "center",
    },
  };
  return (
    <div id="bdSidebar" className="text-white offcanvas-md offcanvas-start">
      <div
        className="p-3 "
        style={{
          width: "100%",
          minHeight: "100vh",
          background: "#00004d",
        }}
      >
        <p className="mx-2 my-0 p-0">Password Manager</p>
        <hr />
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
            height: "100%",
          }}
        >
          <ul className="mynav nav nav-pills flex-column mb-auto">
            <li className="nav-item mb-1">
              <div
                to="/credential/display/dashboard"
                style={styles.centerElement}
              >
                <i className="fa-solid fa-chart-line mx-1"></i>
                Dashboard
              </div>
            </li>
            <li className="nav-item mb-1">
              <div to="/credential/display/cards" style={styles.centerElement}>
                <i className="fa-regular fa-credit-card mx-1"></i>
                Cards
              </div>
            </li>
            <li className="nav-item mb-1">
              <div
                to="/credential/display/passwords"
                style={styles.centerElement}
              >
                <i className="fa-solid fa-key mx-1"></i>
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
                  <div
                    to="/"
                    className="sidebar-link"
                    style={styles.centerElement}
                  >
                    <i className="fas fa-user-plus pe-2 mx-1"></i>
                    <span className="topic">Edit Profile</span>
                  </div>
                </li>
                <li className="sidebar-item">
                  <div
                    to="/"
                    className="sidebar-link"
                    style={styles.centerElement}
                  >
                    <i className="fas fa-sign-out-alt pe-2 mx-1"></i>
                    <span className="topic">Log Out</span>
                  </div>
                </li>
              </ul>
            </li>
          </ul>
          <hr />

          <div className="" style={styles.centerElement}>
            <i className="fa-solid fa-user mx-2"></i>
            <span>
              <h6 className="mt-1 mb-0">Sandeep Mishra</h6>
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DisplaySidebar;
