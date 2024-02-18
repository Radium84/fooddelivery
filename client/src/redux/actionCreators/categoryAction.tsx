import { TCategory, TCategoryList } from "types/productTypes";
import {
  addCategory,
  editcategory,
  getCategories,
  removeCategory
} from "../slices/categorySlice";
import { AppDispatch } from "../store";
import { HOST } from "../../constants/constants";

export const fetchCategory = () => {
  return async function (dispatch: AppDispatch) {
    try {
      const response = await fetch(`${HOST}/categories`, {
        method: "GET",
      });
      const categoriesList: TCategoryList = await response.json();
      dispatch(getCategories(categoriesList));
    } catch (error) {
      console.log(error);
    }
  };
};

export const postCategory = (payload: TCategory) => {
  const userData = JSON.parse(localStorage.getItem("user"));
  return async function (dispatch: AppDispatch) {
    if (userData) {
      const { id, token } = userData;
      try {
        const response = await fetch(`${HOST}/categories`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        });
        const category: TCategory = await response.json();
        dispatch(addCategory(category));
      } catch (error) {
        console.log("error", error);
      }
    }
  };
};

export const putCategory = (payload: TCategory) => {
  const userData = JSON.parse(localStorage.getItem("user"));
  return async function (dispatch: AppDispatch) {
    if (userData) {
      const { id, token } = userData;
      const categoryId = payload.id;
      try {
        const response = await fetch(`${HOST}/categories/${categoryId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        });
        const category: TCategory = await response.json();
        dispatch(editcategory(category));
      } catch (error) {
        console.log("error", error);
      }
    }
  };
};

export const deleteCategory = (payload: TCategory) => {
  const userData = JSON.parse(localStorage.getItem("user"));
  return async function (dispatch: AppDispatch) {
    if (userData) {
      const { id, token } = userData;
      const categoryId = payload.id;
      try {
        const response = await fetch(`${HOST}/categories/${categoryId}`, {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          dispatch(removeCategory(categoryId));
        }
      } catch (error) {
        console.log("error", error);
      }
    }
  };
};
