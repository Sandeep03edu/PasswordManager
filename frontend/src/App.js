import "./App.css";
import CredentialsVerification from "./components/authentication/CredentialsVerification";
import EmailChecker from "./components/authentication/EmailChecker";
import UserFormFillup from "./components/authentication/UserFormFillup";
import Welcome from "./components/authentication/Welcome";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <div style={{ height: "100vh" }}>
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route
          path="/authentication/email"
          element={
            <EmailChecker
              onSuccessVerification={(authResponse) => {
                console.log("AuthResponse:: " + authResponse);
              }}
            />
          }
        />
        <Route
          path="/authentication/verification"
          element={
            <CredentialsVerification
              onChangeLoginPin={(pin) => {
                console.log(`Pin1 value:: ${pin}`);
              }}
              onChangeAppPin={(pin) => {
                console.log(`Pin2 value:: ${pin}`);
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
