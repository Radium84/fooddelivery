import React from "react";
import ProductList from "../../components/Product/ProductList";
import products from "../../../mockData/products";

function Main() {
  return (
    <main className='main'>
      <div className='container'>
        <h1 className='main__title'>Меню</h1>
        <ProductList productsList={products} />
      </div>
    </main>
  );
}

export default Main;
