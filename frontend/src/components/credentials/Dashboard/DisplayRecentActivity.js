import React from "react";
import DisplayRecentActivityItem from "./DisplayRecentActivityItem";

const DisplayRecentActivity = () => {
  const recentActivity = [1, 2, 3, 4, 5, 6, 6, 6, 6, 6];

  return (
    <div className="">
      {recentActivity.map((item, idx) => {
        return <DisplayRecentActivityItem />;
      })}
    </div>
  );
};

export default DisplayRecentActivity;
