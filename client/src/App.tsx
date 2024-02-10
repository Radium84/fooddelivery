import "./styles/style.scss";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainAsync from "./pages/Main/MainAsync";
import PersonalAccountAsync from "./pages/Personal account/PersonalAccountAsync";
import { Link } from "react-router-dom";
import { Suspense } from "react";
import Header from "./components/Header/Header";

function App() {
  return (
    <BrowserRouter>
      <div className='app'>
        <Header />
        <Suspense fallback={<div>loading...</div>}>
          <Routes>
            <Route path='/' element={<MainAsync />}></Route>
            <Route path='/:id' element={<PersonalAccountAsync />}></Route>
          </Routes>
        </Suspense>
      </div>
    </BrowserRouter>
  );
}

export default App;
