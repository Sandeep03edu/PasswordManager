import React, { useEffect, useRef } from "react";
import { Line } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
import { useState } from "react";

Chart.register(...registerables);

const DisplayDashboardChart = ({ setComponentHeight }) => {
  const datesArray = Array.from({ length: 31 }, (_, index) => index + 1);
  const randomValuesArray = Array.from({ length: 31 }, () =>
    Math.floor(Math.random() * 11)
  );

  const chartContainerRef = useRef(null);
  let timeoutRef = useRef(null);

  const [pastHeight, setPastHeight] = useState(-1);

  useEffect(() => {
    const handleResize = () => {
      if (chartContainerRef.current) {
        const height = chartContainerRef.current.offsetHeight;
        clearTimeout(timeoutRef.current);
        timeoutRef.current = setTimeout(() => {
          if (pastHeight !== height) {
            setComponentHeight(height);
            setPastHeight(height);
          }
        }, 30); // Adjust the delay time as needed
      }
    };

    const observer = new ResizeObserver(handleResize);

    if (chartContainerRef.current) {
      observer.observe(chartContainerRef.current);
    }

    return () => {
      observer.disconnect();
      clearTimeout(timeoutRef.current);
    };
  }, [setPastHeight]);

  return (
    <div className="m-3 bg-white rounded px-2">
      <div ref={chartContainerRef}>
        <div
          className="px-2 pt-2 mx-2"
          style={{
            width: "100%",
            fontSize: "1.25rem",
          }}
        >
          Monthly Activity
        </div>
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
        />
      </div>
    </div>
  );
};

export default DisplayDashboardChart;
