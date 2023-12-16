const asyncHandler = require("express-async-handler");

const generateJWT = require("../config/generateJWT");
const { encryptyString } = require("../config/encryptionAlgorithm");

const Card = require("../models/card");

const saveCard = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const createdBy = req.user._id;

  const {
    appId,
    issuerName,
    cardHolderName,
    cardType,
    cardNumber,
    cvv,
    pin,
    issueDate,
    expiryDate,
    creationTime,
  } = req.body;

  const validateCard = cardValidator(
    appId,
    createdBy,
    issuerName,
    cardHolderName,
    cardType,
    cardNumber,
    cvv,
    pin,
    issueDate,
    expiryDate,
    creationTime
  );

  if (validateCard.length !== 0) {
    return res.status(400).json({
      success: false,
      error: validateCard,
    });
  }

  const cardExit = await Card.findOne({ createdBy: createdBy, appId: appId });

  if (!cardExit) {
    const card = await Card.create({
      appId: appId,
      createdBy: createdBy,
      issuerName: issuerName,
      cardHolderName: cardHolderName,
      cardType: encryptyString(cardType),
      cardNumber: encryptyString(cardNumber),
      cvv: encryptyString(cvv),
      pin: encryptyString(pin),
      issueDate: encryptyString(issueDate),
      expiryDate: encryptyString(expiryDate),
      isSynced: true,
      creationTime: creationTime,
    });

    if (card) {
      // Card Created Successfully!!
      return res.status(200).json({
        success: true,
        _id: card._id,
        appId: card.appId,
        createdBy: card.createdBy,
        issuerName: card.issuerName,
        cardHolderName: card.cardHolderName,
        cardType: card.cardType,
        cardNumber: card.cardNumber,
        cvv: card.cvv,
        pin: card.pin,
        issueDate: card.issueDate,
        expiryDate: card.expiryDate,
        isSynced: card.isSynced,
        creationTime: card.creationTime,
      });
    } else {
      return res.status(400).json({
        success: false,
        error: "An internal error occurred!!",
      });
    }
  } else {
    return res.status(200).json({
      success: true,
      _id: cardExit._id,
      appId: cardExit.appId,
      createdBy: cardExit.createdBy,
      issuerName: cardExit.issuerName,
      cardHolderName: cardExit.cardHolderName,
      cardType: cardExit.cardType,
      cardNumber: cardExit.cardNumber,
      cvv: cardExit.cvv,
      pin: cardExit.pin,
      issueDate: cardExit.issueDate,
      expiryDate: cardExit.expiryDate,
      isSynced: cardExit.isSynced,
      creationTime: cardExit.creationTime,
    });
  }
});

const getAllCards = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const userCards = await Card.find({ createdBy: loggedUser._id });

  return res.status(200).json({
    success: true,
    allCards: userCards,
  });
});

const getCardDetails = asyncHandler(async (req, res) => {});

const cardValidator = (
  appId,
  createdBy,
  issuerName,
  cardHolderName,
  cardType,
  cardNumber,
  cvv,
  pin,
  issueDate,
  expiryDate,
  isSynced,
  creationTime
) => {
  if (!appId) {
    return "AppId Missing";
  }

  if (!createdBy) {
    return "CreatedBy Missing";
  }

  if (!issuerName) {
    return "Card issuer name can't be empty!!";
  }

  if (issuerName.length < 3) {
    return "Card issuer name invalid!!";
  }

  if (!cardHolderName) {
    return "Card holder name can't be empty!!";
  }

  if (cardHolderName.length < 3) {
    return "Card holder name invalid!!";
  }

  if (!cardNumber) {
    return "Card number can't be empty!!";
  }

  if (cardNumber.length < 6) {
    return "Card number should be 6 digit long";
  }

  if (!cardType) {
    return "Card type can't be empty!!";
  }

  if (cardType.length < 3) {
    return "Invalid card type";
  }

  if (!issueDate && !expiryDate) {
    return "Issue date or Expiry date is compulsory!!";
  }

  if (!cvv && !pin) {
    return "Pin or Cvv is compulsory!!";
  }

  return "";
};

module.exports = { saveCard, getAllCards, getCardDetails };
