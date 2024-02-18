import { useEffect, useState } from "react";
import { fetchCategory } from "../../../redux/actionCreators/categoryAction";
import { useAppDispatch, useAppSelector } from "../../../redux/hooks/redux";
import { fetchCategoryProducts } from "../../../redux/actionCreators/producrAction";
import { log } from "console";
import { useNavigate } from "react-router-dom";

function HeaderNavList() {
  const navigate = useNavigate();
  //const [isVisible, setIsVisible] = useState(isAdmin);

  const dispatch = useAppDispatch();
  const { categories } = useAppSelector((state) => state.categories);

  const showAllProducts = () => {
    navigate(`/`);
  };

  const showCategoryProducts = (id: number) => {
    dispatch(fetchCategoryProducts(id));
    navigate(`${id}`);
  };

  useEffect(() => {
    dispatch(fetchCategory());
  }, []);

  return (
    <nav className='nav'>
      <div className='nav__item' onClick={showAllProducts}>
        Главная
      </div>
      {categories.map((item) => (
        <div
          className='nav__item'
          key={item.id}
          onClick={() => showCategoryProducts(item.id)}>
          {item.name}
        </div>
      ))}
    </nav>
  );
}

export default HeaderNavList;
