// import React, { useState } from "react";
// import CloseButton from "src/components/CloseButton/CloseButton";

// type UserModalProps = {
//   isActive: boolean;
//   setActive: React.Dispatch<React.SetStateAction<boolean>>;
//   onModal: React.Dispatch<React.SetStateAction<boolean>>;
// };

// function UserModal({ isActive, setActive, onModal }: UserModalProps) {
//   const [userSignIn, setUserSignIn] = useState({
//     email: '',
//     password: '',

//   });

//   function handleSubmit(e: React.SyntheticEvent) {
//     // Prevent the browser from reloading the page
//     e.preventDefault();
//   }

//   return (
//     <div className={`user-modal modal ${isActive ? "" : "hidden"}`}>
//       <div className='container'>
//         <form
//           onSubmit={(e) => handleSubmit(e)}
//           className='modal-form form grey-bg'>
//           <CloseButton action={onModal} />
//           <h2 className='form__header'>Новая категория</h2>
//           <div className='form__name form__item'>
//             <label htmlFor='categoryName'>Наименование</label>
//             <input
//               type='text'
//               id='categoryName'
//               value={category.name}
//               name='name'
//               onChange={handleValue}
//             />
//           </div>
//           <div className='form__item form__endpoint'>
//             <label htmlFor='categoryNameEng'>Наименование латиницей</label>
//             <input
//               type='text'
//               id='categoryNameEng'
//               name='endpointName'
//               value={category.endpointName}
//               onChange={handleValue}
//             />
//           </div>
//           <div className='form__item form__description'>
//             <label htmlFor='categoryDescription'>Описание</label>
//             <input
//               type='text'
//               id='categoryDescription'
//               value={category.description}
//               name='description'
//               onChange={handleValue}
//             />
//           </div>
//           <div className='form__btn'>
//             <button type='submit' className='btn'>
//               Добавить
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default UserModal;
