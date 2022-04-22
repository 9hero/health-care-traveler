import React,{useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';

function App() {
  const [restText, setRestText] = useState("");
  useEffect(() => {
    console.log("useEffect");
    axios.get("/api/helo/").then((response) => {
      const { name,name2}=response.data;
      setRestText(name);
    });
  });
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        hello {restText}
      </header>
      {restText}
    </div>
  );
}

export default App;
