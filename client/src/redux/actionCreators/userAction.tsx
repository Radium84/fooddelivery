import { HOST } from "../../constants/constants";
import { AppDispatch } from "../store";
import { TUser, TUserFormData } from "types/userTypes";
import { logOutUser, signInUser, signUpUser } from "../slices/userSlice";

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
      token: "ddd",
      isAdmin: false,
    };
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

export const fetchSignUp = (userData: TUserFormData) => {
  console.log("====================================");
  console.log(JSON.stringify(userData));
  console.log("====================================");
  return async function (dispatch: AppDispatch) {
    try {
      const response = await fetch(`http://localhost:8080/api/auth/sign-up`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
      });
      const user: TUser = await response.json();
      localStorage.setItem("token", user.token);
      dispatch(signUpUser(user));
    } catch (error) {
      console.log(error, error.message);
    }
  };
};

export const fetchLogOut = () => {
  return function (dispatch: AppDispatch) {
    localStorage.removeItem("token");
    dispatch(logOutUser());
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
