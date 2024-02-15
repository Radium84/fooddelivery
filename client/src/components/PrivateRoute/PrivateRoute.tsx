import React, { ReactComponentElement, useEffect } from "react";
import { useSelector } from "react-redux";
import { Navigate, useNavigate, useParams } from "react-router-dom";

type PrivateRoute = {
  children: JSX.Element;
};
function PrivateRoute({ children }: PrivateRoute) {
  const id = useParams();
  const navigate = useNavigate();
  const authUser = async () => {
    const response = await fetch(`${process.env.REACT_APP_HOST}/`, {
      credentials: "include",
    });
    if (!response.ok) {
      navigate("/auth/signIn");
    }
  };

  useEffect(() => {
    authUser();
  }, []);

  return children;
}

export default PrivateRoute;
