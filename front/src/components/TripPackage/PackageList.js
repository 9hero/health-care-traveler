import axios from "axios";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import PackageBlock from "./PackageBlock";

const PackageListBlock = styled.div`
  margin: 2rem 0px;
  width: 100%;
  height: 800px;
  background: green;
  display: flex;
  display: flex;
  flex-wrap: wrap;
  align-content: space-around;
`;

function PackageList() {
  const [tripPackages, setTripPackages] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const getPackages = async () => {
      try {
        setLoading(true);
        setTripPackages(null);
        const response = await axios.get("/api/trip-package/");
        setTripPackages(response.data);
      } catch (error) {}
      setLoading(false);
    };
    getPackages();
  }, []);

  if (loading) return <div>로딩중</div>;
  if (!tripPackages) return null;
  return (
    <PackageListBlock>
      {tripPackages.map((tripPackage) => {
        return (
          <PackageBlock
            key={tripPackage.id}
            id={tripPackage.id}
            title={tripPackage.title}
            price={tripPackage.price}
            url={tripPackage.thumbnail.url}
          />
        );
      })}
    </PackageListBlock>
  );
}

export default PackageList;
