import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";

function FindPw() {
  const [checkIn, setCheckIn] = useState(false);
  const [button, setButton] = useState();

  useEffect(() => {
    if (checkIn) {
      setButton("변경");
    } else {
      setButton("계정확인");
    }
  }, [checkIn]);

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
                  <input type="password" className="findId-input"></input>
                </td>
              </tr>
              <tr>
                <td>
                  <span className="findId-span">비밀번호 확인</span>
                </td>
                <td>
                  <input type="password" className="findId-input"></input>
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
                  <input type="text" className="findId-input"></input>
                </td>
              </tr>
              <tr>
                <td>
                  <span className="findId-span">이메일</span>
                </td>
                <td>
                  <input type="email" className="findId-input"></input>
                </td>
              </tr>
            </tbody>
          )}
        </table>
        <button type="button" className="findId-btn">
          {button}
        </button>
      </div>
    </div>
  );
}

export default FindPw;
