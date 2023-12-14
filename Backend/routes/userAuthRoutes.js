const express = require("express");
const {
  loginUser,
  registerUser,
  userEmailExist,
} = require("../controller/userAuthController");

const router = express.Router();

router.post("/register", registerUser);

router.post("/login", loginUser);

router.post("/emailExist", userEmailExist);

module.exports = router;
