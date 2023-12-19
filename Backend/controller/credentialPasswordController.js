const asyncHandler = require("express-async-handler");

const generateJWT = require("../config/generateJWT");

const {
  encryptString,
  decryptPassword,
} = require("../config/encryptionAlgorithm");
const Password = require("../models/password");

const savePassword = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const createdBy = req.user._id;

  const {
    appId,
    title,
    url,
    username,
    email,
    password,
    pin,
    tags,
    creationTime,
  } = req.body;

  const validatePassword = passwordValidator(
    appId,
    createdBy,
    title,
    url,
    username,
    email,
    password,
    pin,
    tags,
    creationTime
  );

  if (validatePassword.length !== 0) {
    return res.status(400).json({
      success: false,
      error: validatePassword,
    });
  }

  const passwordExist = await Password.findOne({
    createdBy: createdBy,
    appId: appId,
  }).select("-__v -createdAt -updatedAt");

  if (!passwordExist) {
    const passwordAdd = await Password.create({
      appId: appId,
      createdBy: createdBy,
      title: title,
      url: url,
      username: encryptString(username, createdBy, appId),
      email: encryptString(email, createdBy, appId),
      password: encryptString(password, createdBy, appId),
      pin: encryptString(pin, createdBy, appId),
      isSynced: true,
      tags: tags,
      creationTime,
    });

    if (passwordAdd) {
      return res.status(200).json({
        success: true,
        passwords: [
          decryptPassword(
            passwordAdd,
            passwordAdd.createdBy,
            passwordAdd.appId
          ),
        ],
      });
    } else {
      return res.status(400).json({
        success: false,
        error: "An internal error occurred!!",
      });
    }
  } else {
    return res.status(200).json({
      success: true,
      passwords: [
        decryptPassword(
          passwordExist,
          passwordExist.createdBy,
          passwordExist.appId
        ),
      ],
    });
  }
});

const getAllPasswords = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const userPasswords = await Password.find({
    createdBy: loggedUser._id,
  }).select("-__v -createdAt -updatedAt");

  for (let i = 0; i < userPasswords.length; ++i) {
    var currPass = userPasswords[i];
    userPasswords[i] = decryptPassword(
      currPass,
      currPass.createdBy,
      currPass.appId
    );
  }

  return res.status(200).json({
    success: true,
    passwords: userPasswords,
  });
});

const getPasswordDetails = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const { appId } = req.body;
  if (!appId) {
    return res.status(400).json({
      success: false,
      error: "AppId Missing",
    });
  }

  const userPassword = await Password.find({
    createdBy: loggedUser._id,
    appId: appId,
  }).select("-__v -createdAt -updatedAt");

  for (let i = 0; i < userPassword.length; ++i) {
    var currPass = userPassword[i];
    userPassword[i] = decryptPassword(
      currPass,
      currPass.createdBy,
      currPass.appId
    );
  }

  if (userPassword) {
    return res.status(200).json({
      success: true,
      passwords: userPassword,
    });
  } else {
    res.status(400).json({
      success: false,
      error: "Password does not exist!!",
    });
  }
});

const passwordValidator = (
  appId,
  createdBy,
  title,
  url,
  username,
  email,
  password,
  pin,
  tags,
  creationTime
) => {
  if (!appId.trim()) {
    return "AppId Missing";
  }

  if (!createdBy.toString().trim()) {
    return "Created By Missing";
  }

  if (!title.trim()) {
    return "Title can't be empty!!";
  }

  if (!url.trim()) {
    return "Url can't be empty!!";
  }

  if (!username.trim() && !email.trim()) {
    return "Username or Email id is compulsory!!";
  }

  if (!pin.trim() & !password.trim()) {
    return "Pin or Password is compulsory!!";
  }

  if (!tags || tags.length === 0) {
    return "Please select at least 1 Tag";
  }

  return "";
};

module.exports = { savePassword, getAllPasswords, getPasswordDetails };
