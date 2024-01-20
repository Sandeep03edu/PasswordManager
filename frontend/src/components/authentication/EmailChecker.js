import React, { useEffect, useState } from "react";
import axios from "axios";
import emailCheckerBkg from "../../images/emailCheckerBkg.jpeg";
import { BASE_URL, EndPoints } from "../utils/NetworkEndPoints";
import { useToastState } from "../context/ToastContext";

const EmailChecker = ({ onSuccessVerification }) => {
  // Define styles as objects

  const { updateToastState } = useToastState();

  const styles = {
    fullHeight: {
      height: "100vh",
    },
    imageColumn: {
      padding: 0,
      display: "flex",
      justifyContent: "center",
      backgroundColor: "#FFCAB8", // Background color for form column
    },
    image: {
      width: "100%",
      height: "100%",
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
  };

  const checkEmail = async (e) => {
    e.preventDefault();
    if (!email) {
      return;
    }

    setLoading(true);

    try {
      const headerConfig = {
        headers: {
          "Content-type": "application/json",
        },
      };

      const { data } = await axios.post(
        `${BASE_URL}/${EndPoints.emailExist}`,
        {
          email,
        },
        headerConfig
      );
      localStorage.setItem("RememberMe", rememberMe);
      setLoading(false);
      onSuccessVerification(data);
    } catch (error) {
      updateToastState({ message: error.toString(), variant: "Danger" });
      setLoading(false);
    }
  };

  // Clearing LocalStorage RememberMe Value
  useEffect(() => {
    localStorage.setItem("RememberMe", false);
  }, []);

  const [rememberMe, setRememberMe] = useState(false);
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const handleRememberMeChange = () => {
    setRememberMe(!rememberMe);
  };

  return (
    <div style={styles.fullHeight}>
      <div className="container-fluid" style={styles.fullHeight}>
        <div className="row" style={styles.fullHeight}>
          <div className="col-md-8" style={styles.imageColumn}>
            <img
              src={emailCheckerBkg}
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
                <div className="mb-2">
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    placeholder="Email address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="form-check mb-3" style={{ display: "flex" }}>
                  <input
                    type="checkbox"
                    className="form-check-input "
                    id="rememberMe"
                    checked={rememberMe}
                    onChange={handleRememberMeChange}
                    style={{ marginRight: "10px" }}
                  />
                  <label className="form-check-label" htmlFor="rememberMe">
                    Remember Me
                  </label>
                </div>
                <button
                  type="submit"
                  className="btn btn-primary"
                  style={{ width: "100%" }}
                  onClick={checkEmail}
                  disabled={loading}
                >
                  Continue
                </button>

                <button
                  type="submit"
                  className="btn btn-danger my-2"
                  style={{ width: "100%" }}
                  disabled={loading}
                >
                  Guest Login
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default EmailChecker;
