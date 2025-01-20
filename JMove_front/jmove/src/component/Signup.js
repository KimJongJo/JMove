import "../css/Signup.css";
import { Link } from "react-router-dom";

function Signup() {
  return (
    <div className="signup">
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
                <input type="text" className="signup-input"></input>
              </td>
              <td>
                <button type="button" className="signup-btn">
                  중복검사
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">여기</span>
              </td>
              <td></td>
            </tr>
            <tr>
              <td>
                <span className="signup-column-span">비밀번호</span>
              </td>
              <td>
                <input type="password" className="signup-input"></input>
              </td>
            </tr>
            <tr></tr>
            <tr>
              <td>
                <span className="signup-column-span">비밀번호 확인</span>
              </td>
              <td>
                <input type="password" className="signup-input"></input>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">여기</span>
              </td>
              <td></td>
            </tr>
            <tr>
              <td>
                <span className="signup-column-span">이메일</span>
              </td>
              <td>
                <input type="email" className="signup-input"></input>
              </td>
              <td>
                <button type="button" className="signup-btn">
                  전송
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">여기</span>
              </td>
              <td></td>
            </tr>
            <tr>
              <td>
                <span className="signup-column-span">인증번호</span>
              </td>
              <td>
                <input type="password" className="signup-input"></input>
              </td>
              <td>
                <button type="button" className="signup-btn">
                  인증
                </button>
              </td>
            </tr>
            <tr>
              <td></td>
              <td>
                <span className="check-span">여기</span>
              </td>
              <td></td>
            </tr>
          </tbody>
        </table>
        <button type="button" className="signup-end-btn">
          가입하기
        </button>
      </div>
    </div>
  );
}

export default Signup;
