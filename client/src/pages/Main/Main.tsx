import React, { useEffect } from "react";
import ProductList from "../../components/Product/ProductList";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/redux";
import { fetchAllProducts } from "../../redux/actionCreators/producrAction";
import { useLocation } from "react-router-dom";
import { log } from "console";

function Main() {
  const dispatch = useAppDispatch();
  const { products, productsByCategory } = useAppSelector(
    (state) => state.products
  );

  const location = useLocation().pathname;
  console.log("====================================");
  console.log(location);
  console.log("====================================");

  useEffect(() => {
    dispatch(fetchAllProducts());
  }, []);

  return (
    <main className='main'>
      <h1 className='main__title'>Меню</h1>
      <ProductList
        products={location === "/" ? products : productsByCategory}
      />
    </main>
  );
}

export default Main;
