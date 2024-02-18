import "./categoriesInfo.scss";
import React, { useState } from "react";
import CategoryModal from "../../Modals/CategoryModal/CategoryModal";
import { useAppSelector } from "../../../redux/hooks/redux";
import CategoryEditForm from "./CategoryEditForm";
import Button from "../../Button/Button";

function CategoriesInfo() {
  const [modalActive, setModalActive] = useState(false);
  const { categories } = useAppSelector((state) => state.categories);

  const changeModalState = () => {
    setModalActive(!modalActive);
  };

  return (
    <div className='categories-info'>
      <div className='categories-info__add-block add-block'>
        <h2 className='categories-info__title'>Категории</h2>
        <Button
          type='button'
          classNames=''
          action={changeModalState}
          text='Добавить категорию'
        />
      </div>

      <ul className='categories-list__header'>
        <li className='categories-list__header-item'>Наименование</li>
        <li className='categories-list__header-item'>Наименование (лат.)</li>
        <li className='categories-list__header-item'>Описание</li>
      </ul>
      <ul className='categories-list'>
        {categories.map((category) => (
          <CategoryEditForm key={category.id} {...category} />
        ))}
      </ul>

      <CategoryModal
        isActive={modalActive}
        setActive={setModalActive}
        onModal={changeModalState}
      />
    </div>
  );
}

export default CategoriesInfo;
