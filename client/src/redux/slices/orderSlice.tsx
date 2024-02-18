import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { log } from "console";
import { TOrder, TOrderItem } from "types/orderTypes";

interface Order {
  userId: number;
  orderItemsList: TOrderItem[];
  isSuccess: boolean;
}
const initialState: Order = {
  userId: null,
  orderItemsList: [],
  isSuccess: false,
};

export const orderSlice = createSlice({
  name: "Order",
  initialState,
  reducers: {
    createOrder: (state, action: PayloadAction<TOrder>) => {
      state.userId = action.payload.userId;
      state.orderItemsList = [
        ...state.orderItemsList,
        action.payload.orderArray[0],
      ];
      return state;
    },

    sendOrder: (state, action: PayloadAction<TOrder>) => {
      state.userId = action.payload.userId;
      state.orderItemsList = [];
      return state;
    },

    addToOrder: (state, action: PayloadAction<TOrderItem>) => {
      const orderItemToUpdate = [...state.orderItemsList].find(
        (el) => el.productId === action.payload.productId
      );
      if (orderItemToUpdate) {
        orderItemToUpdate.quantity = action.payload.quantity;
      }
      return state;
    },

    removeFromOrder: (state, action: PayloadAction<number>) => {
      const orderItemToRemove = [...state.orderItemsList].findIndex(
        (el) => el.productId === action.payload
      );

      if (orderItemToRemove !== -1) {
        const item = state.orderItemsList.splice(orderItemToRemove, 1);
      }
      return state;
    },

    setSuccessOrder: (state, action: PayloadAction<boolean>) => {
      state.isSuccess = action.payload;
      return state;
    },

    // getAllOrders: (state, action: PayloadAction<TOrder[]>) => {
    //   state.order = action.payload;
    //   return state;
    // },

    // getOrderById: (state, action: PayloadAction<TOrder>) => {
    //   state.userId = action.payload.userId;
    //   state.order = [...state.order, action.payload];
    //   return state;
    // },
  },
});

export const {
  createOrder,
  addToOrder,
  removeFromOrder,
  sendOrder,
  setSuccessOrder,
} = orderSlice.actions;

export default orderSlice.reducer;
