const express = require("express");
const app = express();
const cors = require("cors");
const connectDb = require("./config/db");
const dotenv = require("dotenv");
const { encryptyString } = require("./config/encryptionAlgorithm");
const userAuthRoutes = require("./routes/userAuthRoutes.js");

// Configuring dotenv
dotenv.config();

// Connecting database
connectDb();

// Setting local host port for local host
const PORT = process.env.PORT;

// Use cors
app.use(cors());

// Accept JSON data
app.use(express.json());

// Connect to PORT
const server = app.listen(PORT, console.log(`Server Started on port ${PORT}`));

// Authentication API
app.use("/api/auth", userAuthRoutes);
