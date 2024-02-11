import "./styles/style.scss";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainAsync from "./pages/Main/MainAsync";
import PersonalAccountAsync from "./pages/Personal account/PersonalAccountAsync";
import { Suspense } from "react";
import Header from "./components/Header/Header";
import Cart from "./components/Cart/Cart";

function App() {
  return (
    <BrowserRouter>
      <div className='app'>
        <Header />
        <Suspense fallback={<div>loading...</div>}>
          <Routes>
            <Route path='/' element={<MainAsync />}></Route>
            <Route path='/lk/:id' element={<PersonalAccountAsync />}></Route>
            <Route path='/:id/cart' element={<Cart />}></Route>
          </Routes>
        </Suspense>
      </div>
    </BrowserRouter>
  );
}

export default App;
