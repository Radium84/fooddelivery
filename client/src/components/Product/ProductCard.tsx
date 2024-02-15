import { TProduct } from "types/productTypes";

function ProductCard(props: TProduct) {
  const { name, composition, description, price, calculatedPrice, isDiscount } =
    props;

  return (
    <li className='product-list__item product-card'>
      <div className='product-card__image'></div>
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
            {price} руб.
          </div>
          <div className='price-block__new-price'>
            {isDiscount ? calculatedPrice : price} руб.{" "}
          </div>
        </div>
        <button
          className='product-card__add-to-cart-btn'
          type='button'></button>
      </div>
    </li>
  );
}

export default ProductCard;
