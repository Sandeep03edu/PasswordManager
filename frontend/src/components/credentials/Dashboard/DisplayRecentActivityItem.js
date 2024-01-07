import React from "react";

const DisplayRecentActivityItem = () => {
  return (
    <div
      className="m-2"
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <div
        style={{
          display: "flex",
          alignItems: "center",
        }}
      >
        <i
          className="fa-solid fa-gauge-high p-0 me-2"
          style={{ fontSize: "1.75rem" }}
        ></i>

        <div
          className="ms-2 p-2"
          style={{ display: "flex", flexDirection: "column" }}
        >
          <p className="m-0 p-0" style={{ fontSize: "1rem" }}>
            Updated Card
          </p>
          <p className="m-0 p-0" style={{ fontSize: "0.9rem" }}>
            Card Number : 12212121
          </p>
        </div>
      </div>
      <p className="me-2 my-0 ms-0 p-0" style={{ fontSize: "0.8rem" }}>
        Just Now
      </p>
    </div>
  );
};

export default DisplayRecentActivityItem;
