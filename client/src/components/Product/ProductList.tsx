import { TProductList } from "types/productTypes";
import ProductCard from "./ProductCard";
import './productList.scss'

type ProductListProps = {
  productsList: TProductList;
};

function ProductList({ productsList }: ProductListProps) {
  console.log(productsList);

  return (
    <ul className='product-list'>
      {productsList.map((el) => (
        <ProductCard {...el} key={el.id} />
      ))}
    </ul>
  );
}

export default ProductList;
