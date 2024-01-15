import React, { useState } from "react";
import { capitalizeWords } from "./ModiyText";

const DisplayUpperCreditCard = ({ card, onClick, onEdit, onDelete }) => {
  const styles = {
    creditCard: {
      position: "relative",
      width: "250px",
      height: "170px",

      borderRadius: "20px",
      overflow: "hidden",
      boxShadow: "0px 8px 16px rgba(0, 0, 0, 0.3)",
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-between",
      background: "linear-gradient(120deg, #4a5568, #2d3748)",
      color: "white",
      fontFamily: "Arial, sans-serif",
      padding: "15px",
    },
    creditCardDetails: {
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-between",
      flexGrow: 1,
      marginTop: "5px", // Added margin top for spacing
    },
    backgroundCircle: {
      position: "absolute",
      top: "-80px",
      right: "-80px",
      width: "200px",
      height: "200px",
      borderRadius: "50%",
      background: "rgba(255, 255, 255, 0.1)",
    },
    dataFont: {
      fontSize: "0.9rem",
    },

    number: {
      fontSize: "1rem", // Adjusted font size
      letterSpacing: "3px",
      wordSpacing: "10px",
      marginTop: "5px",
      marginBottom: "5px",
      display: "flex",
      justifyContent: "space-between",
    },
    holder: {
      fontSize: "1.25rem", // Adjusted font size
      marginTop: "0px", // Increased margin top for cardholder name
      fontFamily: "Georgia, serif", // Changed font family
    },
    bankIssuerRow: {
      display: "flex",
      justifyContent: "space-between", // Align columns horizontally
      marginTop: "10px",
      marginBottom: "10px",
      alignItems: "center",
    },
    bankIssuerColumn: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },
    bankLogo: {
      width: "auto",
      height: "20px",
      marginBottom: "5px",
    },
    issuerLogo: {
      width: "auto",
      height: "20px",
      marginBottom: "5px",
    },
    bankText: {
      fontSize: "0.8rem",
      marginTop: "5px",
      opacity: "0.7",
      maxWidth: "80px", // Added max-width
      overflow: "hidden",
      textOverflow: "ellipsis",
      whiteSpace: "nowrap", // Set to nowrap to limit to one line
    },
    title: {
      fontFamily: "'Roboto', sans-serif", // Change the font family as needed
      fontSize: "1.5rem",
    },
  };

  const [isHovered, setIsHovered] = useState(false);

  const formattedCardNumber = (cardNumber) => {
    if (!cardNumber) return "";
    var cardNum = cardNumber;
    if (cardNumber.length === 4) {
      cardNum = "********" + cardNumber;
    }
    return cardNum && cardNum.match(/.{1,4}/g).join(" ");
  };

  return (
    <div
      style={{ width: "fit-content" }}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div style={styles.creditCard} onClick={() => onClick()}>
        <div style={styles.backgroundCircle}></div>
        <div style={styles.creditCardDetails}>
          <div>
            <div style={styles.holder}>
              {capitalizeWords(card.cardHolderName)}
            </div>
            <div style={styles.number}>
              {formattedCardNumber(card.cardNumber)}
            </div>
          </div>
          <div style={styles.bankIssuerRow}>
            <div style={styles.bankIssuerColumn}>
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/SBI-logo.svg/2048px-SBI-logo.svg.png"
                alt="Bank Logo"
                style={styles.bankLogo}
              />
              <span style={styles.bankText}>
                {capitalizeWords(card.issuerName)}
              </span>
            </div>
            <div style={styles.bankIssuerColumn}>
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/b/b7/MasterCard_Logo.svg"
                alt="Issuer Logo"
                style={styles.issuerLogo}
              />
              <span style={styles.bankText}>
                {capitalizeWords(card.cardType)}
              </span>
            </div>
          </div>
        </div>
      </div>
      <div style={!isHovered ? { opacity: "0" } : {}} className="hover-button">
        <div
          className="py-1 px-2"
          style={{
            display: "flex",
            minWidth: "100%",
            justifyContent: "space-between",
          }}
        >
          <button
            className="btn btn-link text-decoration-none me-1"
            style={{ display: "flex", alignItems: "center" }}
            onClick={(e) => {
              e.preventDefault();
              onEdit();
            }}
          >
            <i className="fas fa-pencil-alt  me-1" style={styles.dataFont}></i>
            <p className="m-0 p-0" style={styles.dataFont}>
              Edit
            </p>
          </button>
          <button
            className="btn btn-link text-decoration-none"
            style={{
              color: "#ff0000",
              display: "flex",
              alignItems: "center",
            }}
            onClick={(e) => {
              e.preventDefault();
              onDelete();
            }}
          >
            <i className="fas fa-trash me-1" style={styles.dataFont}></i>
            <div className="m-0 p-0" style={styles.dataFont}>
              Delete
            </div>
          </button>
        </div>
      </div>
    </div>
  );
};

export default DisplayUpperCreditCard;
