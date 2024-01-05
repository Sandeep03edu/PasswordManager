import React, { useState } from "react";
import DisplayUpperCreditCard from "../utils/DisplayUpperCreditCard";

const HeaderWithProfile = () => {
  const [showDropdown, setShowDropdown] = useState(false);

  const handleDropdownToggle = () => {
    setShowDropdown(!showDropdown);
  };

  const handleEditProfile = () => {
    // Implement edit profile functionality
  };

  const handleLogout = () => {
    // Implement logout functionality
  };

  return (
    <div style={{ width:"100%"}}>
      <nav
        className="navbar navbar-expand-lg navbar-dark bg-dark px-3 py-2"
        style={{
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        {/* Brand/logo (You may replace this with your logo or site name) */}
        <a className="navbar-brand" href="/">
          Your Site
        </a>

        <div className="ml-auto">
          <div className="dropdown">
            <img
              src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/SBI-logo.svg/2048px-SBI-logo.svg.png"
              alt="Profile"
              className="profile-picture rounded-circle border border-light"
              id="dropdownMenuButton"
              height={"40px"}
              width={"auto"}
              onClick={handleDropdownToggle}
              style={{ cursor: "pointer" }}
            />
            {showDropdown && (
              <div
                className="dropdown-menu dropdown-menu-right show"
                aria-labelledby="dropdownMenuButton"
                style={{
                  left: "calc(-110px)",
                  top: "calc(50px)",
                  zIndex: "1000",
                }}
              >
                <button
                  className="dropdown-item"
                  onClick={() => {
                    handleEditProfile();
                    setShowDropdown(false); // Close dropdown
                  }}
                >
                  Edit Profile
                </button>
                <button
                  className="dropdown-item"
                  onClick={() => {
                    handleLogout();
                    setShowDropdown(false); // Close dropdown
                  }}
                >
                  Log out
                </button>
              </div>
            )}
          </div>
        </div>
      </nav>

      <DisplayUpperCreditCard
        cards={[
          {
            _id: "6585837adf82b298d94ed7a8",
            appId: "1702727391991",
            createdBy: "657b58c0f476ce0f6934fc30",
            issuerName: "baroda bank",
            cardHolderName: "sandeep mishra",
            cardType: "Paypal",
            cardNumber: "123412345678",
            cvv: "123",
            pin: "",
            issueDate: "10/23",
            expiryDate: "12/28",
            isSynced: 1,
            creationTime: 1702727391991,
            updatedAt: "2023-12-22T12:39:22.380Z",
          },
          {
            _id: "6585837adf82b298d94ed7a8",
            appId: "1702727391991",
            createdBy: "657b58c0f476ce0f6934fc30",
            issuerName: "baroda bank",
            cardHolderName: "sandeep mishra",
            cardType: "Paypal",
            cardNumber: "123412345678",
            cvv: "123",
            pin: "",
            issueDate: "10/23",
            expiryDate: "12/28",
            isSynced: 1,
            creationTime: 1702727391991,
            updatedAt: "2023-12-22T12:39:22.380Z",
          },
          {
            _id: "6585837adf82b298d94ed7a8",
            appId: "1702727391991",
            createdBy: "657b58c0f476ce0f6934fc30",
            issuerName: "baroda bank",
            cardHolderName: "sandeep mishra",
            cardType: "Paypal",
            cardNumber: "123412345678",
            cvv: "123",
            pin: "",
            issueDate: "10/23",
            expiryDate: "12/28",
            isSynced: 1,
            creationTime: 1702727391991,
            updatedAt: "2023-12-22T12:39:22.380Z",
          },
          {
            _id: "6585837adf82b298d94ed7a8",
            appId: "1702727391991",
            createdBy: "657b58c0f476ce0f6934fc30",
            issuerName: "baroda bank",
            cardHolderName: "sandeep mishra",
            cardType: "Paypal",
            cardNumber: "123412345678",
            cvv: "123",
            pin: "",
            issueDate: "10/23",
            expiryDate: "12/28",
            isSynced: 1,
            creationTime: 1702727391991,
            updatedAt: "2023-12-22T12:39:22.380Z",
          },
          {
            _id: "6585837adf82b298d94ed7a8",
            appId: "1702727391991",
            createdBy: "657b58c0f476ce0f6934fc30",
            issuerName: "baroda bank",
            cardHolderName: "sandeep mishra",
            cardType: "Paypal",
            cardNumber: "123412345678",
            cvv: "123",
            pin: "",
            issueDate: "10/23",
            expiryDate: "12/28",
            isSynced: 1,
            creationTime: 1702727391991,
            updatedAt: "2023-12-22T12:39:22.380Z",
          },
          {
            _id: "658aa490e74b22d48d013a88",
            appId: "1703584843",
            createdBy: "657b58c0f476ce0f6934fc30",
            issuerName: "airtel bank",
            cardHolderName: "sandeep mishra",
            cardType: "VISA",
            cardNumber: "123456781234",
            cvv: "456",
            pin: "1234",
            issueDate: "07/23",
            expiryDate: "07/24",
            isSynced: 1,
            creationTime: 1703584843,
            updatedAt: "2023-12-26T10:01:52.505Z",
          },
        ]}
      />
    </div>
  );
};

export default HeaderWithProfile;
