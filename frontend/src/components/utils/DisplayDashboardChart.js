import React from "react";
import { Bar, Line } from "react-chartjs-2";

import { Chart, registerables } from "chart.js";
Chart.register(...registerables);

const DisplayDashboardChart = () => {
  const datesArray = Array.from({ length: 31 }, (_, index) => index + 1);
  const randomValuesArray = Array.from({ length: 31 }, () =>
    Math.floor(Math.random() * 11)
  );

  return (
    <div
      className="m-3 bg-white rounded"
      style={
        {
          // height: "auto",
          // maxHeight: "400px",
          // minWidth: "400px",
          // display: "inline-block",
        }
      }
    >
      <Line
        datasetIdKey="id"
        data={{
          labels: datesArray,
          datasets: [
            {
              id: 1,
              label: "December 2023",
              data: randomValuesArray,
              backgroundColor: "#ff00ff",
            },
          ],
        }}
        // options={{ maintainAspectRatio: true }}
        // width={100}
        // height={100}
      />
    </div>
  );
};

export default DisplayDashboardChart;
