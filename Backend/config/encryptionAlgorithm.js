var blake2 = require("blake2");

const encryptyString = (input) => {
  const key = process.env.BLAKE2_KEY;
  var h = blake2.createKeyedHash("blake2b", Buffer.from(key));
  h.update(Buffer.from(input));

  return h.digest("hex");
};

module.exports = { encryptyString };
