import "./categoryModal.scss";
import { useAppDispatch } from "../../../redux/hooks/redux";
import { ChangeEvent, FormEventHandler, useEffect, useState } from "react";
import { log } from "console";
import { postCategory } from "../../../redux/actionCreators/categoryAction";
import { Navigate, useNavigate } from "react-router-dom";
import CloseButton from "../../CloseButton/CloseButton";

type CategoryModalProps = {
  isActive: boolean;
  setActive: React.Dispatch<React.SetStateAction<boolean>>;
  onModal: React.Dispatch<React.SetStateAction<boolean>>;
};
function CategoryModal(props: CategoryModalProps) {
  const { isActive, setActive, onModal } = props;

  const navigate = useNavigate();
  const [category, setCategory] = useState({
    name: "",
    endpointName: "",
    description: "",
  });
  const dispatch = useAppDispatch();

  function handleSubmit(e: React.SyntheticEvent) {
    // Prevent the browser from reloading the page
    e.preventDefault();
    dispatch(postCategory(category));
    navigate("/");
  }

  const handleValue = (e: ChangeEvent) => {
    const target = e.target as HTMLInputElement;
    setCategory((prev) => ({ ...prev, [target.name]: target.value }));
  };


  useEffect(() => {
    setActive(isActive);
  }, [isActive]);

  return (
    <div className={`category-modal modal ${isActive ? "" : "hidden"}`}>
      <div className='container'>
        <form
          onSubmit={(e) => handleSubmit(e)}
          className='modal-form form grey-bg'>
          <CloseButton action={onModal} />
          <h2 className='form__header'>Новая категория</h2>
          <div className='form__name form__item'>
            <label htmlFor='categoryName'>Наименование</label>
            <input
              type='text'
              id='categoryName'
              value={category.name}
              name='name'
              onChange={handleValue}
            />
          </div>
          <div className='form__item form__endpoint'>
            <label htmlFor='categoryNameEng'>Наименование латиницей</label>
            <input
              type='text'
              id='categoryNameEng'
              name='endpointName'
              value={category.endpointName}
              onChange={handleValue}
            />
          </div>
          <div className='form__item form__description'>
            <label htmlFor='categoryDescription'>Описание</label>
            <input
              type='text'
              id='categoryDescription'
              value={category.description}
              name='description'
              onChange={handleValue}
            />
          </div>
          <div className='form__btn'>
            <button type='submit' className='btn'>
              Добавить
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CategoryModal;
