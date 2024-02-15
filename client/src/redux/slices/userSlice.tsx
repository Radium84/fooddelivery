import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { TUser } from "types/userTypes";

interface User {
  user: "" | TUser;
}
const initialState: User = {
  user: "",
};

export const userSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    signInUser: (state, action: PayloadAction<TUser>) => {
      state.user = action.payload;
      return state;
    },

    signUpUser: (state, action: PayloadAction<TUser>) => {
      state.user = action.payload;
      return state;
    },

    logOutUser: (state) => {
      state.user = "";
      return state;
    },
  },
});

export const { signInUser, signUpUser, logOutUser } = userSlice.actions;

export default userSlice.reducer;
