import React, { useState } from "react";
import PinEntry from "../PinEntry";

const DisplayRecentCredential = () => {
  const dummyData = [
    {
      id: 1,
      title: "State Bank of India",
      type: "Card",
      userDetails: "**** **** 6677",
      updatedAt: "10/01/2023 08:30",
    },
    {
      id: 2,
      title: "Gmail",
      type: "Password",
      userDetails: "sandee******",
      updatedAt: "10/01/2023 08:30",
    },

    {
      id: 3,
      title: "Facebook",
      type: "Password",
      userDetails: "sandee*******",
      updatedAt: "10/01/2023 08:30",
    },
    // ... dummy data for more rows
  ];

  const handleRowClick = (id) => {
    // Handle click action here based on the row ID
    console.log(`Row clicked: ${id}`);
    setPinEntryShowModal(true);
  };

  const [showPinEntryModal, setPinEntryShowModal] = useState(false);
  const handleCloseModal = () => {
    setPinEntryShowModal(false);
  };

  return (
    <div className="m-0 bg-white rounded px-2">
      <h4 className="pb-3 pt-3">Recent Credentials</h4>
      <div className="" style={{ width: "100%", overflowX: "auto" }}>
        <div className="container m-0" style={{ minWidth: "100%" }}>
          <table className="table " style={{ width: "100%" }}>
            <thead className=" " style={{ background: "#b3b3ff" }}>
              <tr>
                <th>Title</th>
                <th>Type</th>
                <th>User Detail</th>
                <th>Updated at</th>
                <th className="text-center">Action</th>{" "}
              </tr>
            </thead>
            <tbody>
              {dummyData.map((data) => (
                <tr
                  key={data.id}
                  onClick={() => handleRowClick(data.id)}
                  style={{
                    textDecoration: "none",
                    color: "inherit",
                    cursor: "pointer", // Set cursor to pointer
                    transition: "background-color 0.3s", // Add smooth transition
                  }}
                  onMouseEnter={(e) => {
                    e.currentTarget.style.backgroundColor =
                      "rgba(0, 0, 0, 0.05)"; // Add transparent background color on hover
                  }}
                  onMouseLeave={(e) => {
                    e.currentTarget.style.backgroundColor = "transparent"; // Remove background color on mouse leave
                  }}
                >
                  <td>{data.title}</td>
                  <td>{data.type}</td>
                  <td>{data.userDetails}</td>
                  <td>{data.updatedAt}</td>
                  <td
                    style={{
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                    }}
                  >
                    <button className="btn btn-link text-decoration-none me-1">
                      <i className="fas fa-pencil-alt fs-5 me-1"></i> Edit
                    </button>
                    <button
                      className="btn btn-link text-decoration-none"
                      style={{ color: "#ff0000" }}
                    >
                      <i className="fas fa-trash fs-5 me-1"></i> Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <div className="row mt-3 justify-content-end">
        <div className="col-auto">
          <button className="btn btn-link text-decoration-none me-2">
            <i className="fas fa-chevron-left fs-5"></i> Prev
          </button>
          <span>1</span> {/* Current Page Number */}
          <button className="btn btn-link text-decoration-none ms-2">
            Next <i className="fas fa-chevron-right fs-5"></i>
          </button>
        </div>
      </div>
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

export default DisplayRecentCredential;
