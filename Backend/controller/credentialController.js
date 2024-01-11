const asyncHandler = require("express-async-handler");
const generateJWT = require("../config/generateJWT");

const Password = require("../models/password");
const Card = require("../models/card");

const {
  encryptString,
  decryptPassword,
  decryptCard,
  decryptString,
} = require("../config/encryptionAlgorithm");

const fetchRecentEncryptedCredentials = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  try {
    const passwordResults = await Password.find(
      { createdBy: loggedUser._id },
      "createdBy appId title username email updatedAt"
    )
      .sort({ updatedAt: -1 })
      .limit(5);

    const cardResults = await Card.find(
      { createdBy: loggedUser._id },
      "createdBy appId issuerName cardNumber updatedAt"
    )
      .sort({ updatedAt: -1 })
      .limit(5);

    // Decrypt Card and Password
    for (let i = 0; i < cardResults.length; ++i) {
      var currCard = cardResults[i];
      cardResults[i] = decryptCard(
        currCard,
        currCard.createdBy,
        currCard.appId
      );
    }

    for (let i = 0; i < passwordResults.length; ++i) {
      var currPass = passwordResults[i];
      passwordResults[i] = decryptPassword(
        currPass,
        currPass.createdBy,
        currPass.appId
      );
    }

    // Add a new field "type" to distinguish between passwords and cards
    const userRecentPasswords = passwordResults.map((password) => ({
      ...password.toObject(),
      type: "Password",
      dataTitle: password.title,
      dataUserDetails: password.username
        ? password.username.slice(0, password.username.length / 2) +
          "*".repeat(password.username.length / 2)
        : password.email.slice(0, password.email.length / 2) +
          "*".repeat(password.email.length / 2),
    }));

    const userRecentCards = cardResults.map((card) => ({
      ...card.toObject(),
      type: "Card",
      dataTitle: card.issuerName,
      dataUserDetails:
        "*".repeat(card.cardNumber.length / 2) +
        card.cardNumber.slice(card.cardNumber.length / 2),
    }));

    // Combine both arrays
    const combinedResults = [...userRecentPasswords, ...userRecentCards];

    // Sort the combined array by the updatedAt field in descending order
    const sortedResults = combinedResults
      .sort((a, b) => b.updatedAt - a.updatedAt)
      .slice(0, 5);

    // Selecting required fields only
    const selectedFields = sortedResults.map(
      ({ appId, dataUserDetails, dataTitle, type, updatedAt }) => ({
        appId,
        dataUserDetails,
        dataTitle,
        type,
        updatedAt,
      })
    );

    return res.status(201).json({
      success: true,
      data: selectedFields,
    });
  } catch (err) {
    return res.status(400).json({
      success: false,
      error: err,
    });
  }
});

module.exports = { fetchRecentEncryptedCredentials };
