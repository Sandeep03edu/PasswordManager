import React, { useState } from "react";
import DisplayRecentActivityItem from "./DisplayRecentActivityItem";
import PinEntry from "../PinEntry";

const DisplayRecentActivity = () => {
  const recentActivity = [1, 2, 3, 4, 5, 6, 6, 6, 6, 6];

  const handleRowClick = (id) => {
    // Handle click action here based on the row ID
    console.log(`Row clicked: ${id}`);
    setShowModal(true);
  };

  const [showModal, setShowModal] = useState(false);
  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <div className="">
      {recentActivity.map((item, idx) => {
        return (
          <div
            onClick={() => {
              handleRowClick(item._id);
            }}
            style={{ cursor: "pointer" }}
          >
            <DisplayRecentActivityItem />
          </div>
        );
      })}
      <PinEntry
        showModal={showModal}
        handleClose={handleCloseModal}
        keyTitle={"App Pin"}
        keyLimit={6}
        handleResponse={() => {
          console.log("Response");
        }}
      />
    </div>
  );
};

export default DisplayRecentActivity;
