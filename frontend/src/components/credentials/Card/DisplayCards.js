import React, { useState } from "react";
import { cards } from "../../utils/DummyData";
import DisplayUpperCreditCard from "../../utils/DisplayUpperCreditCard";
import PinEntry from "../PinEntry";
import { useNavigate } from "react-router-dom";

const DisplayCards = () => {
  const [showPinModal, setPinEntryShowModal] = useState(false);
  const handleCloseModal = () => {
    setPinEntryShowModal(false);
  };

  const handleCardClick = (id) => {
    // Handle click action here based on the row ID
    console.log(`Row clicked: ${id}`);
    setPinEntryShowModal(true);
  };

  const navigate = useNavigate();

  return (
    <div className="p-0 m-0">
      <nav className="navbar navbar-expand-lg navbar-light bg-light pt-1 pb-1">
        <div className="container-fluid">
          <p className="navbar-brand m-0 p-0">
            <i className="fa-solid fa-credit-card p-0 me-2"></i>
            Your Cards
          </p>
          <div className="navbar-toggler m-0 p-0" type="button">
            <button
              className="btn btn-primary py-1 px-2"
              onClick={() => {
                navigate("/credential/add/card");
              }}
            >
              <i className="fa-solid fa-circle-plus"></i>
            </button>
          </div>
          <div className="collapse navbar-collapse justify-content-end ">
            <ul className="navbar-nav">
              <li className="nav-item me-1">
                <button
                  className="btn btn-primary m-2"
                  onClick={() => {
                    navigate("/credential/add/card");
                  }}
                >
                  <i className="fa-solid fa-circle-plus me-2"></i>
                  Card
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div
        className="row my-2 mx-0 p-0"
        style={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "start",
        }}
      >
        {cards.map((card) => {
          return (
            <div
              className="col m-2"
              style={{
                maxWidth: "250px",
                cursor: "pointer",
              }}
              onClick={() => {
                handleCardClick(card._id);
              }}
            >
              <DisplayUpperCreditCard card={card} />
            </div>
          );
        })}
      </div>

      <PinEntry
        showModal={showPinModal}
        handleClose={handleCloseModal}
        keyTitle={"App Pin"}
        keyLimit={6}
        handleResponse={() => {
          console.log("Response");
        }}
      />
    </div>
  );
};

export default DisplayCards;
