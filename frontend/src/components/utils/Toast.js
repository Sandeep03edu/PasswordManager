import React, { useState } from "react";
import Toast from "react-bootstrap/Toast";
import ToastContainer from "react-bootstrap/ToastContainer";

const ToastDisplay = ({ title, toastVariant }) => {
  const [show, setShow] = useState(true);
  const toggleShow = () => setShow(!show);

  return (
    <div>
      <ToastContainer
        className="p-3"
        position={"bottom-end"}
        style={{ zIndex: 1 }}
      >
        <Toast
          show={show}
          delay={3000}
          autohide
          onClose={toggleShow}
          bg={"dark"}
        >
          <Toast.Header>
            <strong className="me-auto">{toastVariant}</strong>
            <small>Just now</small>
          </Toast.Header>
          <Toast.Body className={"text-white"}>{title}</Toast.Body>
        </Toast>
      </ToastContainer>
    </div>
  );
};

export default ToastDisplay;
