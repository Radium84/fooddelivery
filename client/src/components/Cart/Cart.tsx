import React, { Fragment } from "react";
import { useAppSelector } from "../../redux/hooks/redux";
import CartItem from "./CartItemCard";
import "./cart.scss";

function Cart() {
  const { products } = useAppSelector((state) => state.products);
  const { orderItemsList } = useAppSelector((state) => state.order);
  let orderedProducts = [];
  for (let i = 0; i < products.length; i++) {
    for (let j = 0; j < orderItemsList.length; j++) {
      if (products[i].id === orderItemsList[j].productId) {
        orderedProducts.push({
          product: products[i],
          quantity: orderItemsList[j].quantity,
        });
      }
    }
  }


  return (
    <div className='cart'>
      {orderedProducts.length > 0 ? (
        <>
          <h2 className='cart__title'>Корзина</h2>
          <ul className='product-list'>
            {orderedProducts.map((el) => (
              <CartItem
                product={el.product}
                key={el.product.id}
                quantity={el.quantity}
              />
            ))}
          </ul>
          <button className='product-card__add-to-cart-btn'>Заказать</button>
        </>
      ) : (
        <Fragment>
          <h2 className='cart__title'>Ваша корзина пуста</h2>
          <button className='product-card__add-to-cart-btn btn'>
            Вернуться в меню
          </button>
        </Fragment>
      )}
    </div>
  );
}

export default Cart;
