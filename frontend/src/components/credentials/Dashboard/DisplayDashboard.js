import React, { useEffect, useState } from "react";
import DisplayDashboardChart from "./DisplayDashboardChart";
import DisplayRecentActivity from "./DisplayRecentActivity";
import DisplayRecentCredential from "./DisplayRecentCredential";
import { useNavigate } from "react-router-dom";
import { getUserToken } from "../../utils/UserInfo";
import { BASE_URL, EndPoints } from "../../utils/NetworkEndPoints";
import axios from "axios";

const DisplayDashboard = ({ onClick }) => {
  const countData = [
    {
      count: 0,
      type: "Cards",
      icon: "fa-solid fa-credit-card",
    },
    {
      count: 0,
      type: "Passwords",
      icon: "fa-solid fa-key",
    },
    {
      count: 0,
      type: "Credentials",
      icon: "fa-solid fa-unlock-keyhole",
    },
  ];

  const fetchCredentialCount = async () => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
        },
      };

      const { data } = await axios.get(
        `${BASE_URL}/${EndPoints.fetchCredentialCount}`,
        config
      );

      if (data.success) {
        setCredentialCountArray(data.data);
      }
    } catch (error) {}
  };

  useEffect(() => {
    fetchCredentialCount();
  }, []);

  const [credentialCountArray, setCredentialCountArray] = useState(countData);

  const [chartHeight, setChartHeight] = useState(0);
  const navigate = useNavigate();

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-light bg-light pt-1 pb-1">
        <div className="container-fluid">
          <p className="navbar-brand m-0 p-0">
            <i className="fa-solid fa-gauge-high p-0 me-2"></i>
            Dashboard
          </p>
          <div
            className="navbar-toggler m-0 p-0"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#addCredentailDiv"
            aria-controls="addCredentailDiv"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <button className="btn btn-primary py-1 px-2">
              <i className="fa-solid fa-circle-plus"></i>
            </button>
          </div>
          <div
            className="collapse navbar-collapse justify-content-end "
            id="addCredentailDiv"
          >
            <ul className="navbar-nav">
              <li className="nav-item me-1">
                <button
                  className="btn btn-primary m-2"
                  onClick={() => {
                    navigate("/credential/add/card");
                  }}
                >
                  <i className="fa-solid fa-circle-plus me-2"></i>
                  Card
                </button>
              </li>
              <li className="nav-item">
                <button
                  className="btn btn-primary m-2"
                  onClick={() => {
                    navigate("/credential/add/password");
                  }}
                >
                  <i className="fa-solid fa-circle-plus me-2"></i>
                  Password
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div className="container mt-4">
        <div className="row ">
          {credentialCountArray.map((item, index) => (
            <div
              key={index}
              className="col-lg-4 col-md-6 mb-3"
              onClick={() => {
                onClick(item.type === "Credentials" ? "Dashboard" : item.type);
              }}
              style={{
                cursor: "pointer",
              }}
            >
              <div
                className="px-3 py-4 bg-white rounded mx-3"
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "flex-start",
                }}
              >
                <div
                  className="filled-circle me-2"
                  style={{
                    minWidth: "3rem",
                    minHeight: "3rem",
                    borderRadius: "50%",
                    backgroundColor: "blue",
                    flexWrap: "nowrap",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    color: "white",
                  }}
                >
                  <i
                    className={item.icon}
                    style={{
                      fontSize: "1.5rem", // Adjust the font size as needed
                      width: "1.5rem",
                      height: "1.5rem",
                    }}
                  ></i>
                </div>

                <div
                  style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "flex-start",
                    justifyContent: "center",
                    width: "100%",
                  }}
                  className="ms-2"
                >
                  <h5 className="m-0 p-0">{item.count}</h5>
                  <p className="m-0 p-0">{item.type}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="row m-0" style={{ height: "100%" }}>
        <div className="col-lg-7 col-md-6">
          <DisplayDashboardChart
            setComponentHeight={(value) => {
              if (value !== chartHeight) {
                setChartHeight(value);
              }
            }}
          />
        </div>

        <div className="col-lg-5 col-md-6 my-3 ">
          <div className="bg-white rounded me-2 px-2">
            <div
              className="px-2 pt-2"
              style={{
                width: "100%",
                fontSize: "1.25rem",
              }}
            >
              Recent Activity
            </div>
            <div
              style={{
                height: `${chartHeight - 50}px`,
                margin: "10px",
                overflowY: "auto",
              }}
            >
              <DisplayRecentActivity />
            </div>
          </div>
        </div>
      </div>

      <div className="m-2">
        <DisplayRecentCredential />
      </div>

      <div style={{ height: "100px" }} />
    </>
  );
};

export default DisplayDashboard;
