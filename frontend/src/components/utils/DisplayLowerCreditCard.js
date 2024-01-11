import React from "react";

const DisplayLowerCreditCard = ({ card }) => {
  const styles = {
    creditCardBack: {
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
    bankLogo: {
      width: "auto",
      height: "40px",
      marginBottom: "5px",
    },
    issuerLogo: {
      width: "auto",
      height: "40px",
      marginBottom: "5px",
    },
    bankText: {
      fontSize: "0.9rem",
      marginTop: "5px",
      opacity: "0.7",
      maxWidth: "150px", // Added max-width
      overflow: "hidden",
      textOverflow: "ellipsis",
      whiteSpace: "nowrap",
    },

    creditCardBackDetails: {
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-between",
      flexGrow: 1,
      marginTop: "5px",
      width: "100%",
    },

    bankIssuerRowBack: {
      display: "flex",
      justifyContent: "space-around",
      marginTop: "10px",
      marginBottom: "10px",
      alignItems: "center",
      width: "100%",
      maxWidth: "220px",
      overflow: "hidden",
    },

    bankIssuerColumnBack: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },

    bankTextBack: {
      fontSize: "15px",
      marginTop: "5px",
      opacity: "1",
      maxWidth: "220px",
      overflow: "hidden",
      textOverflow: "ellipsis",
      whiteSpace: "nowrap",
    },

    label: {
      fontSize: "12px",
      opacity: "0.7",
    },

    value: {
      fontSize: "14px",
      overflow: "hidden",
      textOverflow: "ellipsis",
      whiteSpace: "nowrap",
    },
  };

  return (
    <div
      className="m-3 d-flex justify-content-center align-items-center"
      style={styles.creditCardBack}
    >
      <div style={styles.creditCardBackDetails}>
        <div style={styles.bankIssuerRowBack}>
          <span style={styles.bankTextBack}>{card.issuerName}</span>
        </div>
        <div style={styles.bankIssuerRowBack}>
          <div style={styles.bankIssuerColumnBack}>
            <span style={styles.label}>Issue Date</span>
            <span style={styles.value} className="mb-3">
              {card.issueDate}
            </span>
            <span style={styles.label}>Expiry Date</span>
            <span style={styles.value}>{card.expiryDate}</span>
          </div>
          <div style={styles.bankIssuerColumnBack}>
            <span style={styles.label}>CVV</span>
            <span style={styles.value} className="mb-3">
              {card.cvv}
            </span>
            <span style={styles.label}>PIN</span>
            <span style={styles.value}>{card.pin}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DisplayLowerCreditCard;
