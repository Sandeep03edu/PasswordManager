const jwt = require("jsonwebtoken");

// Generate JWT token for each id
const generateJWT = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, {
    expiresIn: "30d",
  });
};

module.exports = generateJWT;
