import { ChangeEvent, useState } from "react";
import "./signIn.scss";
import { useAppDispatch } from "../../redux/hooks/redux";
import { useNavigate } from "react-router-dom";
import { signInUser } from "../../redux/slices/userSlice";
import { fetchSignIn } from "../../redux/actionCreators/userAction";
import { NavLink } from "react-router-dom";

function SignIn() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [userSignIn, setUserSignIn] = useState({
    username: "",
    password: "",
  });

  function handleSubmit(e: React.SyntheticEvent) {
    // Prevent the browser from reloading the page
    e.preventDefault();
    dispatch(fetchSignIn());
    navigate("/");
  }
  const handleNavigate = (path: string) => {
    navigate(path);
  };
  const handleValue = (e: ChangeEvent) => {
    const target = e.target as HTMLInputElement;
    setUserSignIn((prev) => ({ ...prev, [target.name]: target.value }));
  };

  return (
    <div className={`sign-in `}>
      <div className='container'>
        <form
          onSubmit={(e) => handleSubmit(e)}
          className='sign-in__form modal-form form glass-bg'>
          <h2 className='form__header'>Авторизация</h2>
          <div className='form__name form__item'>
            <label htmlFor='username'>Имя</label>
            <input
              type='text'
              id='username'
              value={userSignIn.username}
              name='username'
              onChange={handleValue}
            />
          </div>
          <div className='form__item form__endpoint'>
            <label htmlFor='password'>Пароль</label>
            <input
              type='text'
              id='password'
              name='password'
              value={userSignIn.password}
              onChange={handleValue}
            />
          </div>
          <div className='form__btn'>
            <button type='submit' className='btn'>
              Войти
            </button>

            <div className='sign-up-link'>
              <div className='sign-up-link__text'> Нет аккаунта?</div>
              <div
                onClick={() => handleNavigate("/auth/sign-up")}
                className='link sign-up-link__text'>
                Зарегистрироваться
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignIn;
