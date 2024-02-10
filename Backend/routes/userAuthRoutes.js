const express = require("express");
const {
  loginUser,
  registerUser,
  userEmailExist,
  updateUser,
  verifyAppPin,
  restartApi,
} = require("../controller/userAuthController");

const { protectUser } = require("../middleware/protectUser");

const router = express.Router();

router.post("/register", registerUser);

router.post("/login", loginUser);

router.post("/update", protectUser, updateUser);

router.post("/emailExist", userEmailExist);

router.post("/verifyAppPin", protectUser, verifyAppPin);

router.get("/restart", restartApi);

module.exports = router;
