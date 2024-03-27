const express = require("express");
const { fetchQueryResult } = require("../controller/chatBotController");

const router = express.Router();

router.post("/query", fetchQueryResult);

module.exports = router;
