import "./App.css";
import CredentialsVerification from "./components/authentication/CredentialsVerification";
import EmailChecker from "./components/authentication/EmailChecker";
import UserFormFillup from "./components/authentication/UserFormFillup";
import Welcome from "./components/authentication/Welcome";
import { Routes, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function App() {
  const navigate = useNavigate();

  return (
    <div style={{ height: "100vh" }}>
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route
          path="/authentication/email"
          element={
            <EmailChecker
              onSuccessVerification={(authResponse) => {
                if (authResponse.success && authResponse.userExist) {
                  localStorage.setItem("EmailId", authResponse.email);
                  navigate("/authentication/verification");
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
                    localStorage.setItem("EmailId", JSON.stringify(data));
                  }
                  // Move to display page
                }
              }}
            />
          }
        />
        <Route
          path="/authentication/registration"
          element={<UserFormFillup />}
        />
      </Routes>
    </div>
  );
}

export default App;
