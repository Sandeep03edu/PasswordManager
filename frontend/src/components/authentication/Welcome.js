import React from "react";
import { useNavigate } from "react-router-dom";

const Welcome = (props) => {
  const navigate = useNavigate();

  return (
    <>
      {localStorage.getItem("UserData") ? <></> : <></>}
      <div className="welcome-container">
        <h2>Welcome to Password Manager</h2>
        <p>
          This app is designed to make your life easier. Experience the best
          features and services tailored for you.
        </p>
        <div style={{ width: "100%" }}>
          <button
            className="get-started-button"
            onClick={(e) => {
              e.preventDefault();
              localStorage.getItem("UserData")
                ? navigate("/credential/display")
                : navigate("/authentication/email");
            }}
          >
            Get Started
          </button>
        </div>
      </div>
    </>
  );
};

export default Welcome;
