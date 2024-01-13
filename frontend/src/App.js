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
import { useToastState } from "./components/context/ToastContext";

function App() {
  const navigate = useNavigate();

  const { toastState, updateToastState } = useToastState();

  useEffect(() => {
    if (toastState !== null) {
      setTimeout(() => {
        updateToastState(null);
      }, 3000);
    }
  }, [toastState]);

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
                    updateToastState({
                      message: "Email Id registered!!",
                      variant: "Success",
                    });

                    localStorage.setItem("EmailId", authResponse.email);
                    navigate("/authentication/verification");
                  } else {
                    updateToastState({
                      message: "User doesn't exist",
                      variant: "Primary",
                    });

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
                    updateToastState({
                      message: "Login Successful",
                      variant: "Success",
                    });
                    navigate("/credential/display");
                  } else {
                    updateToastState({
                      message: data.error,
                      variant: "Danger",
                    });
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
        {toastState !== null ? (
          <ToastDisplay
            title={toastState.message}
            toastVariant={toastState.variant}
          />
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export default App;
