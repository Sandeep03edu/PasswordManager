const BASE_URL = "http://localhost:5000";

const EndPoints = {
  emailExist: "api/auth/emailExist",
  login: "api/auth/login",
  registerUser: "api/auth/register",
  fetchCredentialCount: "api/credentials/fetchCredentialCount",
  fetchMonthlyCredentialsData: "api/credentials/fetchMonthlyCredentialsData",
  fetchRecentEncryptedCredentials:
    "api/credentials/fetchRecentEncryptedCredentials",
};

module.exports = { BASE_URL, EndPoints };
