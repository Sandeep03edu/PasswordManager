const mongoose = require("mongoose");

const cardSchema = mongoose.Schema(
  {
    appId: {
      type: String,
      required: true,
    },
    createdBy: {
      type: String,
      required: true,
    },
    issuerName: {
      type: String,
      lowercase: true,
    },
    cardHolderName: {
      type: String,
      lowercase: true,
    },
    cardType: {
      type: String,
    },
    cardNumber: {
      type: String,
    },
    cvv: {
      type: String,
    },
    pin: {
      type: String,
    },
    issueDate: {
      type: String,
    },
    expiryDate: {
      type: String,
    },
    isSynced: {
      type: Boolean,
    },
    creationTime: {
      type: Number,
    },
  },
  {
    timestamps: true,
  }
);

const Card = mongoose.model("Card", cardSchema);

module.exports = Card;
