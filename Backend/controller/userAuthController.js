const asyncHandler = require("express-async-handler");

const generateJWT = require("../config/generateJWT");
const { encryptyString } = require("../config/encryptionAlgorithm");
const User = require("../models/user");

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

  if (!appPin || appPin.length != 6) {
    return res.status(400).json({
      success: false,
      error: "App pin should contain 6 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findOne({ email });

  if (!userExist) {
    // Register the user

    const hashedAppPin = encryptyString(appPin);
    const hashedLoginPin = encryptyString(loginPin);

    const newUser = await User.create({
      email,
      firstName,
      lastName,
      loginPin: hashedLoginPin,
      appPin: hashedAppPin,
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
  } else {
    return res.status(400).json({
      success: false,
      error: "User already registered!!",
    });
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

  if (!appPin || appPin.length != 6) {
    return res.status(400).json({
      success: false,
      error: "App pin should contain 6 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findOne({ email });

  if (userExist) {
    // Login and Validate the user pins

    // Comparing the hashed login and appPin
    const hashedAppPin = encryptyString(appPin);
    const hashedLoginPin = encryptyString(loginPin);

    if (
      userExist.loginPin === hashedLoginPin &&
      userExist.appPin === hashedAppPin
    ) {
      return res.status(201).json({
        success: true,
        _id: userExist._id,
        email: userExist.email,
        firstName: userExist.firstName,
        lastName: userExist.lastName,
        loginPin: userExist.loginPin,
        appPin: userExist.appPin,
        token: generateJWT(userExist._id),
      });
    } else {
      return res.status(400).json({
        success: false,
        error: "Invalid Credentials!!",
      });
    }
  } else {
    return res.status(400).json({
      success: false,
      error: "No user found!!",
    });
  }
});

const userEmailExist = asyncHandler(async (req, res) => {
  const { email } = req.body;

  // Handling Errors
  if (!email) {
    return res.status(400).json({
      success: false,
      error: "Email id missing!!",
    });
  }

  try {
    // Check whether user exist or not
    const userExist = await User.findOne({ email });

    return res.status(201).json({
      success: true,
      userExist: userExist !== null && userExist !== undefined,
      email: email,
    });
  } catch (error) {
    return res.status(400).json({
      success: false,
      error: "An internal Error occurred!! ",
    });
  }
});

module.exports = { registerUser, loginUser, userEmailExist };
