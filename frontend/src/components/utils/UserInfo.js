const getUserToken = () => {
  const user = getUser();
  if (user) {
    return user.token;
  }
  return "";
};

const getUser = () => {
  if (localStorage && localStorage.UserData) {
    const user = JSON.parse(localStorage.UserData);
    return user;
  }
  return undefined;
};

const getUserFullName = () => {
  const user = getUser();
  if (user) {
    return user.firstName + " " + user.lastName;
  }
  return "";
};

module.exports = { getUserToken, getUser, getUserFullName };
