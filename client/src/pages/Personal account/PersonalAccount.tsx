import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import "./personalAccount.scss";
import { Outlet, NavLink, useNavigate } from "react-router-dom";

function PersonalAccount() {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { user, isLoggedIn } = useAppSelector((state) => state.user);
  const { isAdmin, firstname } = user;
  const handleNavigate = (path: string) => {
    navigate(path);
  };

  return (
    <>
      {isAdmin ? (
        <div className='user-account user-page'>
          <h2 className='user-page__title'>Личный кабинет</h2>
          <nav className='nav'>
            <ul className='nav-list nav__list'>
              <NavLink
                to={`/admin/categories`}
                className={({ isActive }) =>
                  isActive ? "nav-list__item active" : "nav-list__item "
                }>
                Категории
              </NavLink>
              <NavLink
                to={`/admin/products`}
                className={({ isActive }) =>
                  isActive ? "nav-list__item active" : "nav-list__item "
                }>
                Продукты
              </NavLink>
              <NavLink
                to={`/admin/discounts`}
                className={({ isActive }) =>
                  isActive ? "nav-list__item active" : "nav-list__item "
                }>
                Скидки
              </NavLink>
            </ul>
          </nav>
          <Outlet />
        </div>
      ) : (
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
                to={`/lk/${user.id}/userinfo`}
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
      )}
    </>
  );
}

export default PersonalAccount;
