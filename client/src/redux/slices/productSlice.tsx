import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import categories from "../../../mockData/categories";
import { TCategoryList, TProductList } from "types/productTypes";
import products from "mockData/products";

interface Products {
  products: TProductList;
  productsByCategory: TProductList;
}

const initialState: Products = {
  products: products,
  productsByCategory: [],
};

export const productSlice = createSlice({
  name: "Products",
  initialState,
  reducers: {
    getProducts: (state, action: PayloadAction<TProductList>) => {
      state.products = action.payload;
      return state;
    },

    getProductsByCategory: (state, action: PayloadAction<number>) => {
      state.productsByCategory = [...state.products].filter(
        (el) => el.categoryId === action.payload
      );
      return state;
    },
  },
});

// Action creators are generated for each case reducer function
export const { getProducts, getProductsByCategory } = productSlice.actions;

export default productSlice.reducer;
