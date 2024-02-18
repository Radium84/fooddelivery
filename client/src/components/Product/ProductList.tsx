import { TProductList } from "types/productTypes";
import ProductCard from "./ProductCard";
import "./productList.scss";
import { useAppSelector } from "src/redux/hooks/redux";

type ProductListProps = {
  products: TProductList;
};

function ProductList({ products }: ProductListProps) {
  return (
    <ul className='product-list'>
      {products.map((el) => (
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
