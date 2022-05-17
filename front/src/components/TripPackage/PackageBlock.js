import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const Pack = styled.div`
  width: 40%;
  height: 25%;
  text-align: center;
  margin: auto;
  color: white;
  img {
    width: 300px;
    height: 85%;
  }
`;

function PackageBlock({ url, title, price, id }) {
  const tripPackageId = id;
  console.log(tripPackageId);

  return (
    <Pack>
      <img alt={title} src={url} />
      <br />
      <span>{title}</span>
      <br />
      <span>{price}</span>
    </Pack>
  );
}

export default PackageBlock;
