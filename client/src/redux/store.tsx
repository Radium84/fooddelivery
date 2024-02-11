import { combineReducers, configureStore } from "@reduxjs/toolkit";

import categoriesReducer from "./slices/categorySlice";

const rootReducer = combineReducers({
  categories: categoriesReducer,
});

export const store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppStore = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
