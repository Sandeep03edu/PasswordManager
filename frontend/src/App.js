import "./App.css";
import EmailChecker from "./components/authentication/EmailChecker";
import PinEntries from "./components/authentication/PinEntries";
import Welcome from "./components/authentication/Welcome";

function App() {
  return (
    <div style={{ height: "100vh" }}>
      {/* <Welcome /> */}
      {/* <EmailChecker/> */}
      <PinEntries
        onChangeLoginPin={(pin) => {
          console.log(`Pin1 value:: ${pin}`);
        }}
        onChangeAppPin={(pin) => {
          console.log(`Pin2 value:: ${pin}`);
        }}
      />
    </div>
  );
}

export default App;
