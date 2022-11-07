import React from "react";
import Header from "../components/Header";
import styled from "styled-components";
import PackageList from "../components/TripPackage/PackageList";
import Footer from "../components/Footer";
import NoticeBoard from "../components/NoticeBoard";

const MainBlock = styled.div`
  width: 70%;
  margin: auto;
`;

function Main() {
  return (
    <MainBlock>
      <Header></Header>
      {/* <PackageList></PackageList> */}
      <NoticeBoard />
      <Footer />
    </MainBlock>
  );
}

export default Main;
