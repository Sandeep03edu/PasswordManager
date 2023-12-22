const asyncHandler = require("express-async-handler");

const generateJWT = require("../config/generateJWT");

const Card = require("../models/card");
const {
  encryptString,
  decryptString,
  decryptCard,
} = require("../config/encryptionAlgorithm");

const addUpdateCard = asyncHandler(async (req, res) => {
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

  const cardExit = await Card.findOne({
    createdBy: createdBy,
    appId: appId,
  }).select("-__v -createdAt ");

  if (!cardExit) {
    var card = await Card.create({
      appId: appId,
      createdBy: createdBy,
      issuerName: issuerName,
      cardHolderName: cardHolderName,
      cardType: cardType,
      cardNumber: encryptString(cardNumber, createdBy, appId),
      cvv: encryptString(cvv, createdBy, appId),
      pin: encryptString(pin, createdBy, appId),
      issueDate: encryptString(issueDate, createdBy, appId),
      expiryDate: encryptString(expiryDate, createdBy, appId),
      isSynced: 1,
      creationTime: creationTime,
    });

    card = await Card.findOne({ createdBy: createdBy, appId: appId }).select(
      "-__v -createdAt "
    );

    if (card) {
      // Card Created Successfully!!
      return res.status(200).json({
        success: true,
        cards: [decryptCard(card, card.createdBy, card.appId)],
      });
    } else {
      return res.status(400).json({
        success: false,
        error: "An internal error occurred!!",
      });
    }
  } else {
    // Update Card
    const updateCard = await Card.findByIdAndUpdate(
      cardExit._id,
      {
        appId: appId,
        createdBy: createdBy,
        issuerName: issuerName,
        cardHolderName: cardHolderName,
        cardType: cardType,
        cardNumber: encryptString(cardNumber, createdBy, appId),
        cvv: encryptString(cvv, createdBy, appId),
        pin: encryptString(pin, createdBy, appId),
        issueDate: encryptString(issueDate, createdBy, appId),
        expiryDate: encryptString(expiryDate, createdBy, appId),
        isSynced: 1,
        creationTime: creationTime,
      },
      {
        new: true,
      }
    ).select("-__v -createdAt ");

    return res.status(200).json({
      success: true,
      cards: [decryptCard(updateCard, updateCard.createdBy, updateCard.appId)],
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

  const userCards = await Card.find({ createdBy: loggedUser._id }).select(
    "-__v -createdAt "
  );

  for (let i = 0; i < userCards.length; ++i) {
    var currCard = userCards[i];
    userCards[i] = decryptCard(currCard, currCard.createdBy, currCard.appId);
  }

  return res.status(200).json({
    success: true,
    cards: userCards,
  });
});

const getCardDetails = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const { appId } = req.body;
  if (!appId) {
    return res.status(400).json({
      success: false,
      error: "AppId Missing",
    });
  }

  const userCard = await Card.find({
    createdBy: loggedUser._id,
    appId: appId,
  }).select("-__v -createdAt ");

  for (let i = 0; i < userCard.length; ++i) {
    var currCard = userCard[i];
    userCard[i] = decryptCard(currCard, currCard.createdBy, currCard.appId);
  }

  if (userCard) {
    return res.status(200).json({
      success: true,
      cards: userCard,
    });
  } else {
    res.status(400).json({
      success: false,
      error: "Card does not exist!!",
    });
  }
});

const deleteCardById = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const { appId } = req.body;
  if (!appId) {
    return res.status(400).json({
      success: false,
      error: "AppId Missing",
    });
  }

  try {
    const response = await Card.deleteOne({
      appId: appId,
      createdBy: loggedUser._id,
    });

    const deletedCard = response.deletedCount;
    if (deletedCard != 0) {
      // Delete successful
      res.status(200).json({
        success: true,
        cards: [{ appId }],
      });
    } else {
      res.status(200).json({
        success: false,
        cards: [{ appId }],
        error: "No Card found!!",
      });
    }
  } catch (error) {
    res.status(200).json({
      success: false,
      cards: [{ appId }],
      error: error.message,
    });
  }
});

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
    return "Created By Missing";
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

module.exports = { addUpdateCard, getAllCards, getCardDetails, deleteCardById };
