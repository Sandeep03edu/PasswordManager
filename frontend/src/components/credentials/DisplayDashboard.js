import React from "react";
import DisplayDashboardChart from "../utils/DisplayDashboardChart";

const DisplayDashboard = () => {
  const countData = [
    {
      count: 15,
      type: "Cards",
      icon: "fa-solid fa-credit-card",
    },
    {
      count: 10,
      type: "Passwords",
      icon: "fa-solid fa-key",
    },
    {
      count: 25,
      type: "Credentials",
      icon: "fa-solid fa-unlock-keyhole",
    },
  ];

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-light bg-light pt-3 pb-2">
        <div className="container-fluid">
          <p className="navbar-brand m-0 p-0">
            <i className="fa-solid fa-gauge-high p-0 me-2"></i>
            Dashboard
          </p>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className="collapse navbar-collapse justify-content-end "
            id="navbarNav"
          >
            <ul className="navbar-nav">
              <li className="nav-item me-1">
                <button className="btn btn-primary m-2">
                  <i class="fa-solid fa-circle-plus me-2"></i>
                  Card
                </button>
              </li>
              <li className="nav-item">
                <button className="btn btn-primary m-2">
                  <i class="fa-solid fa-circle-plus me-2"></i>
                  Password
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div className="container mt-4">
        <div className="row ">
          {countData.map((item, index) => (
            <div key={index} className="col-lg-4 col-md-6 mb-3">
              <div
                className="px-3 py-4 bg-white rounded mx-3"
                style={{
                  display: "flex",
                  alignItems: "center",
                }}
              >
                <div
                  class="filled-circle me-2"
                  style={{
                    minWidth: "60px",
                    minHeight: "60px",
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
                      fontSize: "30px", // Adjust the font size as needed
                      width: "30px",
                      height: "30px",
                    }}
                  ></i>
                </div>

                <div
                  style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    width: "100%",
                  }}
                >
                  <h5 className="m-0 p-0">{item.count}</h5>
                  <p className="m-0 p-0">{item.type}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="row m-0">
        <div className="col-lg-8 col-md-6">
          <DisplayDashboardChart />
        </div>
        <div className="col-lg-4 col-md-6">
          <div className="m-2">Hello gaiz</div>
        </div>
      </div>
    </>
  );
};

export default DisplayDashboard;
