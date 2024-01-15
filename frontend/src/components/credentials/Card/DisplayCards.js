import React, { useState, useEffect } from "react";
import DisplayUpperCreditCard from "../../utils/DisplayUpperCreditCard";
import PinEntry from "../PinEntry";
import { useNavigate } from "react-router-dom";
import { getUserToken } from "../../utils/UserInfo";
import axios from "axios";
import { BASE_URL, EndPoints } from "../../utils/NetworkEndPoints";
import { useToastState } from "../../context/ToastContext";

const DisplayCards = () => {
  const [showPinModal, setPinEntryShowModal] = useState(null);

  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [cards, setCards] = useState([]);

  const { updateToastState } = useToastState();

  const [editClicked, setEditClicked] = useState(false);
  const [deleteClicked, setDeleteClicked] = useState(false);

  const deleteCardDetails = async (setVerifyEnable, setPin) => {
    const appId = showPinModal;
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
          "Content-type": "application/json",
        },
      };

      const body = {
        appId: appId,
      };

      const { data } = await axios.delete(
        `${BASE_URL}/${EndPoints.deleteCard}`,
        body,
        config
      );

      setVerifyEnable(true);
      setPin("");

      if (data.success) {
        setPinEntryShowModal(null);
        const restCards = cards.filter((card) => card.appId !== appId);
        setCards(restCards);
      } else {
        updateToastState({ message: data.error, variant: "Danger" });
      }
    } catch (error) {
      updateToastState({ message: error, variant: "Danger" });
    }
  };

  const fetchCardDetails = async (setVerifyEnable, setPin) => {
    const appId = showPinModal;
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
          "Content-type": "application/json",
        },
      };

      const body = {
        appId: appId,
      };

      const { data } = await axios.post(
        `${BASE_URL}/${EndPoints.fetchCardDetails}`,
        body,
        config
      );

      setVerifyEnable(true);
      setPin("");

      if (data.success) {
        setPinEntryShowModal(null);
        if (data.cards[0]) {
          var card = data.cards[0];
          if (editClicked) {
            card.editable = true;
          }
          window.open(
            `/credential/display/card?cardData=${encodeURIComponent(
              JSON.stringify(card)
            )}`,
            "_blank"
          );
        }
      } else {
        updateToastState({ message: data.error, variant: "Danger" });
      }
    } catch (error) {
      updateToastState({ message: error, variant: "Danger" });
    }
  };

  const fetchPaginatedCards = async () => {
    try {
      const config = {
        headers: {
          Authorization: `Bearer ${getUserToken()}`,
        },
      };

      const { data } = await axios.get(
        `${BASE_URL}/${EndPoints.securePaginatedAllCard}${currentPage}`,
        config
      );

      if (data.success) {
        setCards(data.cards);
        setCurrentPage(data.currentPage);
        setTotalPages(data.totalPage);
      } else {
        updateToastState({ message: data.error, variant: "Danger" });
      }
    } catch (error) {
      updateToastState({ message: error, variant: "Danger" });
    }
  };

  useEffect(() => {
    fetchPaginatedCards();
  }, [currentPage]);

  const styles = {
    buttonFont: {
      fontSize: "1rem",
    },
  };

  const handlePinEntryCloseModal = () => {
    setPinEntryShowModal(null);
  };

  const handlePinEntryCardClick = (id) => {
    // Handle click action here based on the row ID
    setPinEntryShowModal(id);
    setEditClicked(false);
    setDeleteClicked(false);
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
      <div className="m-2">
        <div className="m-0 bg-white rounded px-0 py-2 mt-2">
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
                  key={card.appId}
                  className="col m-2"
                  style={{
                    maxWidth: "250px",
                    cursor: "pointer",
                  }}
                >
                  <DisplayUpperCreditCard
                    card={card}
                    onClick={() => {
                      handlePinEntryCardClick(card.appId);
                    }}
                    onEdit={() => {
                      setPinEntryShowModal(card.appId);
                      setEditClicked(true);
                      setDeleteClicked(false);
                    }}
                    onDelete={() => {
                      setPinEntryShowModal(card.appId);
                      setEditClicked(false);
                      setDeleteClicked(true);
                    }}
                  />
                </div>
              );
            })}
          </div>
          {cards.length !== 0 && (
            <div className="row mx-0 mt-3 justify-content-end">
              <div className="col-auto">
                <button
                  className="btn btn-link text-decoration-none me-2"
                  disabled={currentPage <= 1}
                  onClick={(e) => {
                    e.preventDefault();
                    setCurrentPage(Math.max(1, currentPage - 1));
                  }}
                >
                  <i
                    className="fas fa-chevron-left"
                    style={styles.buttonFont}
                  ></i>
                  Prev
                </button>
                <span>{currentPage}</span>
                <button
                  className="btn btn-link text-decoration-none ms-2"
                  disabled={currentPage >= totalPages}
                  onClick={(e) => {
                    e.preventDefault();
                    setCurrentPage(Math.min(totalPages, currentPage + 1));
                  }}
                >
                  Next
                  <i
                    className="fas fa-chevron-right"
                    style={styles.buttonFont}
                  ></i>
                </button>
              </div>
            </div>
          )}
        </div>
      </div>

      <PinEntry
        showModal={showPinModal !== null}
        handleClose={() => {
          handlePinEntryCloseModal();
        }}
        keyTitle={"App Pin"}
        keyLimit={6}
        handleResponse={(setVerifyEnable, setPin) => {
          if (deleteClicked) {
            deleteCardDetails(setVerifyEnable, setPin);
          } else {
            fetchCardDetails(setVerifyEnable, setPin);
          }
        }}
      />
    </div>
  );
};

export default DisplayCards;
