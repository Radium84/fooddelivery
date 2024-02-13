import { useEffect, useState } from "react";
import { fetchCategory } from "../../../redux/actionCreators/categoryAction";
import { useAppDispatch, useAppSelector } from "../../../redux/hooks/redux";
import { fetchCategoryProducts } from "../../../redux/actionCreators/producrAction";
import { log } from "console";
import { useNavigate } from "react-router-dom";

type HeaderNavListProps = {
  onModal: React.Dispatch<React.SetStateAction<boolean>>;
};

function HeaderNavList({ onModal }: HeaderNavListProps) {
  const navigate = useNavigate();
  //const [isVisible, setIsVisible] = useState(isAdmin);

  const dispatch = useAppDispatch();
  const { categories } = useAppSelector((state) => state.categories);

  const openAddModal = () => {
    onModal(true);
  };

  const showAllProducts = () => {
    dispatch(fetchCategory());
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
      <button className={`nav__add btn`} onClick={() => openAddModal()}>
        Добавить
      </button>
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
