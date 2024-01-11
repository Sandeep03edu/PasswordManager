const express = require("express");
const {
  loginUser,
  registerUser,
  userEmailExist,
  updateUser,
  verifyAppPin,
} = require("../controller/userAuthController");

const { protectUser } = require("../middleware/protectUser");

const router = express.Router();

router.post("/register", registerUser);

router.post("/login", loginUser);

router.post("/update", protectUser, updateUser);

router.post("/emailExist", userEmailExist);

router.post("/verifyAppPin", protectUser, verifyAppPin);

module.exports = router;
