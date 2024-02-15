import { useNavigate } from "react-router-dom";
const navigate = useNavigate();
const handleNavigate = (path: string) => {
  navigate(path);
};

export default handleNavigate;
