import { TProductList } from "types/productTypes";
import { AppDispatch } from "../store";
import { HOST } from "../../constants/constants";
import { getProducts, getProductsByCategory } from "../slices/productSlice";

export const fetchAllProducts = () => {
  return async function (dispatch: AppDispatch) {
    try {
      const response = await fetch(`${HOST}/products`, {
        method: "GET",
      });
      const productsList: TProductList = await response.json();
      dispatch(getProducts(productsList));
    } catch (error) {
      console.log(error);
    }
  };
};

export const fetchCategoryProducts = (id: number) => {
  return function (dispatch: AppDispatch) {
    dispatch(getProductsByCategory(id));
  };
};
