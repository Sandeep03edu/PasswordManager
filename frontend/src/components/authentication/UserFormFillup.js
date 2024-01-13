import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import credentialsBkg from "../../images/credentialsLoginBkg2.png";
import axios from "axios";
import { BASE_URL, EndPoints } from "../utils/NetworkEndPoints";
import { useToastState } from "../context/ToastContext";
import { useLocation, useNavigate } from "react-router-dom";
import { getUser, getUserToken } from "../utils/UserInfo";

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

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const [editable, setEditable] = useState(queryParams.get("editable"));
  const loggedUser = getUser();

  const [firstName, setFirstName] = useState(
    editable ? loggedUser.firstName : ""
  );
  const [lastName, setLastName] = useState(editable ? loggedUser.lastName : "");
  const [loginPin, setLoginPin] = useState("");
  const [appPin, setAppPin] = useState("");

  const [showLoginPin, setShowLoginPin] = useState(false);
  const [showAppPin, setShowAppPin] = useState(false);

  const [firstNameError, setFirstNameError] = useState("");
  const [loginPinError, setLoginPinError] = useState("");
  const [appPinError, setAppPinError] = useState("");

  const validateForm = () => {
    validateInput();
    return firstNameError === "" && loginPinError === "" && appPinError === "";
  };

  const { updateToastState } = useToastState();
  const navigate = useNavigate();

  const validateInput = () => {
    // Reset errors initially
    setFirstNameError("");
    setLoginPinError("");
    setAppPinError("");

    // Check conditions and update errors accordingly
    if (firstName.trim() === "") {
      setFirstNameError("First name must not be empty");
    } else if (firstName.length < 3) {
      setFirstNameError("First name should have at least 3 characters");
    }

    if (loginPin.trim() === "" || !/^\d{4}$/.test(loginPin)) {
      setLoginPinError("Login pin should be a 4-digit number");
    }

    if (appPin.trim() === "" || !/^\d{6}$/.test(appPin)) {
      setAppPinError("App pin should be a 6-digit number");
    }
  };

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

  const registerUser = async (user) => {
    try {
      const config = {
        headers: {
          "Content-type": "application/json",
        },
      };

      const { data } = await axios.post(
        `${BASE_URL}/${EndPoints.registerUser}`,
        user,
        config
      );

      console.log(data);

      if (data.success) {
        navigate("/credential/display");
        const userInfo = {
          appPin: data.appPin,
          email: data.email,
          firstName: data.firstName,
          lastName: data.lastName,
          loginPin: data.loginPin,
          token: data.token,
          _id: data._id,
        };

        localStorage.setItem("UserData", JSON.stringify(userInfo));
      } else {
        updateToastState({ message: data.error, variant: "Danger" });
      }
    } catch (error) {
      updateToastState({ message: error, variant: "Danger" });
    }
  };

  const updateUser = async (user) => {
    try {
      const config = {
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${getUserToken()}`,
        },
      };

      const { data } = await axios.post(
        `${BASE_URL}/${EndPoints.updateUser}`,
        user,
        config
      );

      console.log(data);

      if (data.success) {
        navigate("/credential/display");
        const userInfo = {
          appPin: data.appPin,
          email: data.email,
          firstName: data.firstName,
          lastName: data.lastName,
          loginPin: data.loginPin,
          token: data.token,
          _id: data._id,
        };

        localStorage.setItem("UserData", JSON.stringify(userInfo));
      } else {
        updateToastState({ message: data.error, variant: "Danger" });
      }
    } catch (error) {
      updateToastState({ message: error, variant: "Danger" });
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (validateForm()) {
      const userDetals = {
        email: localStorage.getItem("EmailId"),
        firstName,
        lastName,
        loginPin,
        appPin,
      };

      if (editable) {
        updateUser(userDetals);
      } else {
        registerUser(userDetals);
      }
    }
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
              <form
                onClick={(e) => {
                  e.preventDefault();
                  validateInput();
                }}
              >
                <div className="mb-3" style={{ textAlign: "start" }}>
                  <input
                    type="text"
                    className="form-control"
                    id="firstName"
                    placeholder="First Name"
                    value={firstName}
                    onChange={handleFirstNameChange}
                  />
                  {firstNameError && (
                    <p
                      className="text-danger mx-2"
                      style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                    >
                      {firstNameError}
                    </p>
                  )}
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
                <div className="mb-3" style={{ textAlign: "start" }}>
                  <div style={styles.inputContainer}>
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
                  {loginPinError && (
                    <p
                      className="text-danger mx-2"
                      style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                    >
                      {loginPinError}
                    </p>
                  )}
                </div>
                <div className="mb-3" style={{ textAlign: "start" }}>
                  <div className="" style={styles.inputContainer}>
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
                  {appPinError && (
                    <p
                      className="text-danger mx-2"
                      style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                    >
                      {appPinError}
                    </p>
                  )}
                </div>

                <button
                  type="submit"
                  className="btn btn-primary"
                  style={{ width: "100%" }}
                  onClick={(e) => {
                    handleSubmit(e);
                  }}
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
