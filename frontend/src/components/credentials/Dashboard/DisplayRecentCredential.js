import React, { useState, useEffect } from "react";
import PinEntry from "../PinEntry";
import { getUserToken } from "../../utils/UserInfo";
import axios from "axios";
import { BASE_URL, EndPoints } from "../../utils/NetworkEndPoints";
import { capitalizeWords } from "../../utils/ModiyText";

const DisplayRecentCredential = () => {
  const [recentCredentials, setRecentCredentials] = useState([]);

  const handleRowClick = (id) => {
    setPinEntryShowModal(true);
  };

  const [showPinEntryModal, setPinEntryShowModal] = useState(false);
  const handleCloseModal = () => {
    setPinEntryShowModal(false);
  };

  const fetchRecentCredentials = async () => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
        },
      };

      const { data } = await axios.get(
        `${BASE_URL}/${EndPoints.fetchRecentEncryptedCredentials}`,
        config
      );

      if (data.success) {
        setRecentCredentials(data.data);
      }
    } catch (error) {}
  };

  useEffect(() => {
    fetchRecentCredentials();
  }, []);

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
                <th className="text-center">Action</th>
              </tr>
            </thead>
            <tbody>
              {recentCredentials.map((data) => (
                <tr
                  key={data.appId}
                  onClick={() => handleRowClick(data.appId)}
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
                  <td>{capitalizeWords(data.dataTitle)}</td>
                  <td>{data.type}</td>
                  <td>{data.dataUserDetails}</td>
                  <td>{data.updatedAt.slice(0, 10)}</td>
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
