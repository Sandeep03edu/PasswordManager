import React from "react";

const Welcome = (props) => {
  return (
    <div className="welcome-container">
      <h2>Welcome to Password Manager</h2>
      <p>
        This app is designed to make your life easier. Experience the best
        features and services tailored for you.
      </p>
      <div style={{ width: "100%" }}>
        <button className="get-started-button">Get Started</button>
      </div>
    </div>
  );
};

export default Welcome;
