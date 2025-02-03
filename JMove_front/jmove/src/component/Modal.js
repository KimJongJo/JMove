import "../css/Modal.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import axios from "axios";

function Modal({ onClose }) {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  const login = () => {
    if (id === "" || pw === "") {
      alert("아이디와 비밀번호를 입력해주세요");
      return;
    }
    axios
      .post("http://localhost:8080/users/login", {
        id: id,
        pw: pw,
      })
      .then((response) => {
        console.log(response);
      });
  };

  return (
    <div className="back">
      <div className="modal">
        <div className="x-div">
          <i onClick={onClose} className="fa-solid fa-xmark"></i>
        </div>
        <div>
          <span className="modal-title-first">J</span>
          <span className="modal-title-last">Move</span>
        </div>
        <div className="user-info">
          <table>
            <tbody>
              <tr>
                <td>
                  <span className="span">아이디</span>
                </td>
                <td>
                  <input
                    className="input"
                    type="text"
                    onChange={(e) => setId(e.target.value)}
                  ></input>
                </td>
                <td rowSpan={2}>
                  <button
                    type="button"
                    className="modal-login-btn"
                    onClick={login}
                  >
                    로그인
                  </button>
                </td>
              </tr>
              <tr>
                <td>
                  <span className="span">비밀번호</span>
                </td>
                <td>
                  <input
                    className="input"
                    type="password"
                    onChange={(e) => setPw(e.target.value)}
                  ></input>
                </td>
              </tr>
            </tbody>
          </table>
          <div>
            <Link to="/find-id" onClick={onClose}>
              <span className="span">아이디찾기</span>
            </Link>
            <span className="span"> / </span>
            <Link to="/find-pw" onClick={onClose}>
              <span className="span">비밀번호찾기</span>
            </Link>
            <span className="span"> / </span>
            <Link to="/signup" onClick={onClose}>
              <span className="span">회원가입</span>
            </Link>
          </div>
          <button className="login kakao">카카오 계정으로 로그인</button>
          <button className="login naver">네이버 아이디로 로그인</button>
          <button className="login google">구글 계정으로 로그인</button>
        </div>
      </div>
    </div>
  );
}

export default Modal;
