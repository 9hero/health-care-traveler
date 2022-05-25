import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const HeaderBlock = styled.header`
  width: 100%;
  margin: auto;
  margin-top: 1.5rem;
  background: gray;
  height: 12.5%;
  .logo {
    width: 200px;
    height: 50px;
    background-color: purple;
    margin: 5%;
  }
  nav {
    display: flex;
    width: 100%;
    /* margin: auto; */
    margin-top: 3rem;
    background-color: aqua;
  }

  nav ul {
    width: 100%;
    list-style: none;
    display: flex;
    justify-content: space-evenly;
  }

  nav ul li {
    display: inline-block;
    width: 200px;
    background-color: wheat;
    text-align: center;
    margin: 0 auto;
  }

  .userMenu {
    width: 15%;
    height: 5%;
    background: wheat;
    justify-self: flex-end;
    font-size: 0.8rem;
  }
`;
function Header() {
  return (
    <HeaderBlock>
      <div class="logo">로고</div>
      <div class="userMenu">
        <Link to={"/signin"}>로그인</Link>/
        <Link to={"/signup"}>회원가입</Link>/
        <Link to={"/mypage"}>마이페이지</Link>
      </div>
      <nav>
        <ul>
          <li>홈</li>
          <li>예약</li>
          <li>고객지원</li>
        </ul>
      </nav>
    </HeaderBlock>
  );
}

export default Header;
