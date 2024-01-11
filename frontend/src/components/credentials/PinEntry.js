import { Modal } from "react-bootstrap";

import React, { useState } from "react";

const PinEntry = ({ showModal, handleClose, keyTitle, keyLimit }) => {
  const [pin, setPin] = useState("");

  const handlePinChange = (event) => {
    // Limit input to keyLimit digits
    const newPin = event.target.value.slice(0, keyLimit);
    setPin(newPin);
  };

  const handleVerifyClick = () => {
    // Add your verification logic here
    console.log(`Verifying PIN: ${pin}`);
  };

  return (
    <Modal show={showModal} onHide={handleClose} >
      <Modal.Header closeButton className="bg-primary text-white px-3 py-2">
        <Modal.Title>Verification</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div>
          <form>
            <div
              className="mb-3"
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                width: "100%",
              }}
            >
              <label htmlFor="pinInput" className="form-label">
                {keyTitle}
              </label>
              <input
                type="number"
                className="form-control"
                id="pinInput"
                value={pin}
                onChange={handlePinChange}
                maxLength={keyLimit}
                minLength={keyLimit}
              />
            </div>
            <button
              type="button"
              className="btn btn-primary "
              onClick={handleVerifyClick}
            >
              Verify
            </button>
          </form>
        </div>
      </Modal.Body>
      <Modal.Footer>
        {/* <Button
          variant="secondary"
          onClick={handleClose}
          className="text-danger"
        >
          Close
        </Button> */}
      </Modal.Footer>
    </Modal>
  );
};

export default PinEntry;
