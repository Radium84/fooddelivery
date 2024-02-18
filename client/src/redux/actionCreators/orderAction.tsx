import { HOST } from "../../constants/constants";
import { AppDispatch } from "../store";
import { TOrder } from "../../../types/orderTypes";
import { sendOrder, setSuccessOrder } from "../slices/orderSlice";

export const postOrder = (orderObject: TOrder) => {
  const merged = orderObject.orderArray.reduce(
    (acc, item) => ({ ...acc, [item.productId]: item.quantity }),
    {}
  );

  const userData = JSON.parse(localStorage.getItem("user"));
  return async function (dispatch: AppDispatch) {
    if (userData) {
      try {
        const { token, id } = userData;
        let objForPost = { ourUsersId: id, productQuantity: merged };
        const response = await fetch(`${HOST}/orders`, {
          method: "POSt",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(objForPost),
        });
        const order: TOrder = await response.json();
        dispatch(sendOrder(order));
        if (response.status === 201) {
          dispatch(setSuccessOrder(true));
        }
      } catch (error) {
        console.log(error);
      }
    }
  };
};
