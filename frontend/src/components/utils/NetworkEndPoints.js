const BASE_URL = "http://localhost:5000";

const EndPoints = {
  // Authentication
  emailExist: "api/auth/emailExist",
  login: "api/auth/login",
  registerUser: "api/auth/register",

  // Cards
  securePaginatedAllCard:
    "api/credentials/securePaginatedAllCards?page=",

  // Passwords
  securePaginatedAllPassword:
    "api/credentials/securePaginatedAllPasswords?page=",

  // Credentials
  fetchCredentialCount: "api/credentials/fetchCredentialCount",
  fetchMonthlyCredentialsData: "api/credentials/fetchMonthlyCredentialsData",
  fetchRecentEncryptedCredentials:
    "api/credentials/fetchRecentEncryptedCredentials",
};

module.exports = { BASE_URL, EndPoints };
