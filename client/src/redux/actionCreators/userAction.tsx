import { HOST } from "../../constants/constants";
import { AppDispatch } from "../store";
import { TUser, TUserAuth, TUserFormData } from "types/userTypes";
import {
  logOutUser,
  signInUser,
  signUpUser,
  getUser,
} from "../slices/userSlice";
import { NavigateFunction } from "react-router";

export const fetchUser = () => {
  const userData = JSON.parse(localStorage.getItem("user"));

  return async function (dispatch: AppDispatch) {
    if (userData) {
      const { id, token } = userData;
      try {
        const response = await fetch(`${HOST}/users/${id}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });
        const user: TUser = await response.json();
        dispatch(getUser(user));
      } catch (error) {
        console.log(error, error.message);
      }
    }
  };
};

export const fetchSignIn = (userData: TUserAuth) => {
  return async function (dispatch: AppDispatch) {
    try {
      const response = await fetch(`http://localhost:8080/api/auth/sign-in`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(userData),
      });
      const user: TUser = await response.json();
      const { id, token } = user;
      localStorage.setItem("user", JSON.stringify({ id, token }));
      console.log("====================================");
      console.log(user);
      console.log("====================================");
      dispatch(signInUser(user));
    } catch (error) {
      console.log(error, error.message);
    }
  };
};

export const fetchSignUp = (
  userData: TUserFormData,
  navigate: NavigateFunction
) => {
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
      dispatch(signUpUser(user));
      navigate("/auth/sign-in");
    } catch (error) {
      console.log(error, error.message);
    }
  };
};

export const fetchLogOut = () => {
  return function (dispatch: AppDispatch) {
    localStorage.removeItem("token");
    dispatch(logOutUser());
  };
};
