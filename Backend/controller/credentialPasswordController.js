const asyncHandler = require("express-async-handler");

const generateJWT = require("../config/generateJWT");

const {
  encryptString,
  decryptPassword,
} = require("../config/encryptionAlgorithm");
const Password = require("../models/password");

const addUpdatePassword = asyncHandler(async (req, res) => {
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
  }).select("-__v -createdAt ");

  if (!passwordExist) {
    var passwordAdd = await Password.create({
      appId: appId,
      createdBy: createdBy,
      title: title,
      url: url,
      username: encryptString(username, createdBy, appId),
      email: encryptString(email, createdBy, appId),
      password: encryptString(password, createdBy, appId),
      pin: encryptString(pin, createdBy, appId),
      isSynced: 1,
      tags: tags,
      creationTime,
    });

    passwordAdd = await Password.findOne({
      createdBy: createdBy,
      appId: appId,
    }).select("-__v -createdAt ");

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
    const updatePassword = await Password.findByIdAndUpdate(
      passwordExist._id,
      {
        appId: appId,
        createdBy: createdBy,
        title: title,
        url: url,
        username: encryptString(username, createdBy, appId),
        email: encryptString(email, createdBy, appId),
        password: encryptString(password, createdBy, appId),
        pin: encryptString(pin, createdBy, appId),
        isSynced: 1,
        tags: tags,
        creationTime,
      },
      {
        new: true,
      }
    ).select("-__v -createdAt ");

    return res.status(200).json({
      success: true,
      passwords: [
        decryptPassword(
          updatePassword,
          updatePassword.createdBy,
          updatePassword.appId
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
  }).select("-__v -createdAt ");

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
  }).select("-__v -createdAt ");

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

const deletePasswordById = asyncHandler(async (req, res) => {
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

  try {
    const response = await Password.deleteOne({
      appId: appId,
      createdBy: loggedUser._id,
    });
    console.log(response);
    if (response.acknowledged) {
      // Delete successful
      res.status(200).json({
        success: true,
        passwords: [{ appId }],
      });
    } else {
      res.status(200).json({
        success: false,
        passwords: [{ appId }],
        error: "An internal error occurred!!",
      });
    }
  } catch (error) {
    res.status(200).json({
      success: false,
      passwords: [{ appId }],
      error: error.message,
    });
  }
});

const securePaginatedAllPasswords = asyncHandler(async (req, res) => {
  const loggedUser = req.user;

  if (!loggedUser) {
    return res.status(401).json({
      success: false,
      error: "User not authenticated!!",
    });
  }

  const page = parseInt(req.query.page) || 1;
  const pageSize = 1;

  try {
    const totalItems = await Password.countDocuments({
      createdBy: loggedUser._id,
    });

    const totalPages = Math.ceil(totalItems / pageSize);

    if (page < 1 || page > totalPages) {
      return res.status(400).json({
        success: false,
        error: "Invalid page number",
      });
    }

    const skip = (page - 1) * pageSize;
    const passwords = await Password.find(
      { createdBy: loggedUser._id },
      "_id appId createdBy tags creationTime title url username email"
    )
      .skip(skip)
      .limit(pageSize)
      .sort({ createdAt: -1 });

    for (var i = 0; i < passwords.length; ++i) {
      var currPass = passwords[i];
      passwords[i] = decryptPassword(
        currPass,
        currPass.createdBy,
        currPass.appId
      );

      var len = passwords[i].username.length;
      passwords[i].username =
        passwords[i].username.slice(0, len / 2) + "*".repeat(len / 2);
      len = passwords[i].email.length;
      passwords[i].email =
        passwords[i].email.slice(0, len / 2) + "*".repeat(len / 2);
    }

    return res.status(201).json({
      success: true,
      passwords: passwords,
      currentPage: page,
      totalPage: totalPages,
    });
  } catch (error) {
    return res.status(400).json({
      success: false,
      error: error,
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

  if ((!username || !username.trim()) && (!email || !email.trim())) {
    return "Username or Email id is compulsory!!";
  }

  if ((!pin || !pin.trim) && (!password || !password.trim)) {
    return "Pin or Password is compulsory!!";
  }

  if (!tags || tags.length === 0) {
    return "Please select at least 1 Tag";
  }

  return "";
};

module.exports = {
  addUpdatePassword,
  getAllPasswords,
  getPasswordDetails,
  deletePasswordById,
  securePaginatedAllPasswords,
};
