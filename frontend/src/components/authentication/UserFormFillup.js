import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import credentialsBkg from "../../images/credentialsLoginBkg2.png";

const UserFormFillup = () => {
  const styles = {
    fullHeight: {
      height: "100vh",
    },
    imageColumn: {
      padding: 0,
      display: "flex",
      justifyContent: "center",
      backgroundColor: "#f8f9fa", // Background color for form column
    },
    image: {
      width: "100vh",
      height: "100vh",
      //   objectFit: "cover",
      backgroundSize: "100%",
    },
    formColumn: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
    },
    formContent: {
      textAlign: "center",
      padding: "20px",
    },
    appName: {
      fontSize: "2rem",
      marginBottom: "20px",
    },
    appDescription: {
      fontSize: "1.2rem",
      marginBottom: "30px",
      fontWeight: "300",
    },
    eyeIcon: {
      cursor: "pointer",
      marginLeft: "5px",
    },
    inputContainer: {
      display: "flex",
      alignItems: "center",
      position: "relative",
    },
    inputIcon: {
      position: "absolute",
      right: "10px",
      top: "50%",
      transform: "translateY(-50%)",
    },
  };

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [loginPin, setLoginPin] = useState("");
  const [appPin, setAppPin] = useState("");
  const [showLoginPin, setShowLoginPin] = useState(false);
  const [showAppPin, setShowAppPin] = useState(false);

  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };

  const handleLoginPinChange = (e) => {
    const newPin = e.target.value.replace(/\D/g, ""); // Allow only numeric values
    if (newPin.length <= 4) {
      setLoginPin(newPin);
    }
  };

  const handleAppPinChange = (e) => {
    const newPin = e.target.value.replace(/\D/g, ""); // Allow only numeric values
    if (newPin.length <= 6) {
      setAppPin(newPin);
    }
  };

  const toggleShowLoginPin = () => {
    setShowLoginPin(!showLoginPin);
  };

  const toggleShowAppPin = () => {
    setShowAppPin(!showAppPin);
  };

  return (
    <div style={styles.fullHeight}>
      <div className="container-fluid" style={styles.fullHeight}>
        <div className="row" style={styles.fullHeight}>
          <div className="col-md-8" style={styles.imageColumn}>
            <img
              src={credentialsBkg}
              alt="App Login page"
              style={styles.image}
            />
          </div>
          <div className="col-md-4" style={styles.formColumn}>
            <div style={styles.formContent}>
              <h1 style={styles.appName}>Password Manager</h1>
              <p style={styles.appDescription}>
                A Secure way to store your credentials
              </p>
              <form>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="firstName"
                    placeholder="First Name"
                    value={firstName}
                    onChange={handleFirstNameChange}
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="lastName"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={handleLastNameChange}
                  />
                </div>
                <div className="mb-3" style={styles.inputContainer}>
                  <input
                    type={showLoginPin ? "text" : "password"}
                    className="form-control"
                    id="loginPin"
                    placeholder="Enter Login Pin"
                    value={loginPin}
                    onChange={handleLoginPinChange}
                    pattern="[0-9]*" // Allow only numeric input
                  />
                  <FontAwesomeIcon
                    icon={showLoginPin ? faEyeSlash : faEye}
                    style={{ ...styles.eyeIcon, ...styles.inputIcon }}
                    onClick={toggleShowLoginPin}
                  />
                </div>
                <div className="mb-3" style={styles.inputContainer}>
                  <input
                    type={showAppPin ? "text" : "password"}
                    className="form-control"
                    id="appPin"
                    placeholder="Enter App Pin"
                    value={appPin}
                    onChange={handleAppPinChange}
                    pattern="[0-9]*" // Allow only numeric input
                  />
                  <FontAwesomeIcon
                    icon={showAppPin ? faEyeSlash : faEye}
                    style={{ ...styles.eyeIcon, ...styles.inputIcon }}
                    onClick={toggleShowAppPin}
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary"
                  style={{ width: "100%" }}
                >
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserFormFillup;
