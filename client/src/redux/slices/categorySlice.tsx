import { createSlice } from "@reduxjs/toolkit";
import { categories } from "../../../mockData/categories";

const initialState = categories;

export const categorySlice = createSlice({
  name: "Categories",
  initialState,
  reducers: {
    getCategories: (state) => {
      return state;
    },
  },
});

// Action creators are generated for each case reducer function
export const { getCategories } = categorySlice.actions;

export default categorySlice.reducer;
