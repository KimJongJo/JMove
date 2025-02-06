import { Link, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Header from "./Header";
import "../css/SearchMovie.css";

function SearchMovie() {
  const location = useLocation();
  const quertyparams = new URLSearchParams(location.search);
  const searchMovieName = quertyparams.get("q");
  const [searchMovieList, setSearchMovieList] = useState([]);
  const [clickMovie, setClickMovie] = useState(); // 선택한 영화

  // 영화 이미지 클릭 하면 영화 정보를 화면에 표시
  const movieInfo = (movie) => {
    setClickMovie(movie);
  };

  const searchMovie = () => {
    axios
      .get("http://localhost:8080/movies/search", {
        params: { q: searchMovieName },
      })
      .then((response) => {
        setSearchMovieList(response.data.results);
      });
  };

  useEffect(() => {
    searchMovie();
  }, [searchMovieName]);

  return (
    <div>
      <div className="header-bar">
        <Header />
      </div>
      <div className="search-div"></div>
      <div>
        <div className="search-home-div-i">
          <Link to="/">
            <i className="fa-solid fa-house"></i>
          </Link>
        </div>
        <span className="search-name">
          " {searchMovieName} " 와 연관있는 영화
        </span>
        <div>
          {searchMovieList.length === 0 ? (
            <div className="no-result-span-div">
              <i className="fa-solid fa-magnifying-glass"></i>
              <span className="no-result-span">검색 결과가 없습니다.</span>
            </div>
          ) : (
            <div className="now-movieList">
              {searchMovieList.map((movie, index) => (
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
          )}
        </div>
      </div>
    </div>
  );
}

export default SearchMovie;
