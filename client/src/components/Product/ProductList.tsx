import { TProductList } from "types/productTypes";
import ProductCard from "./ProductCard";
import "./productList.scss";

type ProductListProps = {
  productsList: TProductList;
};

function ProductList({ productsList }: ProductListProps) {
  return (
    <ul className='product-list'>
      {productsList.map((el) => (
        <ProductCard
          product={el}
          key={el.id}
          isHorisontal={false}
          isShop={true}
        />
      ))}
    </ul>
  );
}

export default ProductList;
