const asyncHandler = require("express-async-handler");
const User = require("../models/UserModel");
const generateJWT = require("../config/generateJWT");

const registerUser = asyncHandler(async (req, res) => {
  const { email, firstName, lastName, loginPin, appPin } = req.body;

  if (!email) {
    return res.status(400).json({
      success: false,
      error: "Email id missing!!",
    });
  }

  if (!firstName || firstName.length < 4) {
    return res.status(400).json({
      success: false,
      error: "First Name should contain atleast 4 characters!!",
    });
  }

  if (!loginPin || loginPin.length != 4) {
    return res.status(400).json({
      success: false,
      error: "Login pin should contain 4 digits",
    });
  }

  if (!appPin || appPin.length != 4) {
    return res.status(400).json({
      success: false,
      error: "App pin should contain 4 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findone({ email });

  if (!userExist) {
    // Register the user

    const newUser = await User.create({
      email,
      firstName,
      lastName,
      loginPin,
      appPin,
    });

    if (newUser) {
      // New User created successfully

      return res.status(201).json({
        success: true,
        _id: newUser._id,
        email: newUser.email,
        firstName: newUser.firstName,
        lastName: newUser.lastName,
        loginPin: newUser.loginPin,
        appPin: newUser.appPin,
        token: generateJWT(newUser._id),
      });
    } else {
      return res.status(400).json({
        success: false,
        error: "An internal error occurred!!",
      });
    }
  }
});

const loginUser = asyncHandler(async (req, res) => {
  const { email, loginPin, appPin } = req.body;

  // Handling Errors
  if (!email) {
    return res.status(400).json({
      success: false,
      error: "Email id missing!!",
    });
  }

  if (!loginPin || loginPin.length != 4) {
    return res.status(400).json({
      success: false,
      error: "Login pin should contain 4 digits",
    });
  }

  if (!appPin || appPin.length != 4) {
    return res.status(400).json({
      success: false,
      error: "App pin should contain 4 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findone({ email });

  if (userExist) {
    // Login and Validate the user pins
    // TODO : Compare the hashed login and appPin
  }
});
