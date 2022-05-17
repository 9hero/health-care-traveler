import React from "react";
import styled from "styled-components";

const FooterBlock = styled.footer`
  width: 100%;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;

  .foot-1 {
    text-align: center;
    width: 100%;
  }
`;

function Footer() {
  return (
    <FooterBlock>
      <div class="foot-1">소개 | 연락처 | 사이트맵</div>
      <div class="foot-2">이용약관, 회사 정보, sns, 저작권 등</div>
    </FooterBlock>
  );
}

export default Footer;
