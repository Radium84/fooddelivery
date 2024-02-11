import { TCategoryList } from "types/productTypes";
import { getCategories } from "../slices/categorySlice";
import { AppDispatch } from "../store";

export const categoryThunk = () => {
  return async function (dispatch: AppDispatch) {
    try {
      const response = await fetch(`${process.env.HOST}/categories`);
      const categoriesList: TCategoryList = await response.json();
      dispatch(getCategories(categoriesList));
    } catch (error) {
      console.log(error);
    }
  };
};
