import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { TUser, TUserSignIn } from "types/userTypes";

interface User {
  user: TUser | TUserSignIn;
}
const initialState: User = {
  user: {
    id: 2,
    firstname: "Марина",
    middlename: "Александровна",
    lastname: "Васильева",
    address: "пр-т Ленина, дом 42, квартира 10, Санкт-Петербург, Россия",
    birthday: "1985-11-23T00:00:00",
    favoriteProducts: [1, 2],
    auth: {
      id: 4,
      username: "marina.vasileva",
      password: "$2a$10$i4/urwVxnJIgaTqqCY64J.12eoV83Je2T/UxKVZeXm.mOqP/HCtgC",
      roles: [
        {
          roleId: 1,
          name: "ROLE_USER",
        },
      ],
    },
  },
};

export const userSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    signInUser: (state, action: PayloadAction<TUserSignIn>) => {
      state.user = action.payload;
      return state;
    },

    signUpUser: (state, action: PayloadAction<TUserSignIn>) => {
      state.user = action.payload;
      return state;
    },
  },
});

export const { signInUser, signUpUser } = userSlice.actions;

export default userSlice.reducer;
