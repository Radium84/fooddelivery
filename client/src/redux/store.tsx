import { combineReducers, configureStore } from "@reduxjs/toolkit";

import categoriesReducer from "./slices/categorySlice";
import productsReducer from "./slices/productSlice";
import usersReducer from "./slices/userSlice";

const rootReducer = combineReducers({
  categories: categoriesReducer,
  products: productsReducer,
  users: usersReducer,
});

export const store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppStore = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
