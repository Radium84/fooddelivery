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
    editcategory: (state, action: PayloadAction<TCategory>) => {
      const categoryItemToUpdate = [...state.categories].find(
        (el) => el.id === action.payload.id
      );
      if (categoryItemToUpdate) {
        state.categories.splice(
          1,
          state.categories.indexOf(categoryItemToUpdate),
          action.payload
        );
      }
      return state;
    },
  },
});

// Action creators are generated for each case reducer function
export const { getCategories, addCategory, editcategory } =
  categorySlice.actions;

export default categorySlice.reducer;
