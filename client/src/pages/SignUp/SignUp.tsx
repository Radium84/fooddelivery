import React, { ChangeEvent, useState } from "react";
import "./signUp.scss";
import { useAppDispatch } from "../../redux/hooks/redux";
import { fetchSignUp } from "../../redux/actionCreators/userAction";
import { useNavigate } from "react-router-dom";

type TSignUpProps = {
  isActive: boolean;
  setActive: React.Dispatch<React.SetStateAction<boolean>>;
  onModal: React.Dispatch<React.SetStateAction<boolean>>;
};

function SignUp() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [userSignUp, setUserSignIn] = useState({
    firstname: "",
    middlename: "",
    lastname: "",
    address: "",
    birthday: "",
    username: "",
    password: "",
  });

  function handleSubmit(e: React.SyntheticEvent) {
    dispatch(fetchSignUp(userSignUp, navigate));
    e.preventDefault();
  }

  const handleValue = (e: ChangeEvent) => {
    const target = e.target as HTMLInputElement;
    setUserSignIn((prev) => ({ ...prev, [target.name]: target.value }));
  };

  return (
    <div className='sign-up'>
      <form
        onSubmit={(e) => handleSubmit(e)}
        className='modal-form form glass-bg'>
        <h2 className='form__header'>Регистрация</h2>
        <div className='form__item form__firstname'>
          <label htmlFor='firstname'>Имя*</label>
          <input
            type='text'
            id='firstname'
            value={userSignUp.firstname}
            name='firstname'
            onChange={handleValue}
            required
          />
        </div>
        <div className='form__item form__lastname'>
          <label htmlFor='lastname'>Фамилия*</label>
          <input
            type='text'
            id='lastname'
            name='lastname'
            value={userSignUp.lastname}
            onChange={handleValue}
            required
          />
        </div>
        <div className='form__item form__middlename'>
          <label htmlFor='middlename'>Отчество</label>
          <input
            type='text'
            id='middlename'
            name='middlename'
            value={userSignUp.middlename}
            onChange={handleValue}
          />
        </div>
        <div className='form__item form__address'>
          <label htmlFor='address'>Адрес*</label>
          <input
            type='text'
            id='address'
            name='address'
            value={userSignUp.address}
            onChange={handleValue}
            required
          />
        </div>
        <div className='form__item form__birthday'>
          <label htmlFor='birthday'>Дата рождения*</label>
          <input
            type='datetime-local'
            id='birthday'
            name='birthday'
            value={userSignUp.birthday}
            onChange={handleValue}
            required
          />
        </div>
        <div className='form__item form__username'>
          <label htmlFor='username'>Логин*</label>
          <input
            type='text'
            id='username'
            name='username'
            value={userSignUp.username}
            onChange={handleValue}
          />
        </div>
        <div className='form__item form__password'>
          <label htmlFor='password'>Пароль*</label>
          <input
            type='password'
            id='password'
            name='password'
            value={userSignUp.password}
            onChange={handleValue}
          />
        </div>
        <div className='form__btn'>
          <button type='submit' className='btn'>
            Зарегистрироваться
          </button>
        </div>
      </form>
    </div>
  );
}

export default SignUp;
