const getUserToken = () => {
  if (localStorage && localStorage.UserData) {
    const user = JSON.parse(localStorage.UserData);
    return user.token;
  }
  return "";
};

module.exports = { getUserToken };
