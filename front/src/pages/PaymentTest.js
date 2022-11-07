import axios from "axios";
import React from "react";
import { useSearchParams } from "react-router-dom";

function PaymentTest() {
  const { IMP } = window;
  IMP.init("imp48022176");

  const [searchParams] = useSearchParams();
  const merchant_uid = searchParams.get("merchant_uid"); // test
  const imp_uid = searchParams.get("imp_uid"); // test

  const payData = {
    pg: "paypal",
    pay_method: "card",
    merchant_uid: "221107R2548", // 상점에서 관리하는 주문 번호
    name: "주문명:결제테스트",
    amount: 150,
    currency: "USD", // 기본값: USD(원화 KRW는 페이팔 정책으로 인해 지원하지 않음)
    buyer_email: "sb-aesai19204397-1@personal.example.com",
    buyer_name: "john doe",
    buyer_tel: "4083641695",
    buyer_addr: "서울특별시 강남구 삼성동",
    buyer_postcode: "123-456",
    m_redirect_url:
      "http://localhost:3002/test/payment/check?imp_uid=imp_852037949730&merchant_uid=221107R2138&imp_success=true", // 예: https://www.my-service.com/payments/complete
  };
  function callback(response) {
    const { success, merchant_uid, error_msg } = response;

    if (success) {
      const getPosts = async () => {
        try {
          const response = await axios.post(
            "http://3.39.185.254/api/notice-board"
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

  const payButton = () => {
    IMP.request_pay(payData, callback);
  };
  return (
    <div>
      결제 테스트 ${merchant_uid}${imp_uid}
      <button onClick={payButton}>결제시도</button>
    </div>
  );
}

export default PaymentTest;
