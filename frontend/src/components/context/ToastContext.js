import React, { createContext, useContext, useState } from "react";

const ToastStateContext = createContext();

export const useToastState = () => useContext(ToastStateContext);

export const ToastStateProvider = ({ children }) => {
  const [toastState, setToastState] = useState(null);

  const updateToastState = (newValue) => {
    setToastState(newValue);
  };

  return (
    <ToastStateContext.Provider value={{ toastState, updateToastState }}>
      {children}
    </ToastStateContext.Provider>
  );
};
