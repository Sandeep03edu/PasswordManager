import React, { useState } from "react";
import { useLocation } from "react-router-dom";

const AddDisplayCreditCard = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const cardData = queryParams.get("cardData");

  const card = cardData ? JSON.parse(decodeURIComponent(cardData)) : null;
  const cardId = card ? card._id : null;

  const [editable, setEditable] = useState(cardId === null);

  const [buttonLabel, setButtonLabel] = useState(
    cardId !== null ? "Edit Card" : "Add Card"
  );
  const [label, setLabel] = useState(
    cardId !== null ? "Your Card" : "Add Card"
  );

  const [cardHolderName, setCardHolderName] = useState(
    card ? card.cardHolderName : "Sandeep Mishra"
  );
  const [cardNumber, setCardNumber] = useState(
    card ? card.cardNumber : "1234567812345678"
  );
  const [issuerName, setIssuerName] = useState(
    card ? card.issuerName : "State Bank of India"
  );
  const [cardType, setCardType] = useState(
    card ? card.cardType : "Master Card"
  );
  const [expiryDate, setExpiryDate] = useState(
    card ? card.expiryDate : "12/26"
  );
  const [issueDate, setIssueDate] = useState(card ? card.issueDate : "04/20");
  const [cvv, setCVV] = useState(card ? card.cvv : "123");
  const [pin, setPin] = useState(card ? card.pin : "1234");

  const [issuerNameError, setIssuerNameError] = useState("");
  const [cardHolderNameError, setCardHolderNameError] = useState("");
  const [cardNumberError, setCardNumberError] = useState("");
  const [cardTypeError, setCardTypeError] = useState("");
  const [dateError, setDateError] = useState("");
  const [securityKeyError, setSecurityKeyError] = useState("");

  const handleButtonClick = () => {
    if (cardId !== null) {
      // Display Card present
      if (buttonLabel === "Edit Card") {
        setEditable(true);
        setButtonLabel("Update Card");
        setLabel("Update Card Details");
      } else if (buttonLabel === "Update Card") {
        // Update details for card
      }
    } else {
      // Add Card Data
    }
  };

  function validateCard(
    cardHolderName,
    cardNumber,
    issuerName,
    cardType,
    expiryDate,
    issueDate,
    cvv,
    pin
  ) {
    if (cardId !== null) {
      return;
    }
    if (!issuerName || issuerName.trim() === "") {
      setIssuerNameError("Card issuer name can't be empty!!");
    } else {
      setIssuerNameError("");
    }

    if (cardHolderName.trim() === "") {
      setCardHolderNameError("Card holder name can't be empty!!");
    } else if (cardHolderName.length < 3) {
      setCardHolderNameError("Card holder name invalid!!");
    } else {
      setCardHolderNameError("");
    }

    if (cardNumber.trim() === "") {
      setCardNumberError("Card number can't be empty!!");
    } else if (cardNumber.length < 6) {
      setCardNumberError("Card number should be 6 digit long");
    } else {
      setCardNumberError("");
    }

    if (!cardType || cardType.trim() === "") {
      setCardTypeError("Card type can't be empty!!");
    } else {
      setCardTypeError("");
    }

    if (
      (!issueDate || issueDate.trim() === "") &&
      (!expiryDate || expiryDate.trim() === "")
    ) {
      setDateError("Issue date or Expiry date is compulsory!!");
    } else {
      setDateError("");
    }

    if ((!pin || pin.trim() === "") && !cvv && cvv.trim() === "") {
      setSecurityKeyError("Pin or Cvv is compulsory!!");
    } else {
      setSecurityKeyError("");
    }
  }

  const styles = {
    creditCard: {
      position: "relative",
      width: "300px",
      height: "200px",
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

    number: {
      fontSize: "1.15rem", // Adjusted font size
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
      minWidth: "250px",
    },
    bankIssuerColumn: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
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
      fontSize: "0.8rem",
      marginTop: "5px",
      opacity: "0.7",
      maxWidth: "80px", // Added max-width
      overflow: "hidden",
      textOverflow: "ellipsis",
      whiteSpace: "nowrap", // Set to nowrap to limit to one line
    },
    creditCardBack: {
      position: "relative",
      width: "300px",
      height: "200px",
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
    },

    bankIssuerColumnBack: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },

    bankTextBack: {
      fontSize: "1.75rem",
      marginTop: "5px",
      opacity: "1",
      overflow: "hidden",
      textOverflow: "ellipsis",
      whiteSpace: "nowrap", // Set to nowrap to limit to one line
    },

    label: {
      fontSize: "12px",
      opacity: "0.7",
    },

    value: {
      fontSize: "14px",
    },
    formContainer: {
      width: "100%",
      height: "auto",
      marginRight: "20px",
    },
    form: {
      display: "flex",
      flexDirection: "column",
      gap: "10px",
      alignItems: "center",
      padding: "20px",
      border: "1px solid #ccc",
      borderRadius: "10px",
      width: "100%",
      background: "#f9f9f9",
    },
    formHeader: {
      fontSize: "24px",
      fontWeight: "bold",
      marginBottom: "10px",
    },
    formInput: {
      width: "100%",
      padding: "8px",
      borderRadius: "5px",
      border: "1px solid #ccc",
      fontSize: "16px",
    },
    formButton: {
      width: "100%",
      padding: "10px",
      borderRadius: "5px",
      border: "none",
      backgroundColor: "#4a5568",
      color: "white",
      fontSize: "16px",
      cursor: "pointer",
    },
    formSelectInput: {
      width: "100%",
      padding: "8px",
      borderRadius: "5px",
      border: "1px solid #ccc",
      fontSize: "16px",
      pointerEvents: "none",
    },
  };

  const handleAddCard = (event) => {
    event.preventDefault();
    validateCard(
      cardHolderName,
      cardNumber,
      issuerName,
      cardType,
      expiryDate,
      issueDate,
      cvv,
      pin
    );
  };

  // Format card number with space every 4 digits
  const formattedCardNumber =
    cardNumber && cardNumber.match(/.{1,4}/g).join(" ");

  const handleDateChange = (e, setDateFunction) => {
    var value = e.target.value.replace("/", "");
    const isNumeric = value.length === 0 || /^\d+$/.test(value);

    if (isNumeric && value.length <= 4) {
      if (value.length > 2) {
        setDateFunction(value.slice(0, 2) + "/" + value.slice(2));
      } else {
        setDateFunction(value);
      }
    }
  };

  return (
    <div className="container-fluid">
      <div className="row">
        <div
          className="col-md-6"
          style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-around",
            alignItems: "center",
          }}
        >
          <div
            className="m-3 d-flex justify-content-center align-items-center"
            style={styles.creditCard}
          >
            <div style={styles.backgroundCircle}></div>
            <div style={styles.creditCardDetails}>
              <div>
                <div style={styles.holder}>{cardHolderName}</div>
                <div style={styles.number}>{formattedCardNumber}</div>
              </div>
              <div style={styles.bankIssuerRow}>
                <div style={styles.bankIssuerColumn}>
                  <img
                    src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/SBI-logo.svg/2048px-SBI-logo.svg.png"
                    alt="Bank Logo"
                    style={styles.bankLogo}
                  />
                  <span style={styles.bankText}>{issuerName}</span>
                </div>
                <div style={styles.bankIssuerColumn}>
                  <img
                    src="https://upload.wikimedia.org/wikipedia/commons/b/b7/MasterCard_Logo.svg"
                    alt="Issuer Logo"
                    style={styles.issuerLogo}
                  />
                  <span style={styles.bankText}>{cardType}</span>
                </div>
              </div>
            </div>
          </div>
          {/* Back side */}
          <div
            className="m-3 d-flex justify-content-center align-items-center"
            style={styles.creditCardBack}
          >
            <div style={styles.creditCardBackDetails}>
              <div style={styles.bankIssuerRowBack}>
                <span style={styles.bankTextBack}>{issuerName}</span>
              </div>
              <div style={styles.bankIssuerRowBack}>
                <div style={styles.bankIssuerColumnBack}>
                  <span style={styles.label}>Issue Date</span>
                  <span style={styles.value} className="mb-3">
                    {issueDate}
                  </span>
                  <span style={styles.label}>Expiry Date</span>
                  <span style={styles.value}>{expiryDate}</span>
                </div>
                <div style={styles.bankIssuerColumnBack}>
                  <span style={styles.label}>CVV</span>
                  <span style={styles.value} className="mb-3">
                    {cvv}
                  </span>
                  <span style={styles.label}>PIN</span>
                  <span style={styles.value}>{pin}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          {/* Form */}
          <div
            className="d-flex justify-content-center align-items-center"
            style={styles.formContainer}
          >
            <form
              className="mx-2 my-3"
              style={styles.form}
              onClick={handleAddCard}
            >
              <div style={styles.formHeader}>{label}</div>
              <input
                type="text"
                placeholder="Card Holder Name"
                style={styles.formInput}
                value={cardHolderName}
                onChange={(e) => setCardHolderName(e.target.value)}
                required
                // readonly={editable ? undefined : "readonly"}
                readonly={editable ? undefined : "readonly"}
              />
              {cardHolderNameError && (
                <p
                  className="text-danger"
                  style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                >
                  {cardHolderNameError}
                </p>
              )}
              <input
                type="number"
                placeholder="Card Number"
                style={styles.formInput}
                value={cardNumber}
                onChange={(e) => {
                  if (e.target.value.length <= 16) {
                    setCardNumber(e.target.value);
                  }
                }}
                required
                readonly={editable ? undefined : "readonly"}
              />
              {cardNumberError && (
                <p
                  className="text-danger"
                  style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                >
                  {cardNumberError}
                </p>
              )}
              <select
                style={editable ? styles.formInput : styles.formSelectInput}
                value={issuerName}
                onChange={(e) => setIssuerName(e.target.value)}
                required
              >
                <option value="">Select Bank</option>
                <option value="State Bank of India">State Bank of India</option>
                <option value="PNB">PNB</option>
              </select>
              {cardTypeError && (
                <p
                  className="text-danger"
                  style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                >
                  {cardTypeError}
                </p>
              )}
              <select
                style={editable ? styles.formInput : styles.formSelectInput}
                value={cardType}
                onChange={(e) => setCardType(e.target.value)}
                required
                readonly={editable ? undefined : "readonly"}
              >
                <option value="">Select Issuer</option>
                <option value="Master Card">Master Card</option>
                <option value="SBI">SBI</option>
              </select>
              {issuerNameError && (
                <p
                  className="text-danger"
                  style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                >
                  {issuerNameError}
                </p>
              )}

              <input
                type="text"
                placeholder="Expiry Date (MM/YY)"
                style={styles.formInput}
                value={expiryDate}
                onChange={(e) => handleDateChange(e, setExpiryDate)}
                maxLength={5}
                required
                readonly={editable ? undefined : "readonly"}
              />
              <input
                type="text"
                placeholder="Issue Date (MM/YY)"
                style={styles.formInput}
                value={issueDate}
                onChange={(e) => handleDateChange(e, setIssueDate)}
                maxLength={5}
                required
                readonly={editable ? undefined : "readonly"}
              />
              {dateError && (
                <p
                  className="text-danger"
                  style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                >
                  {dateError}
                </p>
              )}
              <input
                type="number"
                placeholder="CVV"
                style={styles.formInput}
                value={cvv}
                onChange={(e) => setCVV(e.target.value)}
                required
                readonly={editable ? undefined : "readonly"}
              />
              <input
                type="number"
                placeholder="PIN"
                style={styles.formInput}
                value={pin}
                onChange={(e) => setPin(e.target.value)}
                required
                readonly={editable ? undefined : "readonly"}
              />
              {securityKeyError && (
                <p
                  className="text-danger"
                  style={{ fontSize: "0.8rem", margin: 0, width: "100%" }}
                >
                  {securityKeyError}
                </p>
              )}
              <button
                type="submit"
                style={styles.formButton}
                onClick={(e) => {
                  e.preventDefault();
                  handleButtonClick();
                }}
              >
                {buttonLabel}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddDisplayCreditCard;
