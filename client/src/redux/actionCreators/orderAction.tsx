import { HOST } from "../../constants/constants";
import { AppDispatch } from "../store";
import { TOrder } from "../../../types/orderTypes";
import { createOrder } from "../slices/orderSlice";

export const postOrder = (orderObject: TOrder) => {
  return async function (dispatch: AppDispatch) {
    const response = await fetch(`${HOST}/api/orders`, {
      method: "POSt",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(orderObject),
    });
    const order: TOrder = await response.json();
    dispatch(createOrder(order));
  };
};
