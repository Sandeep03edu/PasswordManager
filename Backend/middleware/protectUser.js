const User = require("../models/user");
const jwt = require("jsonwebtoken");
const asyncHandler = require("express-async-handler");

const protectUser = asyncHandler(async (req, res, next) => {
  let token;
  if (
    req.headers.authorization &&
    req.headers.authorization.startsWith("Bearer")
  ) {
    try {
      token = req.headers.authorization.split(" ")[1];

      //decodes token id
      const decoded = jwt.verify(token, process.env.JWT_SECRET);

      req.user = await User.findById(decoded.id);

      next();
    } catch (error) {
      return res.status(401).json({
        success: false,
        error: "Not authorized, token failed",
      });
    }
  } else {
    return res.status(401).json({
      success: false,
      error: "Not authorized!!",
    });
  }
});

module.exports = { protectUser };
