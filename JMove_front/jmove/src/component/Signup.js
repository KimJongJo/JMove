import "../css/Signup.css";
import { Link, useNavigate } from "react-router-dom";
import React, { useState, useEffect } from "react";
import axios from "axios";
import Header from "./Header";

function Signup() {
  const navigate = useNavigate();

  const [id, setId] = useState("");
  const [checkId, setCheckId] = useState("");
  const [pw, setPw] = useState("");
  const [pwch, setPwch] = useState("");
  const [checkPw, setCheckPw] = useState("");
  const [email, setEmail] = useState("");
  const [auth, setAuth] = useState("");
  const [authMessage, setAuthMessage] = useState("");
  const [sendTime, setSendTime] = useState(null); // 타이머 상태 추가
  const [timer, setTimer] = useState(null); // setInterval을 위한 상태

  // 회원가입을 하기에 적절한 정보인지 확인
  const [checkAll, setCheckAll] = useState({
    id: false,
    pw: false,
    email: false,
    auth: false,
  });

  const allValid = Object.values(checkAll).every((value) => value === true);

  // 아이디 정규화 패턴
  const USER_ID_PATTERN = /^[A-Za-z0-9+@._-]{4,15}$/;

  // 이메일 정규화 패턴
  const EMAIL_PATTERN = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  const checkIdAPI = () => {
    if (USER_ID_PATTERN.test(id)) {
      axios
        .get("http://localhost:8080/users/check-id", { params: { id: id } })
        .then((response) => {
          setCheckId(response.data.message);
          if (response.data.code === 1) {
            setCheckAll((prev) => ({ ...prev, id: true }));
          } else {
            setCheckAll((prev) => ({ ...prev, id: false }));
          }
        });
    } else {
      setCheckAll((prev) => ({ ...prev, id: false }));
      setCheckId("4~15글자, 대소문자, @ . _ - 가능");
    }
  };

  const handlePwChange = (e) => {
    setPw(e.target.value);
  };

  const handlePwchChange = (e) => {
    setPwch(e.target.value);
  };

  const handleBlur = () => {
    if (pw === "" || pwch === "") {
      return;
    } else {
      if (pw === pwch) {
        setCheckPw("비밀번호가 일치합니다.");
        setCheckAll((prev) => ({ ...prev, pw: true }));
      } else {
        setCheckPw("비밀번호가 일치하지 않습니다.");
        setCheckAll((prev) => ({ ...prev, pw: false }));
      }
    }
  };

  const sendEmailAPI = () => {
    axios
      .post("http://localhost:8080/send-email", {
        email: email,
        type: "signup",
      })
      .then((response) => console.log(response));
  };

  const sendMail = () => {
    setCheckAll((prev) => ({ ...prev, email: false }));
    setCheckAll((prev) => ({ ...prev, auth: false }));

    if (EMAIL_PATTERN.test(email)) {
      setCheckAll((prev) => ({ ...prev, email: true }));
      sendEmailAPI();
      // 타이머 시작
      if (timer) {
        clearInterval(timer); // 이미 타이머가 있다면 이전 타이머를 클리어
      }

      alert("인증번호를 보냈습니다.");

      const startTime = Date.now();
      const endTime = startTime + 5 * 60 * 1000; // 5분 후 시간 설정

      const interval = setInterval(() => {
        const remainingTime = endTime - Date.now(); // 남은 시간 계산
        if (remainingTime <= 0) {
          clearInterval(interval); // 타이머가 끝나면 클리어
          setCheckAll((prev) => ({ ...prev, email: false }));
          setSendTime("시간 초과");
        } else {
          const minutes = Math.floor(remainingTime / 1000 / 60);
          const seconds = Math.floor((remainingTime / 1000) % 60);
          setSendTime(`${minutes}:${seconds < 10 ? "0" + seconds : seconds}`);
        }
      }, 1000); // 1초마다 갱신

      setTimer(interval); // 타이머 상태로 설정
    } else {
      setSendTime("이메일 양식을 확인해주세요");
    }
  };

  const authCheck = () => {
    axios
      .post("http://localhost:8080/auth", {
        authNum: auth,
        email: email,
      })
      .then((response) => {
        if (response.data.code === 1) {
          clearInterval(timer);
          alert("인증되었습니다.");
          setAuthMessage("인증되었습니다.");
          setSendTime(null);
          setCheckAll((prev) => ({ ...prev, auth: true }));
        } else {
          alert("인증번호가 틀립니다.");
        }
      });
  };

  const signupAPI = () => {
    axios
      .post("http://localhost:8080/signup", {
        id: id,
        email: email,
        password: pw,
      })
      .then((response) => {
        if (response.data.code === 1) {
          alert("회원가입이 완료되었습니다.");
          // 완료되면 메인 화면으로 이동
          navigate("/");
        } else {
          alert("회원가입 실패 ...");
          console.log(response);
        }
      });
  };

  const ch = () => {
    if (!allValid) {
      alert("입력 정보를 확인해주세요");
    } else {
      signupAPI();
    }
  };

  return (
    <div className="signup">
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
        <span className="signup-span">회원가입</span>
        <table>
          <tbody>
            <tr>
              <td>
                <span className="signup-column-span">아이디</span>
              </td>
              <td>
                <input
                  type="text"
                  className="signup-input"
                  onChange={(e) => setId(e.target.value)}
                ></input>
              </td>
              <td>
                <button
                  type="button"
                  className="signup-btn"
                  onClick={checkIdAPI}
                >
                  중복검사
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">{checkId}</span>
              </td>
              <td></td>
            </tr>
            <tr>
              <td>
                <span className="signup-column-span">비밀번호</span>
              </td>
              <td>
                <input
                  type="password"
                  className="signup-input"
                  onChange={handlePwChange}
                ></input>
              </td>
            </tr>
            <tr></tr>
            <tr>
              <td>
                <span className="signup-column-span">비밀번호 확인</span>
              </td>
              <td>
                <input
                  type="password"
                  className="signup-input"
                  onChange={handlePwchChange}
                  onBlur={handleBlur} // onBlur 이벤트 추가
                ></input>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">{checkPw}</span>
              </td>
              <td></td>
            </tr>
            <tr>
              <td>
                <span className="signup-column-span">이메일</span>
              </td>
              <td>
                <input
                  type="email"
                  className="signup-input"
                  autoComplete="off"
                  onChange={(e) => setEmail(e.target.value)}
                ></input>
              </td>
              <td>
                <button type="button" className="signup-btn" onClick={sendMail}>
                  전송
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">{sendTime}</span>
              </td>
              <td></td>
            </tr>
            <tr>
              <td>
                <span className="signup-column-span">인증번호</span>
              </td>
              <td>
                <input
                  type="text"
                  className="signup-input"
                  onChange={(e) => setAuth(e.target.value)}
                ></input>
              </td>
              <td>
                <button
                  type="button"
                  className="signup-btn"
                  onClick={authCheck}
                >
                  인증
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">{authMessage}</span>
              </td>
              <td></td>
            </tr>
          </tbody>
        </table>
        <button type="button" className="signup-end-btn" onClick={ch}>
          가입하기
        </button>
      </div>
    </div>
  );
}

export default Signup;
