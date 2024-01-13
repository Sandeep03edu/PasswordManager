import "./App.css";
import CredentialsVerification from "./components/authentication/CredentialsVerification";
import EmailChecker from "./components/authentication/EmailChecker";
import UserFormFillup from "./components/authentication/UserFormFillup";
import Welcome from "./components/authentication/Welcome";
import { Routes, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import ToastDisplay from "./components/utils/Toast";
import { useEffect, useState } from "react";
import DisplayCredentialsPage from "./components/credentials/DisplayCredentialPage";
import AddCardPage from "./components/credentials/Card/AddCardPage";
import AddDisplayPasswordPage from "./components/credentials/Password/AddDisplayPasswordPage";
import DisplayFullCreditCard from "./components/credentials/Card/DisplayFullCreditCard";
import DisplayFullPassword from "./components/credentials/Password/DisplayFullPassword";

function App() {
  const navigate = useNavigate();
  const [toastMessage, setToastMessage] = useState("");
  const [toastVariant, setToastVariant] = useState("Warning");

  useEffect(() => {
    if (toastMessage.length > 0) {
      setTimeout(() => {
        setToastMessage("");
      }, 3000);
    }
  }, [toastMessage]);

  return (
    <div style={{ position: "relative", width: "100%", height: "100%" }}>
      <div style={{ height: "100vh" }}>
        <Routes>
          <Route path="/" element={<Welcome />} />
          <Route
            path="/authentication/email"
            element={
              <EmailChecker
                onSuccessVerification={(authResponse) => {
                  if (authResponse.success && authResponse.userExist) {
                    setToastMessage("Email Id registered!!");
                    setToastVariant("Success");

                    localStorage.setItem("EmailId", authResponse.email);
                    navigate("/authentication/verification");
                  } else {
                    setToastMessage("User doesn't exist");
                    setToastVariant("Primary");

                    localStorage.setItem("EmailId", authResponse.email);
                    navigate("/authentication/registration");
                  }
                }}
              />
            }
          />
          <Route
            path="/authentication/verification"
            element={
              <CredentialsVerification
                onSuccessValidation={(data) => {
                  if (data.success) {
                    // User logged in successfully!!
                    delete data["loginPin"];
                    delete data["appPin"];
                    console.log("Dataaa");
                    console.log(data);

                    if (localStorage.getItem("RememberMe")) {
                      localStorage.setItem("UserData", JSON.stringify(data));
                    }
                    // Move to display page
                    setToastMessage("Login Successful");
                    setToastVariant("Success");
                    navigate("/credential/display");
                  } else {
                    setToastMessage(data.error);
                    setToastVariant("Danger");
                  }
                }}
              />
            }
          />
          <Route
            path="/authentication/registration"
            element={<UserFormFillup />}
          />
          <Route
            path="/credential/display"
            element={<DisplayCredentialsPage />}
          />
          <Route
            path="/credential/display/card"
            element={<DisplayFullCreditCard />}
          />
          <Route
            path="/credential/display/password"
            element={<DisplayFullPassword />}
          />
          <Route path="/credential/add/card" element={<AddCardPage />} />
          <Route
            path="/credential/add/password"
            element={<AddDisplayPasswordPage />}
          />
        </Routes>
      </div>

      <div
        style={{
          position: "absolute",
          bottom: "20px",
          right: "20px",
          zIndex: "1000",
          width: "400px",
        }}
      >
        {toastMessage.length !== 0 ? (
          <ToastDisplay title={toastMessage} toastVariant={toastVariant} />
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export default App;
