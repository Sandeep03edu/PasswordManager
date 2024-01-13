const BASE_URL = "http://localhost:5000";

const EndPoints = {
  // Authentication
  emailExist: "api/auth/emailExist",
  login: "api/auth/login",
  registerUser: "api/auth/register",
  verifyAppPin: "api/auth/verifyAppPin",

  // Cards
  securePaginatedAllCard: "api/credentials/securePaginatedAllCards?page=",
  fetchCardDetails: "api/credentials/fetchCardDetails",

  // Passwords
  securePaginatedAllPassword:
    "api/credentials/securePaginatedAllPasswords?page=",
  fetchPasswordDetails: "api/credentials/fetchPasswordDetails",

  // Credentials
  fetchCredentialCount: "api/credentials/fetchCredentialCount",
  fetchMonthlyCredentialsData: "api/credentials/fetchMonthlyCredentialsData",
  fetchRecentEncryptedCredentials:
    "api/credentials/fetchRecentEncryptedCredentials",
};

module.exports = { BASE_URL, EndPoints };
