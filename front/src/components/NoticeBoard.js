import axios from "axios";
import React, { useEffect, useState } from "react";
import styled from "styled-components";

const NoticeBoardBlock = styled.table`
  width: 100%;
  /* height: 200px; */
  background-color: yellow;
  border-top: 1px solid black;
  text-align: center;
  caption {
    background-color: aliceblue;
  }
`;

function NoticeBoard() {
  const [posts, setPosts] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const getPosts = async () => {
      try {
        setLoading(true);
        setPosts(null);
        const response = await axios.get(
          "http://3.39.185.254/api/notice-board"
        );
        console.log(response);
        setPosts(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    getPosts();
  }, []);

  if (loading) return <div>로딩중</div>;
  if (!posts) return null;
  return (
    <NoticeBoardBlock>
      <caption>공지사항</caption>
      <tr>
        <th>글번호</th>
        <th>제목</th>
        <th>작성일</th>
      </tr>
      {posts.map((post) => {
        return (
          <tr>
            <td>{post.id}</td>
            <td>{post.title}</td>
            <td>{post.createdAt}</td>
          </tr>
        );
      })}
    </NoticeBoardBlock>
  );
}

export default NoticeBoard;
