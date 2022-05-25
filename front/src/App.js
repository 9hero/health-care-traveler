import React from "react";
import { Route, Routes } from "react-router";
import Main from "./pages/Main";
import MyPage from "./pages/MyPage";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";

function App() {
  return(
  <Routes>
    <Route index element={<Main />}/>
    <Route path="/signin" element={<SignIn/>}/>
    <Route path="/signup" element={<SignUp/>}/>
    <Route path="/mypage" element={<MyPage/>}/>
  </Routes>)
}

export default App;
