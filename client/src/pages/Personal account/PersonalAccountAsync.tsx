import { lazy } from "react";

const PersonalAccountAsync = lazy(() => import("./PersonalAccount"));
export default PersonalAccountAsync;
