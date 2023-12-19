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
      type: Array,
      default: [],
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

const Password = mongoose.model("Password", passwordSchema);

module.exports = Password;
