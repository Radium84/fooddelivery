import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import categories from "../../../mockData/categories";
import { TCategoryList } from "types/productTypes";

interface Categories {
  categories: TCategoryList;
}

const initialState: Categories = {
  categories: categories,
};

export const categorySlice = createSlice({
  name: "Categories",
  initialState,
  reducers: {
    getCategories: (state, action: PayloadAction<TCategoryList>) => {
      state.categories = action.payload;
      return state;
    },
  },
});

// Action creators are generated for each case reducer function
export const { getCategories } = categorySlice.actions;

export default categorySlice.reducer;
