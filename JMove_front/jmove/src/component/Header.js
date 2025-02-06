// Header.js
import { Link, useNavigate } from "react-router-dom";
import React, { useContext, useState } from "react";
import { AuthContext } from "../component/AuthContext";
import Modal from "./Modal";
import "../css/Header.css";

function Header() {
  const navigate = useNavigate();
  const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);
  const [searchMovieName, setSearchMovieName] = useState("");

  // 내부에서 모달 상태 관리
  const [isModalOpen, setModalOpen] = useState(false);
  const modalOpen = () => setModalOpen(true);
  const modalClose = () => setModalOpen(false);

  // 로그아웃 핸들러
  const handleLogout = async () => {
    try {
      await fetch("http://localhost:8080/users/logout", {
        method: "POST",
        credentials: "include",
      });
      alert("로그아웃 되었습니다.");
      setIsLoggedIn(false);
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
              e.preventDefault();
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
