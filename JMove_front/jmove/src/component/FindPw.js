import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";

function FindPw() {
  const navigate = useNavigate();

  // 상태 설정
  const [checkIn, setCheckIn] = useState(false); // 아이디와 이메일 확인 여부
  const [id, setId] = useState(""); // 아이디 상태
  const [email, setEmail] = useState(""); // 이메일 상태
  const [pw, setPw] = useState(""); // 비밀번호 상태
  const [pwCh, setPwCh] = useState(""); // 비밀번호 확인 상태

  // 아이디와 이메일을 이용해 회원 존재 여부 확인
  const CheckId = () => {
    axios
      .post("http://localhost:8080/users/checkIdEmail", {
        id: id,
        email: email,
      })
      .then((response) => {
        if (response.data.code === 1) {
          alert("비밀번호를 변경해주세요.");
          setPw(""); // 비밀번호 초기화
          setPwCh(""); // 비밀번호 확인 초기화
          setCheckIn(true); // 비밀번호 변경 폼 표시
        } else {
          alert("존재하지 않는 회원입니다.");
        }
      })
      .catch((error) => {
        console.error("Error checking ID and email: ", error);
        alert("아이디 또는 이메일 확인 중 오류가 발생했습니다.");
      });
  };

  // 비밀번호 정규화 패턴
  const PASSWORD_PATTERN = /^(?=.*\d)[A-Za-z\d!@#$%^&*]{8,20}$/;

  // 비밀번호 변경 처리
  const updatePw = () => {
    // 비밀번호 형식 검사
    if (!PASSWORD_PATTERN.test(pw)) {
      alert("비밀번호는 8~20자, 공백 없이 숫자 최소 1개 포함해야 합니다.");
      return;
    }

    // 비밀번호 확인 일치 여부 검사
    if (pw !== pwCh) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    // 비밀번호 변경 API 호출
    axios
      .patch("http://localhost:8080/users/update", {
        id: id,
        email: email,
        pw: pw,
      })
      .then((response) => {
        if (response.data.code === 1) {
          alert("비밀번호가 변경되었습니다.");
          navigate("/"); // 홈으로 이동
        } else {
          alert("현재 사용중인 비밀번호입니다.");
        }
      })
      .catch((error) => {
        console.error("Error updating password: ", error);
        alert("서버와 연결할 수 없습니다. 나중에 다시 시도해주세요.");
      });
  };

  return (
    <div className="find-id">
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
        <span className="signup-span">비밀번호 찾기</span>
        <p className="findId-content">
          사용자가 입력한 아이디와 이메일을 회원DB에 해당하는 회원이 존재하는
          경우 비밀번호를 변경할 수 있게 해줍니다.
        </p>
        <table>
          {checkIn ? (
            <tbody>
              <tr>
                <td>
                  <span className="findId-span">비밀번호</span>
                </td>
                <td>
                  <input
                    type="password"
                    className="findId-input"
                    onChange={(e) => setPw(e.target.value)} // 비밀번호 상태 업데이트
                    value={pw} // 상태값으로 입력값 동기화
                  />
                </td>
              </tr>
              <tr>
                <td>
                  <span className="findId-span">비밀번호 확인</span>
                </td>
                <td>
                  <input
                    type="password"
                    className="findId-input"
                    onChange={(e) => setPwCh(e.target.value)} // 비밀번호 확인 상태 업데이트
                    value={pwCh} // 상태값으로 입력값 동기화
                  />
                </td>
              </tr>
            </tbody>
          ) : (
            <tbody>
              <tr>
                <td>
                  <span className="findId-span">아이디</span>
                </td>
                <td>
                  <input
                    type="text"
                    className="findId-input"
                    onChange={(e) => setId(e.target.value)} // 아이디 상태 업데이트
                    value={id} // 상태값으로 입력값 동기화
                  />
                </td>
              </tr>
              <tr>
                <td>
                  <span className="findId-span">이메일</span>
                </td>
                <td>
                  <input
                    type="email"
                    className="findId-input"
                    onChange={(e) => setEmail(e.target.value)} // 이메일 상태 업데이트
                    value={email} // 상태값으로 입력값 동기화
                  />
                </td>
              </tr>
            </tbody>
          )}
        </table>

        {checkIn ? (
          <button type="button" className="findId-btn" onClick={updatePw}>
            변경
          </button>
        ) : (
          <button type="button" className="findId-btn" onClick={CheckId}>
            계정확인
          </button>
        )}
      </div>
    </div>
  );
}

export default FindPw;
