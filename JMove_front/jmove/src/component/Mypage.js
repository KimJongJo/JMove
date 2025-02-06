import { Link } from "react-router-dom";
import axios from "axios";
import Header from "./Header";
import { useEffect, useState } from "react";
import "../css/Mypage.css";

function Mypage() {
  const [myMovie, setMyMovie] = useState([]);

  useEffect(() => {
    myMovies();
  }, []);

  const [clickMovie, setClickMovie] = useState(); // 선택한 영화
  const [clickMovieId, setClickMovieId] = useState("");

  // 영화 이미지 클릭 하면 영화 정보를 화면에 표시
  const movieInfo = (movie) => {
    setClickMovie(movie);
  };

  const deleteMovie = () => {
    axios
      .post(
        "http://localhost:8080/users/movies",
        { movie: clickMovie },
        { withCredentials: true }
      ) // 빈 객체를 두 번째 인자로 넣고, withCredentials는 세 번째 인자에
      .then((response) => {
        console.log(response);
        alert("삭제되었습니다.");
        myMovies();
        setClickMovie(null);
      })
      .catch((error) => {
        console.error("삭제 실패:", error);
      });

    console.log(clickMovieId);
  };

  const myMovies = () => {
    axios
      .get("http://localhost:8080/users/mypage", { withCredentials: true })
      .then((response) => {
        setMyMovie(response.data);
        console.log(response.data);
      });
  };

  return (
    <div>
      <div className="header-bar">
        <Header />
      </div>
      <div className="mypage-span-div">
        <span className="mypage-span">마이페이지</span>
      </div>
      <div className="movieList">
        {myMovie.map((movie, index) => (
          <div className="movie-box" key={index}>
            <div
              onClick={() => {
                movieInfo(movie);
                setClickMovieId(movie.id);
              }}
              key={index}
              className="nowplay-movie-image"
              style={{
                minWidth: "100%",
                minHeight: "100%",
                backgroundImage: `url(https://image.tmdb.org/t/p/w500${movie.posterPath})`,
                backgroundRepeat: "no-repeat",
                backgroundSize: "cover",
                backgroundPosition: "center",
              }}
            >
              <div className="movie-overview">
                <span className="movie-overview-span">{`${movie.text}`}</span>
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
                src={`https://image.tmdb.org/t/p/w500${clickMovie.posterPath}`}
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
                          {`${clickMovie.average}`}
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
                          {`${clickMovie.count}`}
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div className="movie-info-span-line-div">
                  <span className="movie-info-span-line">{`${clickMovie.text}`}</span>
                </div>
                <button
                  className="movie-save-btn"
                  type="button"
                  onClick={deleteMovie}
                >
                  삭제하기
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Mypage;
