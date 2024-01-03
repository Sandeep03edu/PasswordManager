import "./App.css";
import CredentialsVerification from "./components/authentication/CredentialsVerification";
import EmailChecker from "./components/authentication/EmailChecker";
import UserFormFillup from "./components/authentication/UserFormFillup";
import Welcome from "./components/authentication/Welcome";

function App() {
  return (
    <div style={{ height: "100vh" }}>
      {/* <Welcome /> */}
      {/* <EmailChecker/> */}

      {/* <CredentialsVerification
        onChangeLoginPin={(pin) => {
          console.log(`Pin1 value:: ${pin}`);
        }}
        onChangeAppPin={(pin) => {
          console.log(`Pin2 value:: ${pin}`);
        }} */
      }
      
      <UserFormFillup/>
      
    </div>
  );
}

export default App;
