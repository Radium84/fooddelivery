import { TCategory, TCategoryList } from "types/productTypes";
import { addCategory, getCategories } from "../slices/categorySlice";
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
  console.log("====================================");
  console.log("hey");
  console.log("====================================");
  return async function (dispatch: AppDispatch) {
    try {
      const response = await fetch(`${HOST}/categories`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });
      const category: TCategory = await response.json();
      dispatch(addCategory(category));
    } catch (error) {
      console.log("error", error);
    }
  };
};
