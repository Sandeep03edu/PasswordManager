import React, { useState } from "react";
import DisplaySidebar from "./DisplaySidebar";
import DisplayDashboard from "./Dashboard/DisplayDashboard";
import DisplayCards from "./Card/DisplayCards";
import DisplayPasswords from "./Password/DisplayPasswords";

const DisplayCredentialPage = () => {
  const [displayType, setDisplayType] = useState("Passwords");

  return (
    <div className="container-fluid p-0 d-flex h-100">
      <div style={{ width: "fit-content" }}>
        <DisplaySidebar
          onClick={(type) => {
            setDisplayType(type);
          }}
        />
      </div>

      <div className="bg-light flex-fill" style={{ overflowY: "auto" }}>
        <div
          className="p-2 d-md-none d-flex text-white "
          style={{ background: "#00004d" }}
        >
          <div
            className="text-white"
            data-bs-toggle="offcanvas"
            data-bs-target="#bdSidebar"
          >
            <i className="fa-solid fa-bars"></i>
          </div>
          <span className="ms-3">Password Manager</span>
        </div>

        <div
          className="m-0 p-0"
          style={{ background: "#e6e6e6", minHeight: "100vh", width: "100%" }}
        >
          {/* Page Content */}
          {displayType === "Dashboard" ? (
            <DisplayDashboard
              onClick={(type) => {
                setDisplayType(type);
              }}
            />
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
