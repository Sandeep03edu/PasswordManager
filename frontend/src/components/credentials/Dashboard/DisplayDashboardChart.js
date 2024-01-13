import React, { useEffect, useRef } from "react";
import { Line } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
import { useState } from "react";
import { getUserToken } from "../../utils/UserInfo";
import axios from "axios";
import { BASE_URL, EndPoints } from "../../utils/NetworkEndPoints";
import { useToastState } from "../../context/ToastContext";

Chart.register(...registerables);

const DisplayDashboardChart = ({ setComponentHeight }) => {
  const { updateToastState} = useToastState

  const getDaysInCurrentMonth = () => {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth() + 1; // Months are zero-based, so we add 1

    // Create a new date for the first day of the next month
    const firstDayOfNextMonth = new Date(year, month, 1);

    // Subtracting 1 day from the first day of the next month gives the last day of the current month
    const lastDayOfCurrentMonth = new Date(firstDayOfNextMonth - 1);

    // Extract the day component to get the total number of days
    const numberOfDays = lastDayOfCurrentMonth.getDate();

    return numberOfDays;
  };

  const getCurrentMonthAndYear = () => {
    const currentDate = new Date();
    const month = currentDate.toLocaleString("default", { month: "long" });
    const year = currentDate.getFullYear();

    return month + " " + year;
  };

  const datesArray = Array.from(
    { length: getDaysInCurrentMonth() },
    (_, index) => index + 1
  );

  const [dateWiseCredentialsCount, setDateWiseCredentialsCount] = useState(
    Array.from({ length: getDaysInCurrentMonth() }, (_, index) => 0)
  );

  const getMonthlyActivity = async () => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
        },
      };

      const { data } = await axios.get(
        `${BASE_URL}/${EndPoints.fetchMonthlyCredentialsData}`,
        config
      );

      if (data.success) {
        setDateWiseCredentialsCount(data.data);
      }
      else {
        updateToastState({ message: data.error, variant: "Danger" });
      }
    } catch (error) {
      updateToastState({ message: error, variant: "Danger" });
    }
  };

  useEffect(() => {
    getMonthlyActivity();
  }, []);

  // Height adjustment
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
                label: getCurrentMonthAndYear(),
                data: dateWiseCredentialsCount,
                backgroundColor: "#ff00ff",
              },
            ],
          }}
          options={{
            scales: {
              y: {
                ticks: {
                  stepSize: 1, // Set the step size to 1 to ensure integer values
                  precision: 0, // Set precision to 0 to remove decimal places
                },
                max: Math.max(...dateWiseCredentialsCount) + 2, // Set the maximum y-tick value
              },
            },
          }}
        />
      </div>
    </div>
  );
};

export default DisplayDashboardChart;
