import { Link } from "react-router-dom";
import Modal from "./Modal";
import React, { useState, useEffect } from "react";
import "../css/Header.css";

function Header() {
  const [searchMovieName, setSearchMovieName] = useState("");
  const [isModalOpen, setModalOpen] = useState(false); // 모달 열기/닫기 상태

  // 모달 열기
  const modalOpen = () => {
    setModalOpen(true);
  };

  // 모달 닫기
  const modalClose = () => {
    setModalOpen(false);
  };

  return (
    <div>
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

      <button onClick={modalOpen} type="button" className="login-btn">
        로그인
      </button>
      {isModalOpen && <Modal onClose={modalClose} />}
    </div>
  );
}

export default Header;
