import "./categoriesInfo.scss";
import React, { ChangeEvent, useRef, useState } from "react";
import Button from "../../Button/Button";
import { TCategory } from "types/productTypes";
import { useAppDispatch } from "../../../redux/hooks/redux";
import {
  putCategory,
  deleteCategory,
} from "../../../redux/actionCreators/categoryAction";
import { log } from "console";

function CategoryEditForm({ id, name, endpointName, description }: TCategory) {
  console.log("====================================");
  console.log({ id, name, endpointName, description });
  console.log("====================================");
  const dispatch = useAppDispatch();
  const [isEdit, setIsEdit] = useState(false);
  const [category, setCategory] = useState({
    id,
    name,
    endpointName,
    description,
  });

  const handleValue = (e: ChangeEvent) => {
    const target = e.target as HTMLInputElement;
    setCategory((prev) => ({ ...prev, [target.name]: target.value }));
  };

  const handleEdit = () => {
    setIsEdit(!isEdit);
  };

  const handleSaving = (e: React.SyntheticEvent) => {
    e.preventDefault();
    setIsEdit(!isEdit);
    dispatch(putCategory(category));
  };

  const handleDelete = (e: React.SyntheticEvent) => {
    e.preventDefault();
    setIsEdit(false);
    dispatch(deleteCategory(category));
  };

  return (
    <li className={`categories-info__item item ${isEdit ? "active" : ""}`}>
      <form action='' className='item__form'>
        <div className='item__form-container'>
          <div className='item__form-item form-item'>
            <label htmlFor='name' className='form-item__label'>
              {name}
            </label>
            <input
              type='text'
              className='form-item__input'
              id='name'
              name='name'
              value={category.name}
              onChange={handleValue}
              required
            />
          </div>
          <div className='item__form-item form-item'>
            <label htmlFor='endpointName' className='form-item__label'>
              {endpointName}
            </label>
            <input
              type='text'
              className='form-item__input'
              id='endpointName'
              name='endpointName'
              value={category.endpointName}
              onChange={handleValue}
              required
            />
          </div>
          <div className='item__form-item form-item'>
            <label htmlFor='description' className='form-item__label'>
              {description}
            </label>
            <input
              type='text'
              className='form-item__input'
              id='description'
              name='description'
              value={category.description}
              onChange={handleValue}
              required
            />
          </div>
        </div>

        <div className='form__buttons'>
          <Button
            type='button'
            classNames='edit-btn'
            text='Редактировать'
            action={handleEdit}
          />
          <Button
            type='submit'
            classNames='save-btn'
            text='  Сохранить'
            action={(e) => handleSaving(e)}
          />
          <Button
            type='submit'
            classNames='delete-btn'
            text='Удалить'
            action={(e) => handleDelete(e)}
          />
        </div>
      </form>
    </li>
  );
}

export default CategoryEditForm;
