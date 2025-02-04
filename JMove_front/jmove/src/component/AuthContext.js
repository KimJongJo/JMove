import { createContext, useState, useEffect } from "react";
import axios from "axios";

// 1. AuthContext 생성
export const AuthContext = createContext(null);

// 2. AuthProvider 함수형 컴포넌트
function AuthProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // 로그인 상ㅌㅐ 확인
  useEffect(() => {
    const checkLoginStatus = async () => {
      try {
        const response = await axios.get("http://localhost:8080/auth/check", {
          withCredentials: true, // 쿠키를 포함
        });

        // console.log(response);
        setIsLoggedIn(response.data); // 로그인 여부 업데이트
      } catch (error) {
        console.error("로그인 상태 확인 실패 : ", error);
        setIsLoggedIn(false);
      }
    };

    checkLoginStatus();
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>
      {children}
    </AuthContext.Provider>
  );
}

export { AuthProvider };
