import { TProduct } from "types/productTypes";
import { useState, ChangeEvent, useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import {
  addToOrder,
  createOrder,
  removeFromOrder,
} from "../../redux/slices/orderSlice";
import { useNavigate } from "react-router-dom";

type TProductCard = {
  product: TProduct;
  isHorisontal: boolean;
  isShop: boolean;
};

function ProductCard(props: TProductCard) {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { user } = useAppSelector((state) => state.user);
  const [isPicked, setIsPicked] = useState(false);
  const [counter, setCounter] = useState(1);
  const {
    name,
    composition,
    description,
    price,
    calculatedPrice,
    isDiscount,
    id,
  } = props.product;
  const { isHorisontal, isShop } = props;
  const [summaryPrice, setSummaryPrice] = useState(price);
  const [discountPrice, setDiscountPrice] = useState(calculatedPrice);

  const handlePick = () => {
    setIsPicked(true);
    dispatch(
      createOrder({
        userId: user.id,
        orderArray: [{ productId: id, quantity: counter }],
      })
    );
  };

  const increase = () => {
    setCounter(counter + 1);
    dispatch(addToOrder({ productId: id, quantity: counter + 1 }));
  };

  const decrease = () => {
    if (counter > 1) {
      setCounter(counter - 1);
      dispatch(addToOrder({ productId: id, quantity: counter - 1 }));
    } else if (counter === 1) {
      setIsPicked(false);
      dispatch(removeFromOrder(id));
    }
  };

  useEffect(() => {
    setSummaryPrice(price * counter);
    setDiscountPrice(calculatedPrice * counter);
  }, [counter]);

  return (
    <li
      className={`product-list__item product-card product-card_${
        isHorisontal ? "horisontal" : "vertical"
      }`}>
      <div className='product-card__image'></div>
      <div className='like'>
        <img src='../../assets/images/icons/like.png' alt='' />
      </div>
      <div className='product-card__content'>
        <h2 className='product-card__title'>{name}</h2>
        <div
          className={`product-card__discount-label ${
            isDiscount ? "" : "hidden"
          }`}>
          Акция
        </div>

        <p className='product-card__description'>{description}</p>
        <p className='product-card__composition'>Состав: {composition}</p>
      </div>
      <div className='product-card__purchase-block purchase'>
        <div className='purchase__price-block price-block'>
          <div
            className={`price-block__old-price ${!isDiscount ? "hidden" : ""}`}>
            {summaryPrice} руб.
          </div>
          <div className='price-block__new-price'>{discountPrice} руб. </div>
        </div>
        <div
          className={`price-block__quantity ${
            isShop && isPicked && counter > 0 ? "" : !isShop ? "" : "hidden"
          }
          `}>
          <button type='button' className='round-btn' onClick={decrease}>
            -
          </button>
          <div className='quantity'>{counter}</div>
          <button type='button' className='round-btn' onClick={increase}>
            +
          </button>
        </div>
        {user ? (
          <button
            className='product-card__add-to-cart-btn'
            type='button'
            onClick={handlePick}></button>
        ) : (
          <div
            className='product-card__login link'
            onClick={() => navigate("/auth/sign-in")}>
            Войдите для заказа
          </div>
        )}
      </div>
    </li>
  );
}

export default ProductCard;
