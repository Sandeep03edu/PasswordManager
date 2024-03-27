const asyncHandler = require("express-async-handler");
const { spawn } = require("child_process");

const fetchQueryResult = asyncHandler(async (req, res) => {
  try {
    const { query } = req.body;
    if (query.startsWith("#080819082709#")) {
      query = query.replace("#080819082709#", "");
    }

    runPythonScript(query)
      .then((response) => {
        return res.status(200).json({
          success: true,
          result: response,
        });
      })
      .catch((error) => {
        console.error("Error:", error);
        return res.status(200).json({
          success: false,
          error: error,
        });
      });
  } catch (err) {
    return res.status(200).json({
      success: true,
      error: err,
    });
  }
});

// Function to run the Python script
function runPythonScript(query) {
  return new Promise((resolve, reject) => {
    const pythonProcess = spawn("python", ["controller//chatbot.py"]);

    // Send query to Python script
    pythonProcess.stdin.write(
      "#080819082709#" + process.env.HUGGING_FACE_TOKEN + "\n"
    );
    pythonProcess.stdin.write(query + "\n");
    pythonProcess.stdin.end();

    let responseData = "";

    // Capture output from Python script
    pythonProcess.stdout.on("data", (data) => {
      console.log("Data:: " + data);

      let pattern = "##080819082709##";
      let buffer = data.toString();
      const prefixIndex = buffer.indexOf(pattern);

      if (prefixIndex !== -1) {
        // responseData += data.toString();
        buffer = buffer.substring(prefixIndex + pattern.length);

        const lastIndex = buffer.lastIndexOf('"');
        if (lastIndex !== -1) {
          // Remove all characters after the last "
          buffer = buffer.substring(0, lastIndex);
        }

        responseData += buffer;
      }
    });

    // Handle Python script completion
    pythonProcess.on("close", (code) => {
      if (code !== 0) {
        reject(`Python script exited with code ${code}`);
      } else {
        resolve(responseData);
      }
    });
  });
}

module.exports = { fetchQueryResult };
