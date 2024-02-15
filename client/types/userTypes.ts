import { TProductList } from "./productTypes";

type TUserFormData = {
  firstname: string;
  middlename?: string;
  lastname: string;
  address: string;
  birthday: string;
  username: string;
  password: string;
};

type TUser = {
  id: number;
  firstname: string;
  middlename?: string;
  lastname: string;
  address: string;
  birthday: string;
  favoriteProducts?: null | number[];
  auth: TAuth;
  token: string;
  isAdmin: boolean;
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

export { TUser, TUserList, TUserFormData };
