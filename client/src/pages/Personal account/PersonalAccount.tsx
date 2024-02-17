import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import "./personalAccount.scss";
import { Outlet, NavLink, useNavigate } from "react-router-dom";

function PersonalAccount() {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { user, isLoggedIn } = useAppSelector((state) => state.user);

  const handleNavigate = (path: string) => {
    navigate(path);
  };

  return (
    <div className='user-account user-page'>
      <h2 className='user-page__title'>Личный кабинет</h2>
      <nav className='nav'>
        <ul className='nav-list nav__list'>
          {/* <li
            className='nav-list__item active'
            onClick={() => handleNavigate(`/lk/${user.id}/userData`)}>
            Личные данные
          </li>
          <li
            className='nav-list__item'
            onClick={() => handleNavigate(`/lk/${user.id}/orders`)}>
            Ваши заказы
          </li>
          <li
            className='nav-list__item'
            onClick={() => handleNavigate(`/lk/${user.id}/favorites`)}>
            Избранное
          </li> */}
          <NavLink
            to={`/lk/${user.id}`}
            className={({ isActive }) =>
              isActive ? "nav-list__item active" : "nav-list__item "
            }>
            Личные данные
          </NavLink>
          <NavLink
            to={`/lk/${user.id}/orders`}
            className={({ isActive }) =>
              isActive ? "nav-list__item active" : "nav-list__item "
            }>
            Ваши заказы
          </NavLink>
          <NavLink
            to={`/lk/${user.id}/favorites`}
            className={({ isActive }) =>
            isActive ? "nav-list__item active" : "nav-list__item "
          }>
            Избранное
          </NavLink>
        </ul>
      </nav>
      <Outlet />
    </div>
  );
}

export default PersonalAccount;
