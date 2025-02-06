import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./component/AuthContext";
import "./App.css";
import Main from "./component/Main";
import FindId from "./component/FindId";
import FindPw from "./component/FindPw";
import Signup from "./component/Signup";
import SearchMovie from "./component/SearchMovie";
import Mypage from "./component/Mypage";

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/" element={<Main />} />
            <Route path="/find-id" element={<FindId />} />
            <Route path="/find-pw" element={<FindPw />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/search" element={<SearchMovie />} />
            <Route path="/mypage" element={<Mypage />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
