import "../css/Modal.css";
import { Link } from "react-router-dom";
import { useContext, useState } from "react";
import axios from "axios";
import { AuthContext } from "../component/AuthContext";

function Modal({ onClose }) {
  const { setIsLoggedIn } = useContext(AuthContext);
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  const login = async () => {
    if (id === "" || pw === "") {
      alert("아이디와 비밀번호를 입력해주세요");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/users/login",
        { id: id, pw: pw },
        { withCredentials: true } // ✅ 쿠키 포함 (JWT 토큰을 저장하기 위해 필요)
      );

      alert("로그인 되었습니다.");
      // console.log(response);

      setIsLoggedIn(true); // ✅ 로그인 상태 업데이트
      onClose(); // 모달 닫기
    } catch (error) {
      alert("아이디와 비밀번호가 틀렸습니다.");
      console.error("로그인 실패:", error);
    }
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
              <span className="span menu">아이디찾기</span>
            </Link>
            <span className="span"> / </span>
            <Link to="/find-pw" onClick={onClose}>
              <span className="span menu">비밀번호찾기</span>
            </Link>
            <span className="span"> / </span>
            <Link to="/signup" onClick={onClose}>
              <span className="span menu">회원가입</span>
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
