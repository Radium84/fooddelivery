import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { TUser } from "types/userTypes";

interface User {
  user: null | TUser;
  isLoggedIn: boolean;
}
const initialState: User = {
  user: null,
  isLoggedIn: false,
};

export const userSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    signInUser: (state, action: PayloadAction<TUser>) => {
      state.user = action.payload;
      state.isLoggedIn = true;
      return state;
    },

    signUpUser: (state, action: PayloadAction<TUser>) => {
      state.user = action.payload;
      state.isLoggedIn = false;
      return state;
    },

    getUser: (state, action: PayloadAction<TUser>) => {
      state.user = action.payload;
      return state;
    },

    logOutUser: (state) => {
      state.user = null;
      state.isLoggedIn = false;
      return state;
    },
  },
});

export const { signInUser, signUpUser, logOutUser, getUser } =
  userSlice.actions;

export default userSlice.reducer;
