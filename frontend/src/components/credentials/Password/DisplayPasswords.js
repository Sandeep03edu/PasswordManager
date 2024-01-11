import React, { useState } from "react";
import { passwords } from "../../utils/DummyData";
import PinEntry from "../PinEntry";

const DisplayPasswords = () => {
  const [showModal, setShowModal] = useState(false);

  const handleRowClick = (id) => {
    // Handle click action here based on the row ID
    console.log(`Row clicked: ${id}`);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const styles = {
    dataFont: {
      fontSize: "0.9rem",
    },
    buttonFont: {
      fontSize: "1rem",
    },
    centerElements: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
    },
  };

  return (
    <div className="p-0 m-0">
      <nav className="navbar navbar-expand-lg navbar-light bg-light pt-1 pb-1">
        <div className="container-fluid">
          <p className="navbar-brand m-0 p-0">
            <i className="fa-solid fa-key p-0 me-2"></i>
            Your Passwords
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
                <button className="btn btn-primary m-2">
                  <i className="fa-solid fa-circle-plus me-2"></i>
                  Password
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div className="row my-0 m-2 p-0">
        <div
          className="m-0 bg- rounded px-2 py-3 mt-2"
          style={{ background: "#ffffff" }}
        >
          <div className="" style={{ width: "100%", overflowX: "auto" }}>
            <div className="container m-0" style={{ minWidth: "100%" }}>
              <table className="table " style={{ width: "100%" }}>
                <thead className=" " style={{ background: "#b3b3ff" }}>
                  <tr>
                    <th>Title</th>
                    <th>Url</th>
                    <th>Username</th>
                    <th>EmailId</th>
                    {/* <th>Password</th>
                    <th>Pin</th> */}
                    <th>Tags</th>
                    <th>Updated At</th>
                    <th className="text-center">Action</th>
                  </tr>
                </thead>
                <tbody>
                  {passwords.map((data) => (
                    <tr
                      key={data.id}
                      onClick={() => handleRowClick(data.id)}
                      style={{
                        textDecoration: "none",
                        color: "inherit",
                        cursor: "pointer",
                        transition: "background-color 0.3s",
                      }}
                      onMouseEnter={(e) => {
                        e.currentTarget.style.backgroundColor =
                          "rgba(0, 0, 0, 0.05)";
                      }}
                      onMouseLeave={(e) => {
                        e.currentTarget.style.backgroundColor = "transparent";
                      }}
                    >
                      <td style={styles.dataFont}>{data.title || "N/A"}</td>
                      <td
                        style={styles.dataFont}
                        className="custom-url-container"
                      >
                        {data.url ? (
                          <a
                            href={data.url}
                            target="_blank"
                            rel="noopener noreferrer"
                            style={{ textDecoration: "none" }}
                          >
                            {data.url}
                          </a>
                        ) : (
                          "N/A"
                        )}
                      </td>
                      <td style={styles.dataFont}>{data.username || "N/A"}</td>
                      <td style={styles.dataFont}>{data.email || "N/A"}</td>
                      {/* <td style={styles.dataFont}>{data.password || "N/A"}</td>
                      <td style={styles.dataFont}>{data.pin || "N/A"}</td> */}
                      <td style={styles.dataFont}>
                        {`[${data.tags}]` || "N/A"}
                      </td>
                      <td style={styles.dataFont}>
                        {`${data.updatedAt.slice(0, 10)}` || "N/A"}
                      </td>
                      <td style={styles.centerElements}>
                        <button
                          className="btn btn-link text-decoration-none me-1"
                          style={{ display: "flex", alignItems: "center" }}
                        >
                          <i
                            className="fas fa-pencil-alt  me-1"
                            style={styles.dataFont}
                          ></i>
                          <p className="m-0 p-0" style={styles.dataFont}>
                            Edit
                          </p>
                        </button>
                        <button
                          className="btn btn-link text-decoration-none"
                          style={{
                            color: "#ff0000",
                            display: "flex",
                            alignItems: "center",
                          }}
                        >
                          <i
                            className="fas fa-trash me-1"
                            style={styles.dataFont}
                          ></i>
                          <div className="m-0 p-0" style={styles.dataFont}>
                            Delete
                          </div>
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
                <i
                  className="fas fa-chevron-left"
                  style={styles.buttonFont}
                ></i>{" "}
                Prev
              </button>
              <span>1</span>
              <button className="btn btn-link text-decoration-none ms-2">
                Next{" "}
                <i
                  className="fas fa-chevron-right"
                  style={styles.buttonFont}
                ></i>
              </button>
            </div>
          </div>
        </div>
      </div>

      <PinEntry
        showModal={showModal}
        handleClose={handleCloseModal}
        keyTitle={"App Pin"}
        keyLimit={6}
      />
    </div>
  );
};

export default DisplayPasswords;
