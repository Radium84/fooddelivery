import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import categories from "../../../mockData/categories";
import { TCategory, TCategoryList } from "types/productTypes";
import { log } from "console";

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
      console.log(categoryItemToUpdate);

      if (categoryItemToUpdate) {
        state.categories.splice(
          state.categories.indexOf(categoryItemToUpdate),
          1,
          action.payload
        );
      }
      return state;
    },

    removeCategory: (state, action: PayloadAction<number>) => {
      const categoryItemToRemove = [...state.categories].findIndex(
        (el) => el.id === action.payload
      );
      if (categoryItemToRemove) {
        state.categories.splice(categoryItemToRemove, 1);
      }
      return state;
    },
  },
});

// Action creators are generated for each case reducer function
export const { getCategories, addCategory, editcategory, removeCategory } =
  categorySlice.actions;

export default categorySlice.reducer;
