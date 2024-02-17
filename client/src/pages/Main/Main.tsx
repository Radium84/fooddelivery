import React, { useEffect } from "react";
import ProductList from "../../components/Product/ProductList";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import { fetchAllProducts } from "../../redux/actionCreators/producrAction";

function Main() {
  const { products } = useAppSelector((state) => state.products);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchAllProducts());
  }, []);

  return (
    <main className='main'>
      <h1 className='main__title'>Меню</h1>
      <ProductList productsList={products} />
    </main>
  );
}

export default Main;
