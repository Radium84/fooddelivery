
type TOrderItem = {
  productId: number;
  quantity: number;
};

type TOrder = {
  userId: number;
  orderArray: TOrderItem[];
};

export { TOrderItem, TOrder };
