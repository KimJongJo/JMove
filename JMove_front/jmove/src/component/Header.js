import { Link, useNavigate } from "react-router-dom";
import { useContext, useState, useEffect } from "react";
import { AuthContext } from "../component/AuthContext";
import Modal from "./Modal";
import "../css/Header.css";

function Header({ isModalOpen, modalOpen, modalClose }) {
  const navigate = useNavigate();

  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);
  const [searchMovieName, setSearchMovieName] = useState("");
  // const [isModalOpen, setModalOpen] = useState(false); // 모달 열기/닫기 상태

  // // 모달 열기
  // const modalOpen = () => {
  //   setModalOpen(true);
  // };

  // // 모달 닫기
  // const modalClose = () => {
  //   setModalOpen(false);
  // };

  // 로그아웃 버튼
  const handleLogout = async () => {
    try {
      await fetch("http://localhost:8080/users/logout", {
        method: "POST",
        credentials: "include",
      });

      alert("로그아웃 되었습니다.");
      setIsLoggedIn(false); // 로그아웃 후 상태 변경
      navigate("/");
    } catch (error) {
      console.error("로그아웃 실패", error);
    }
  };

  return (
    <div className="header">
      <div className="header-title">
        <Link to="/">
          <span className="search-title-first">J</span>
          <span className="search-title-last">Move</span>
        </Link>
      </div>
      <div className="header-search-line">
        <input
          type="text"
          placeholder="영화 검색"
          className="search-box"
          onChange={(e) => setSearchMovieName(e.target.value)}
        />
        <Link
          to={"/search?q=" + searchMovieName}
          onClick={(e) => {
            if (!searchMovieName.trim()) {
              e.preventDefault(); // 검색어가 없으면 이동 막기
              alert("검색어를 입력해주세요");
            }
          }}
        >
          <button type="button" className="search-btn">
            검색
          </button>
        </Link>

        {isLoggedIn ? (
          <div className="logout-div">
            <button onClick={handleLogout} type="button" className="logout-btn">
              로그아웃
            </button>
            <Link to="/mypage">
              <button className="myPage-btn">마이페이지</button>
            </Link>
          </div>
        ) : (
          <button onClick={modalOpen} type="button" className="login-btn">
            로그인
          </button>
        )}
        {isModalOpen && (
          <Modal onClose={modalClose} isModalOpen={isModalOpen} />
        )}
      </div>
    </div>
  );
}

export default Header;
