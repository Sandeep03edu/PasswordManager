import React, { useState } from "react";
import { Link } from "react-router-dom";
import DisplaySidebar from "./DisplaySidebar";
import DisplayDashboard from "./DisplayDashboard";
import DisplayCards from "./DisplayCards";
import DisplayPasswords from "./DisplayPasswords";

const DisplayCredentialPage = () => {
  const [displayType, setDisplayType] = useState("Dashboard");

  return (
    <div className="container-fluid p-0 d-flex h-100">
      <DisplaySidebar
        onClick={(type) => {
          setDisplayType(type);
        }}
      />

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
          className=""
          style={{ background: "#e6e6e6", minHeight: "100vh", width: "100%" }}
        >
          {/* Page Content */}
          {displayType === "Dashboard" ? (
            <DisplayDashboard />
          ) : displayType === "Cards" ? (
            <DisplayCards />
          ) : displayType === "Passwords" ? (
            <DisplayPasswords />
          ) : (
            <></>
          )}
        </div>
      </div>
    </div>
  );
};

export default DisplayCredentialPage;
