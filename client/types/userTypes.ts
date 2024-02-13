import { TProductList } from "./productTypes";

type TUser = {
  id: number;
  firstname: string;
  middlename?: string;
  lastname: string;
  address: string;
  birthday: string;
  favoriteProducts?: TProductList;
  username: string;
  password: string;
};

type TUserSignIn = {
  id: number;
  firstname: string;
  middlename?: string;
  lastname: string;
  address: string;
  birthday: string;
  favoriteProducts?: null | number[];
  auth: TAuth;
};

type TAuth = {
  id: number;
  username: string;
  password: string;
  roles: Array<TRole>;
};

type TRoleAdmin = {
  roleId: 2;
  name: "ROLE_ADMIN";
};

type TRoleUser = {
  roleId: 1;
  name: "ROLE_USER";
};

type TRole = {
  roleId: number;
  name: string;
};

type TUserList = TUser[];

export { TUser, TUserList, TUserSignIn };
