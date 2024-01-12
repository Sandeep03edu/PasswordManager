const convertToDateTime = (millisec) => {
  const date = new Date(millisec);

  const dateString = date.toLocaleDateString(); // Format as "MM/DD/YYYY"
  const timeString = date.toLocaleTimeString(); // Format as "HH:mm:ss"

  return dateString + " " + timeString;
};

module.exports = { convertToDateTime };
