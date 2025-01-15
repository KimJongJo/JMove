import Modal from "./Modal";
import "../css/Main.css";
import React, { useState, useEffect } from "react";

function Main() {
  const [poster, setPoster] = useState([]); // 포스터 목록
  const [currentIndex, setCurrentIndex] = useState(0); // 현재 슬라이드 인덱스
  const [isModalOpen, setModalOpen] = useState(false); // 모달 열기/닫기 상태

  // 포스터 데이터 가져오기
  useEffect(() => {
    fetch("http://localhost:8080/poster")
      .then((response) => response.json())
      .then((data) => {
        const backdropPaths = data.results.map((item) => item.backdrop_path);
        setPoster(backdropPaths);
        console.log(backdropPaths);
      });
  }, []);

  // 자동 이미지 슬라이드

  // 모달 열기
  const modalOpen = () => {
    setModalOpen(true);
  };

  // 모달 닫기
  const modalClose = () => {
    setModalOpen(false);
  };

  return (
    <div className="Main">
      <div className="header-bar">
        <input type="text" placeholder="영화 검색" className="search-box" />
        <button type="button" className="search-btn">
          검색
        </button>
        <button onClick={modalOpen} type="button" className="login-btn">
          로그인
        </button>
        {isModalOpen && <Modal onClose={modalClose} />}
      </div>

      <div className="main-poster">
        <div className="main-poster-left">
          <span className="title-first">J</span>
          <span className="title-last">Move</span>
          <div className="comment-box">
            <a>
              React와 JPA를 공부하면서 프론트엔드/백엔드가 분리된 환경으로
              개발한 영화 추천 사이트입니다.
              <br />
              <br />
              백엔드 - 로그인 기능을 구현하여 회원마다 영화를 저장해서 담아둘 수
              있습니다.
              <br />
              - TMDB API를 이용한 요청/응답에 대한 데이터를 가공합니다.
              <br />
              프론트엔드 - 반응형 웹을 통해 해상도에 따라 화면 크기, 배치도의
              변화가 있습니다.
              <br />- 사용자의 움직임으로 인한 모든 애니메이션을 동작합니다.
            </a>
          </div>
        </div>

        <div className="main-poster-right">
          {poster.length > 0 &&
            poster.map((image, index) => (
              <img
                key={index}
                src={`https://image.tmdb.org/t/p/w500${image}`} // 슬라이드에 맞는 이미지 경로
                alt="movie poster"
                className={`slide-image ${
                  index === currentIndex ? "active" : "inactive"
                }`}
              />
            ))}
        </div>
      </div>
    </div>
  );
}

export default Main;
