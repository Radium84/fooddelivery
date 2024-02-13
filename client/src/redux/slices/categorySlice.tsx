import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import categories from "../../../mockData/categories";
import { TCategory, TCategoryList } from "types/productTypes";

interface Categories {
  categories: TCategoryList;
}

const initialState: Categories = {
  categories: [],
};

export const categorySlice = createSlice({
  name: "Categories",
  initialState,
  reducers: {
    getCategories: (state, action: PayloadAction<TCategoryList>) => {
      state.categories = action.payload;
      return state;
    },
    addCategory: (state, action: PayloadAction<TCategory>) => {
      state.categories = [...state.categories, action.payload];
      return state;
    },
  },
});

// Action creators are generated for each case reducer function
export const { getCategories, addCategory } = categorySlice.actions;

export default categorySlice.reducer;
