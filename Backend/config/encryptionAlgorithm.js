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
  const encryption_key = "byz9VFNtbRQM0yBODcCb1lrUtVVH3D3x"; // Must be 32 characters
  const initialization_vector = "X05IGQ5qdBnIqAWD"; // Must be 16 characters

  const cipher = crypto.createCipheriv(
    "aes-256-cbc",
    Buffer.from(encryption_key),
    Buffer.from(initialization_vector)
  );
  var crypted = cipher.update(text, "utf8", "hex");
  crypted += cipher.final("hex");
  return crypted;
};

module.exports = { hashString, encryptString };
