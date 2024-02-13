import { HOST } from "src/constants/constants";
import { AppDispatch } from "../store";
import { TUser } from "types/userTypes";
import { signInUser } from "../slices/userSlice";

export const fetchSignIn = () => {
  return function (dispatch: AppDispatch) {
    const user = {
      id: 2,
      firstname: "Марина",
      middlename: "Александровна",
      lastname: "Васильева",
      address: "пр-т Ленина, дом 42, квартира 10, Санкт-Петербург, Россия",
      birthday: "1985-11-23T00:00:00",
      favoriteProducts: [1, 2],
      auth: {
        id: 4,
        username: "marina.vasileva",
        password:
          "$2a$10$i4/urwVxnJIgaTqqCY64J.12eoV83Je2T/UxKVZeXm.mOqP/HCtgC",
        roles: [
          {
            roleId: 1,
            name: "ROLE_USER",
          },
        ],
      },
    };
    console.log("====================================");
    console.log("actionCreator");
    console.log("====================================");
    dispatch(signInUser(user));
    // try {
    //   const response = await fetch(`${HOST}/products`, {
    //     method: "GET",
    //   });
    //   const user: TUser = await response.json();
    // } catch (error) {
    //   console.log(error);
    // }
  };
};
