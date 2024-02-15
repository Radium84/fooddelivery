import "./styles/style.scss";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainAsync from "./pages/Main/MainAsync";
import PersonalAccountAsync from "./pages/Personal account/PersonalAccountAsync";
import { Suspense, useState } from "react";
import Header from "./components/Header/Header";
import Cart from "./components/Cart/Cart";
import CategoryModal from "./components/Modals/CategoryModal/CategoryModal";
import SignIn from "./pages/SignIn/SignIn";
import SignUp from "./pages/SignUp/SignUp";

function App() {
  const [modalActive, setModalActive] = useState(false);

  const changeModalState = () => {
    setModalActive(!modalActive);
  };

  return (
    <BrowserRouter>
      <div className='app'>
        <Header onModal={changeModalState} isLoggedIn={false} />
        <div className='page'>
          <Suspense fallback={<div>loading...</div>}>
            <Routes>
              <Route path='/' element={<MainAsync />}></Route>
              <Route path='/auth'>
                <Route path='sign-up' element={<SignUp />}></Route>
                <Route path='sign-in' element={<SignIn />}></Route>
              </Route>
              <Route path='/:categoryName' element={<MainAsync />}></Route>
              <Route path='/lk/:id' element={<PersonalAccountAsync />}></Route>
              <Route path='/:id/cart' element={<Cart />}></Route>
            </Routes>
          </Suspense>
          <CategoryModal
            isActive={modalActive}
            setActive={setModalActive}
            onModal={changeModalState}
          />
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
