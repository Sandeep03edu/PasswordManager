var blake2 = require("blake2");
var crypto = require("crypto");
var assert = require("assert");

const hashString = (input) => {
  const key = process.env.BLAKE2_KEY;
  var h = blake2.createKeyedHash("blake2b", Buffer.from(key));
  h.update(Buffer.from(input));

  return h.digest("hex");
};

const encryptString = (text, userId, appId) => {
  var salt = process.env.AES_SALT;
  var merger = salt + appId + userId;

  while (merger.length < 33) {
    merger += appId + userId;
  }

  const encryption_key = merger.substring(0, 32);
  const initialization_vector = merger.substring(0, 16);

  const cipher = crypto.createCipheriv(
    "aes-256-cbc",
    Buffer.from(encryption_key),
    Buffer.from(initialization_vector)
  );
  var crypted = cipher.update(text, "utf8", "hex");
  crypted += cipher.final("hex");
  return crypted;
};

const decryptString = (text, userId, appId) => {
  var salt = process.env.AES_SALT;
  var merger = salt + appId + userId;

  while (merger.length < 33) {
    merger += appId + userId;
  }
  const encryption_key = merger.substring(0, 32);
  const initialization_vector = merger.substring(0, 16);

  const decipher = crypto.createDecipheriv(
    "aes-256-cbc",
    Buffer.from(encryption_key),
    Buffer.from(initialization_vector)
  );
  let dec = decipher.update(text, "hex", "utf8");
  dec += decipher.final("utf8");
  return dec;
};

const decryptCard = (card, userId, appId) => {
  card.cardNumber = decryptString(card.cardNumber, userId, appId);
  card.pin = decryptString(card.pin, userId, appId);
  card.cvv = decryptString(card.cvv, userId, appId);
  card.issueDate = decryptString(card.issueDate, userId, appId);
  card.expiryDate = decryptString(card.expiryDate, userId, appId);

  return card;
};

module.exports = { hashString, encryptString, decryptString, decryptCard };
