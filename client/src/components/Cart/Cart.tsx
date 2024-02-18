import React, { Fragment } from "react";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import CartItem from "./CartItemCard";
import "./cart.scss";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { postOrder } from "../../redux/actionCreators/orderAction";
import { TOrder } from "types/orderTypes";
import "../Product/productList.scss";
import { setSuccessOrder } from "../../redux/slices/orderSlice";

function Cart() {
  const { products } = useAppSelector((state) => state.products);
  const { orderItemsList, userId, isSuccess } = useAppSelector(
    (state) => state.order
  );
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  let orderedProducts = [];
  let summary = 0;
  for (let i = 0; i < products.length; i++) {
    for (let j = 0; j < orderItemsList.length; j++) {
      if (products[i].id === orderItemsList[j].productId) {
        orderedProducts.push({
          product: products[i],
          quantity: orderItemsList[j].quantity,
        });
        summary += products[i].calculatedPrice * orderItemsList[j].quantity;
      }
    }
  }
  const handleOrder = () => {
    dispatch(postOrder({ userId, orderArray: orderItemsList }));
  };

  const handleSuccess = () => {
    navigate("/");
    dispatch(setSuccessOrder(false));
  };
  return (
    <div className='cart'>
      {orderedProducts.length > 0 && !isSuccess ? (
        <>
          <h2 className='cart__title'>Корзина</h2>
          <ul className='cart__product-list'>
            {orderedProducts.map((el) => (
              <CartItem
                product={el.product}
                key={el.product.id}
                quantity={el.quantity}
              />
            ))}
          </ul>
          <div className='summary'>
            <span>Итого:</span>
            <span>{summary} руб.</span>
          </div>
          <button className='cart__order-btn btn' onClick={handleOrder}>
            Заказать
          </button>
        </>
      ) : (
        <Fragment>
          {isSuccess ? (
            <h2 className='cart__title'>Заказ успешно создан</h2>
          ) : (
            <h2 className='cart__title'>Ваша корзина пуста</h2>
          )}
          <button className='cart__return-btn btn' onClick={handleSuccess}>
            Вернуться в меню
          </button>
        </Fragment>
      )}
    </div>
  );
}

export default Cart;
