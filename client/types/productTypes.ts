type TypeCategory = {
  id: number;
  name: string;
  iconSrc?: string;
};

type TypeProduct = {
  id: number;
  name: string;
  categoryName: string;
  composition: string;
  description: string;
  price: number;
  discountName: string | null;
  calculatedPrice: number;
};

export {TypeCategory, TypeProduct};
