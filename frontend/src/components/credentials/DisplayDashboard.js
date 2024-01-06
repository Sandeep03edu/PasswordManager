import React from "react";

const DisplayDashboard = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light pt-3 pb-2">
      <div className="container-fluid">
        <p className="navbar-brand m-0 p-0">
          <i className="fa-solid fa-gauge-high p-0 me-2"></i>
          Dashboard
        </p>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div
          className="collapse navbar-collapse justify-content-end"
          id="navbarNav"
        >
          <ul className="navbar-nav">
            <li className="nav-item me-1">
              <button className="btn btn-primary me-2">
                <i class="fa-solid fa-circle-plus me-2"></i>
                Card
              </button>
            </li>
            <li className="nav-item">
              <button className="btn btn-primary">
                <i class="fa-solid fa-circle-plus me-2"></i>
                Password
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default DisplayDashboard;
