import { Modal } from "react-bootstrap";

import React, { useEffect, useState } from "react";
import { getUserToken } from "../utils/UserInfo";
import axios from "axios";
import { BASE_URL, EndPoints } from "../utils/NetworkEndPoints";

const PinEntry = ({
  showModal,
  handleClose,
  handleResponse,
  keyTitle,
  keyLimit,
}) => {
  const [pin, setPin] = useState("123456");
  const [verifyEnable, setVerifyEnable] = useState(true);

  const handlePinChange = (event) => {
    // Limit input to keyLimit digits
    const newPin = event.target.value.slice(0, keyLimit);
    setPin(newPin);
  };

  const handleVerifyClick = async (e) => {
    setVerifyEnable(false);
    e.preventDefault();
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
        },
      };

      const body = {
        appPin: pin,
      };

      const { data } = await axios.post(
        `${BASE_URL}/${EndPoints.verifyAppPin}`,
        body,
        config
      );
      if (data.success) {
        handleResponse(setVerifyEnable, setPin);
      } else {
        setVerifyEnable(true);
      }
    } catch (error) {
      setVerifyEnable(true);
    }
  };

  return (
    <Modal
      show={showModal}
      onHide={() => {
        handleClose();
      }}
    >
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
              disabled={!verifyEnable}
            >
              Verify
            </button>
          </form>
        </div>
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
    </Modal>
  );
};

export default PinEntry;
