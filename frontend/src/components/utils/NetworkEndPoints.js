const BASE_URL = "http://localhost:5000";

const EndPoints = {
  // Authentication
  emailExist: "api/auth/emailExist",
  login: "api/auth/login",
  registerUser: "api/auth/register",
  updateUser: "api/auth/update",
  verifyAppPin: "api/auth/verifyAppPin",

  // Cards
  securePaginatedAllCard: "api/credentials/securePaginatedAllCards?page=",
  fetchCardDetails: "api/credentials/fetchCardDetails",
  addUpdateCard: "api/credentials/addUpdateCard",

  // Passwords
  securePaginatedAllPassword:
    "api/credentials/securePaginatedAllPasswords?page=",
  fetchPasswordDetails: "api/credentials/fetchPasswordDetails",
  addUpdatePassword: "api/credentials/addUpdatePassword",

  // Credentials
  fetchCredentialCount: "api/credentials/fetchCredentialCount",
  fetchMonthlyCredentialsData: "api/credentials/fetchMonthlyCredentialsData",
  fetchRecentEncryptedCredentials:
    "api/credentials/fetchRecentEncryptedCredentials",
};

module.exports = { BASE_URL, EndPoints };
