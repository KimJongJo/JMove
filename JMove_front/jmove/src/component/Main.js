import "../css/Main.css";
import React, { useState, useEffect } from "react";
import axios from "axios";
import Header from "./Header";

function Main() {
  const [poster, setPoster] = useState([]); // 포스터 목록
  const [movies, setMovie] = useState([]); // 영화 목록
  const [currentIndex, setCurrentIndex] = useState(0); // 현재 슬라이드 인덱스

  const [clickMovie, setClickMovie] = useState(); // 선택한 영화
  const [page, setPage] = useState(1); // 현재 페이지

  // 포스터 데이터 가져오기, 현재 상영중인 영화 데이터 가져오기
  useEffect(() => {
    axios.get("http://localhost:8080/posters").then((response) => {
      let list = new Array();

      // 포스터의 개수를 5개로 조정
      for (let i = 0; i < 5; i++) {
        list.push(response.data.results[i]);
      }

      setPoster(list);
    });

    axios.get("http://localhost:8080/movies?page=0").then((response) => {
      setMovie(response.data.results);
    });
  }, []);

  // 영화 정보 추가 요청 api
  const moreMovie = () => {
    axios.get("http://localhost:8080/movies?page=" + page).then((response) => {
      setMovie((prevMovies) => [...prevMovies, ...response.data.results]);
      setPage((prevPage) => prevPage + 1);
    });
  };

  // 자동 이미지 슬라이드
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % poster.length);
    }, 4000);

    return () => clearInterval(interval); // 컴포넌트 언마운트 시 정리
  }, [poster.length]);

  // 영화 이미지 클릭 하면 영화 정보를 화면에 표시
  const movieInfo = (movie) => {
    setClickMovie(movie);
  };

  return (
    <div className="Main">
      <div className="header-bar">
        <Header />
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
          {poster.map((movie, index) => (
            <div
              key={index}
              className="poster"
              style={{
                minWidth: "100%",
                minHeight: "100%",
                backgroundImage: `url(https://image.tmdb.org/t/p/w500${movie.backdrop_path})`,
                backgroundRepeat: "no-repeat",
                backgroundSize: "cover",
                backgroundPosition: "center",
                transform: `translateX(-${currentIndex * 100}%)`, // 현재 인덱스 기준으로 이동
              }}
            >
              <div className="movie-content">
                <span className="movie-span movie-title">{`${movie.title}`}</span>
                <table>
                  <tbody>
                    <tr>
                      <td>
                        <span className="movie-span movie-average">평점</span>
                      </td>
                      <td>
                        <span className="movie-span-average">
                          <i className="fa-solid fa-star"></i>
                          {`${movie.vote_average}`}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span className="movie-span movie-count">투표수</span>
                      </td>
                      <td>
                        <span className="movie-span-count">
                          <i className="fa-solid fa-user"></i>
                          {`${movie.vote_count}`}
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="now-playing-span">인기 있는 영화</div>
      <div className="now-movieList">
        {movies.map((movie, index) => (
          <div className="movie-box" key={index}>
            <div
              onClick={() => movieInfo(movie)}
              key={index}
              className="nowplay-movie-image"
              style={{
                minWidth: "100%",
                minHeight: "100%",
                backgroundImage: `url(https://image.tmdb.org/t/p/w500${movie.poster_path})`,
                backgroundRepeat: "no-repeat",
                backgroundSize: "cover",
                backgroundPosition: "center",
              }}
            >
              <div className="movie-overview">
                <span className="movie-overview-span">{`${movie.overview}`}</span>
              </div>
            </div>
            <div className="nowplay-movie-title-div">
              <span className="nowplay-movie-title">{`${movie.title}`}</span>
            </div>
          </div>
        ))}
        {clickMovie && (
          <div className="movie-info-div">
            <div className="movie-info">
              <img
                className="movie-info-image"
                src={`https://image.tmdb.org/t/p/w500${clickMovie.poster_path}`}
              ></img>
              <div className="movie-info-under">
                <div className="xmark-div">
                  <i
                    onClick={() => setClickMovie()}
                    className="fa-solid fa-xmark movie-info-xmark"
                  ></i>
                </div>
                <span className="movie-info-title">{`${clickMovie.title}`}</span>
                <table>
                  <tbody>
                    <tr>
                      <td>
                        <span className="movie-info-span">평점</span>
                      </td>
                      <td>
                        <span className="movie-info-span-imo">
                          <i className="fa-solid fa-star"></i>
                          {`${clickMovie.vote_average}`}
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <span className="movie-info-span">투표수</span>
                      </td>
                      <td>
                        <span className="movie-info-span-imo">
                          <i className="fa-solid fa-user"></i>
                          {`${clickMovie.vote_count}`}
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div className="movie-info-span-line-div">
                  <span className="movie-info-span-line">{`${clickMovie.overview}`}</span>
                </div>
                <button className="movie-save-btn" type="button">
                  저장하기
                </button>
              </div>
            </div>
          </div>
        )}
      </div>

      <div className="more-btn-div">
        <button className="more-btn" type="button" onClick={moreMovie}>
          <span>더보기 </span>
          <i className="fa-solid fa-arrow-down"></i>
        </button>
      </div>
    </div>
  );
}

export default Main;
