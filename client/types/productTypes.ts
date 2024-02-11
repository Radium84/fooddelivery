type TCategory = {
  id: number;
  name: string;
  endpointName: string;
  description: string;
};

type TCategoryList = TCategory[];

type TProduct = {
  id: number;
  name: string;
  categoryId: number;
  composition: string;
  description: string;
  price: number;
  isDiscount: boolean;
  discountName: string | null;
  calculatedPrice: number;
};

type TProductList = TProduct[];

export { TCategoryList, TProductList, TProduct, TCategory };
