import { useState, ChangeEvent } from "react";
import { useAppSelector } from "../../redux/hooks/redux";
import "./userData.scss";

function UserData() {
  const { user } = useAppSelector((state) => state.user);
  const { firstname, lastname, middlename, address, birthday } = user;
  const [userEditData, setUserEditData] = useState({
    firstname,
    middlename,
    lastname,
    address,
    birthday,
  });

  const [isEditable, setIsEditable] = useState(false);

  const handleValue = (e: ChangeEvent) => {
    const target = e.target as HTMLInputElement;
    setUserEditData((prev) => ({ ...prev, [target.name]: target.value }));
  };

  const handleEdit = () => {
    setIsEditable(!isEditable);
  };

  const handleSubmitEdit = () => {
    setIsEditable(!isEditable);
  };

  return (
    <div className='user-data'>
      <form action='' className='user-data-form form'>
        <div className='form__item'>
          <label htmlFor=''>{firstname}</label>
          <input
            type='text'
            id='firstname'
            name='firstname'
            value={userEditData.firstname}
            onChange={handleValue}
            className={isEditable ? `` : "hidden"}
          />
        </div>
        <div className='form__item'>
          <label htmlFor=''>{lastname}</label>
          <input
            type='text'
            id='lastname'
            name='lastname'
            value={userEditData.lastname}
            onChange={handleValue}
            className={isEditable ? `` : "hidden"}
          />
        </div>
        <div className='form__item'>
          <label htmlFor=''>{middlename}</label>
          <input
            type='text'
            id='middlename'
            name='middlename'
            value={userEditData.middlename}
            onChange={handleValue}
            className={isEditable ? `` : "hidden"}
          />
        </div>

        <div className='form__item'>
          <label htmlFor=''>{address}</label>
          <input
            type='text'
            id='address'
            name='address'
            value={userEditData.address}
            onChange={handleValue}
            className={isEditable ? `` : "hidden"}
          />
        </div>
        <div className='form__item'>
          <label htmlFor=''>{birthday}</label>
          <input
            type='date-local'
            id='birthday'
            name='birthday'
            value={userEditData.birthday}
            onChange={handleValue}
            className={isEditable ? `` : "hidden"}
          />
        </div>

        <div className='form-buttons'>
          <button type='button' className='btn' onClick={handleEdit}>
            Редактировать
          </button>
          <button type='button' className='btn' onClick={handleSubmitEdit}>
            Сохранить
          </button>
        </div>
      </form>
    </div>
  );
}

export default UserData;
