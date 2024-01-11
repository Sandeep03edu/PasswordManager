import React from "react";
import { Modal } from "react-bootstrap";
import DisplayFullCreditCard from "./DisplayFullCreditCard";

const DisplayCardDetailsModal = ({ showModal, handleClose, card }) => {
  return (
    <Modal show={showModal} onHide={handleClose}>
      <Modal.Header closeButton className="bg-primary text-white px-3 py-2">
        <Modal.Title>Card Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <DisplayFullCreditCard card={card} />
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
    </Modal>
  );
};

export default DisplayCardDetailsModal;
