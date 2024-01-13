import React, { createContext, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const ToastStateContext = createContext();

export const useToastState = () => useContext(ToastStateContext);

export const ToastStateProvider = ({ children }) => {
  const [toastState, setToastState] = useState(null);

  // const navigate = useNavigate();
  // useEffect(() => {
  //   const userInfo = JSON.parse(localStorage.getItem("userData"));
  //   console.log(navigate.toString());
  //   if (!userInfo) {
  //     navigate("/");
  //   }
  // }, [navigate]);

  const updateToastState = (newValue) => {
    setToastState(newValue);
  };

  return (
    <ToastStateContext.Provider value={{ toastState, updateToastState }}>
      {children}
    </ToastStateContext.Provider>
  );
};
