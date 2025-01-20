import "../css/FindId.css";
import { Link } from "react-router-dom";

function FindId() {
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
        <span className="signup-span">아이디 찾기</span>
        <p className="findId-content">
          사용자가 입력한 이메일로 아이디를 전송합니다.
        </p>
        <div>
          <span className="findId-span">이메일</span>
          <input type="email" className="findId-input"></input>
        </div>
        <button type="button" className="findId-btn">
          이메일 전송
        </button>
      </div>
    </div>
  );
}

export default FindId;
