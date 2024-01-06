import React from "react";
import { Link } from "react-router-dom";
import DisplaySidebar from "./DisplaySidebar";

const DisplayCredentialDashboard = () => {
  return (
    <div className="container-fluid p-0 d-flex h-100">
      <DisplaySidebar />

      <div className="bg-light flex-fill">
        <div className="p-2 d-md-none d-flex text-white bg-success">
          <Link
            to="/"
            className="text-white"
            data-bs-toggle="offcanvas"
            data-bs-target="#bdSidebar"
          >
            <i className="fa-solid fa-bars"></i>
          </Link>
          <span className="ms-3">Password Manager</span>
        </div>

        <div
          className="p-4"
          style={{ background: "#ff00ff", minHeight: "100vh" }}
        >
          {/* Page Content */}
        </div>
      </div>
    </div>
  );
};

export default DisplayCredentialDashboard;
