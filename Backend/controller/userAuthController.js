const asyncHandler = require("express-async-handler");

const generateJWT = require("../config/generateJWT");
const { hashString } = require("../config/encryptionAlgorithm");
const User = require("../models/user");

const registerUser = asyncHandler(async (req, res) => {
  const { email, firstName, lastName, loginPin, appPin } = req.body;

  if (!email) {
    return res.status(201).json({
      success: false,
      error: "Email id missing!!",
    });
  }

  if (!firstName || firstName.length < 4) {
    return res.status(201).json({
      success: false,
      error: "First Name should contain atleast 4 characters!!",
    });
  }

  if (!loginPin || loginPin.length != 4) {
    return res.status(201).json({
      success: false,
      error: "Login pin should contain 4 digits",
    });
  }

  if (!appPin || appPin.length != 6) {
    return res.status(201).json({
      success: false,
      error: "App pin should contain 6 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findOne({ email });

  if (!userExist) {
    // Register the user

    const hashedAppPin = hashString(appPin);
    const hashedLoginPin = hashString(loginPin);

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
      return res.status(201).json({
        success: false,
        error: "An internal error occurred!!",
      });
    }
  } else {
    return res.status(201).json({
      success: false,
      error: "User already registered!!",
    });
  }
});

const loginUser = asyncHandler(async (req, res) => {
  const { email, loginPin, appPin } = req.body;

  // Handling Errors
  if (!email) {
    return res.status(201).json({
      success: false,
      error: "Email id missing!!",
    });
  }

  if (!loginPin || loginPin.length != 4) {
    return res.status(201).json({
      success: false,
      error: "Login pin should contain 4 digits",
    });
  }

  if (!appPin || appPin.length != 6) {
    return res.status(201).json({
      success: false,
      error: "App pin should contain 6 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findOne({ email });

  if (userExist) {
    // Login and Validate the user pins

    // Comparing the hashed login and appPin
    const hashedAppPin = hashString(appPin);
    const hashedLoginPin = hashString(loginPin);

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
      return res.status(201).json({
        success: false,
        error: "Invalid Credentials!!",
      });
    }
  } else {
    return res.status(201).json({
      success: false,
      error: "No user found!!",
    });
  }
});

const updateUser = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const { email, firstName, lastName, loginPin, appPin } = req.body;

  if (!email) {
    return res.status(201).json({
      success: false,
      error: "Email id missing!!",
    });
  }

  if (!firstName || firstName.trim().length < 4) {
    return res.status(201).json({
      success: false,
      error: "First Name should contain atleast 4 characters!!",
    });
  }

  if (!loginPin || loginPin.trim().length != 4) {
    return res.status(201).json({
      success: false,
      error: "Login pin should contain 4 digits",
    });
  }

  if (!appPin || appPin.trim().length != 6) {
    return res.status(201).json({
      success: false,
      error: "App pin should contain 6 digits",
    });
  }

  // Check whether user exist or not
  const userExist = await User.findOne({ email });

  if (userExist) {
    // Register the user

    const hashedAppPin = hashString(appPin);
    const hashedLoginPin = hashString(loginPin);

    const updateUser = await User.findByIdAndUpdate(
      loggedUser._id,
      {
        email,
        firstName,
        lastName,
        loginPin: hashedLoginPin,
        appPin: hashedAppPin,
      },
      {
        new: true,
      }
    ).select("-__v -createdAt -updatedAt");

    if (updateUser) {
      // New User created successfully

      return res.status(201).json({
        success: true,
        _id: updateUser._id,
        email: updateUser.email,
        firstName: updateUser.firstName,
        lastName: updateUser.lastName,
        loginPin: updateUser.loginPin,
        appPin: updateUser.appPin,
        token: generateJWT(updateUser._id),
      });
    } else {
      return res.status(201).json({
        success: false,
        error: "An internal error occurred!!",
      });
    }
  } else {
    return res.status(201).json({
      success: false,
      error: "User doesn't exist!!",
    });
  }
});

const userEmailExist = asyncHandler(async (req, res) => {
  const { email } = req.body;

  // Handling Errors
  if (!email) {
    return res.status(201).json({
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
    return res.status(201).json({
      success: false,
      error: "An internal Error occurred!! ",
    });
  }
});

const verifyAppPin = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const { appPin } = req.body;

  if (!appPin) {
    return res.status(201).json({
      success: false,
      error: "Invalid Login Pin",
    });
  }
  const hashedAppPin = hashString(appPin);

  if (loggedUser.appPin == hashedAppPin) {
    return res.status(201).json({
      success: true,
    });
  } else {
    return res.status(201).json({
      success: false,
      error: "Invalid Login Pin",
    });
  }
});

module.exports = {
  registerUser,
  loginUser,
  updateUser,
  userEmailExist,
  verifyAppPin,
};
