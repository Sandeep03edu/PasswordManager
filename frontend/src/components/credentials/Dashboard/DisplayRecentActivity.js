import React, { useState } from "react";
import DisplayRecentActivityItem from "./DisplayRecentActivityItem";
import PinEntry from "../PinEntry";

const DisplayRecentActivity = () => {
  const recentActivity = [1, 2, 3, 4, 5, 6, 6, 6, 6, 6];

  const handleRowClick = (id) => {
    // Handle click action here based on the row ID
    setPinEntryShowModal(true);
  };

  const [showPinEntryModal, setPinEntryShowModal] = useState(false);
  const handleCloseModal = () => {
    setPinEntryShowModal(false);
  };

  return (
    <div className="">
      {recentActivity.map((item, idx) => {
        return (
          <div
            key={idx}
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
        showModal={showPinEntryModal}
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
