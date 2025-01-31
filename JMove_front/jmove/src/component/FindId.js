import "../css/FindId.css";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";
import Header from "./Header";

function FindId() {
  const [email, setEmail] = useState("");

  // 이메일 정규화 패턴
  const EMAIL_PATTERN = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  const sendEmailAPI = () => {
    if (email === "") {
      alert("이메일을 입력해주세요");
      return;
    }

    if (!EMAIL_PATTERN.test(email)) {
      alert("이메일 양식을 확인해주세요");
      return;
    }

    // 존재하는 이메일인지 확인하기
    axios
      .get("http://localhost:8080/users/check-email", {
        params: { email: email },
      })
      .then((response) => {
        console.log(response);
        if (response.data.code === 1) {
          alert("입력하신 이메일에 회원 아이디를 보냈습니다.");
          axios
            .post("http://localhost:8080/send-email", {
              email: email,
              type: "findId",
            })
            .then((response) => console.log(response));
        } else {
          alert("존재하지 않는 이메일 입니다.");
        }
      });
  };

  return (
    <div className="find-id">
      <div className="header-bar">
        <Header />
      </div>
      <div className="home-div">
        <Link to="/">
          <i className="fa-solid fa-house"></i>
        </Link>
      </div>
      <div className="signup-div">
        <Link to="/">
          <div className="title">
            <span className="span-J">J</span>
            <span className="span-Move">Move</span>
          </div>
        </Link>
        <span className="signup-span">아이디 찾기</span>
        <p className="findId-content">
          사용자가 입력한 이메일로 아이디를 전송합니다.
        </p>
        <div>
          <span className="findId-span">이메일</span>
          <input
            type="email"
            className="findId-input"
            onChange={(e) => setEmail(e.target.value)}
          ></input>
        </div>
        <button type="button" className="findId-btn" onClick={sendEmailAPI}>
          이메일 전송
        </button>
      </div>
    </div>
  );
}

export default FindId;
