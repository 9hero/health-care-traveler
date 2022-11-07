import axios from "axios";
import React from "react";
import { useSearchParams } from "react-router-dom";

function PaymentCheckTest() {
  const authToken =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFzZGZmZHNhIiwiZXhwIjoxNjY3ODA4Mzc0fQ.zzXdd7wxLQlvVaR9jll_CXYy_nLyPYBhhdLSynajers";
  const [searchParams] = useSearchParams();
  const merchant_uid = searchParams.get("merchant_uid"); // test
  const imp_uid = searchParams.get("imp_uid"); // test

  function callback(response) {
    const { success, merchant_uid, error_msg } = response;

    if (success) {
      const getPosts = async () => {
        try {
          const response = await axios.post(
            `http://localhost:8080/api/payment/verification?imp_uid=${imp_uid}&merchant_uid=${merchant_uid}`,
            null,
            { headers: { Authorization: "Bearer " + authToken } }
          );
          console.log(response);
        } catch (error) {
          console.log(error);
        }
      };
      getPosts();
      alert("결제 성공");
    } else {
      alert(`결제 실패: ${error_msg}, ${merchant_uid}`);
    }
  }
  return (
    <div>
      <button onClick={() => callback({ success: true, merchant_uid })}>
        검증시도
      </button>
    </div>
  );
}

export default PaymentCheckTest;
