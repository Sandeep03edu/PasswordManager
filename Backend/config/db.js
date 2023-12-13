const mongoose = require("mongoose");

const connectDb = async () => {
  console.log(`Started connecting!! ${process.env.MONGO_URI}`);
  try {
    const conn = await mongoose.connect(process.env.MONGO_URI, {});
    console.log(`Connected to ${conn.connection.host}`);
  } catch (e) {
    console.log(`MongoDb Connect Error ${e.message}`);
    process.exit();
  }
};

module.exports = connectDb;
