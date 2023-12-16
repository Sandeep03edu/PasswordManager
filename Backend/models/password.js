const mongoose = require("mongoose");

const passwordSchema = mongoose.Schema(
  {
    appId: {
      type: String,
      required: true,
    },
    createdBy: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      lowercase: true,
    },
    url: {
      type: String,
    },
    username: {
      type: String,
    },
    email: {
      type: String,
    },
    password: {
      type: String,
    },
    pin: {
      type: String,
    },
    tags: {
      type: String,
    },
    isSynced: {
      type: Boolean,
    },
    creationTime: {
      type: Number,
    },
  },
  {
    timestamps: true,
  }
);

const password = mongoose.model("Password", passwordSchema);

module.exports = password;
