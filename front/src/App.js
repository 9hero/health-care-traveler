import React, { useEffect, useState } from "react";
import logo from "./logo.svg";
import "./App.css";
import axios from "axios";

function App() {
  const [restText, setRestText] = useState("");
  useEffect(() => {
    console.log("useEffect");
    axios.get("/api/helo/").then((response) => {
      const { name } = response.data;
      setRestText(name);
    });
  });
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        hello {restText}
        <a href="http://localhost:8080/swagger-ui/index.html#/">look swagger</a>
      </header>
      {restText}
    </div>
  );
}

export default App;
