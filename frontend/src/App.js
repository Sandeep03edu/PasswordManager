import "./App.css";
import EmailChecker from "./components/authentication/EmailChecker";
import Welcome from "./components/authentication/Welcome";

function App() {
  return (
    <div style={{ height: "100vh" }}>
      {/* <Welcome /> */}
      <EmailChecker/>
    </div>
  );
}

export default App;
