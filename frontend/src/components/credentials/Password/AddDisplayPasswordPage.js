import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";

import { useLocation } from "react-router-dom";

const AddDisplayPasswordPage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const passwordData = queryParams.get("passwordData");

  const password = passwordData
    ? JSON.parse(decodeURIComponent(passwordData))
    : null;
  const passwordId = password ? password._id : null;

  const [editable, setEditable] = useState(passwordId === null);

  const [buttonLabel, setButtonLabel] = useState(
    passwordId !== null ? "Edit Password" : "Add Password"
  );
  const [label, setLabel] = useState(
    passwordId !== null ? "Your Password" : "Add Password"
  );

  const [showPassword, setShowPassword] = useState(!editable);
  const [showPin, setShowPin] = useState(!editable);
  const togglePasswordVisibility = () => {
    setShowPassword((prevShowPassword) => !prevShowPassword);
  };
  const togglePinVisibility = () => {
    setShowPin((prevShowPassword) => !prevShowPassword);
  };

  const [title, setTitle] = useState(password ? password.title : "");
  const [url, setUrl] = useState(password ? password.url : "");
  const [username, setUsername] = useState(password ? password.username : "");
  const [email, setEmail] = useState(password ? password.email : "");
  const [passwordInput, setPasswordInput] = useState(
    password ? password.password : ""
  );
  const [pin, setPin] = useState(password ? password.pin : "");
  const [selectedTags, setSelectedTags] = useState(
    password ? password.tags : []
  );
  const tags = ["Personal", "Work", "Browser", "Banking"];

  const handleTagClick = (e, tag) => {
    e.preventDefault();
    if (selectedTags.includes(tag)) {
      setSelectedTags(
        selectedTags.filter((selectedTag) => selectedTag !== tag)
      );
    } else {
      setSelectedTags([...selectedTags, tag]);
    }
  };

  const handleButtonClick = () => {
    if (passwordId !== null) {
      // Display Card present
      if (buttonLabel === "Edit Password") {
        setEditable(true);
        setButtonLabel("Update Password");
        setLabel("Update Password Details");
      } else if (buttonLabel === "Update Password") {
        // Update details for card
      }
    } else {
      // Add Card Data
    }
  };

  const renderTags = () => {
    return tags.map((tag, index) => (
      <button
        key={index}
        className={`btn me-2 mb-2 ${
          selectedTags.includes(tag) ? "btn-info" : "btn-secondary"
        }`}
        style={{ borderRadius: "5px" }}
        onClick={(e) => {
          if (editable) {
            handleTagClick(e, tag);
          }
        }}
      >
        {tag}
      </button>
    ));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission logic here
  };

  return (
    <div
      className="container-fluid p-3"
      style={{
        background: "linear-gradient(135deg, #3949ab, #1e88e5)",
        minHeight: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        className=""
        style={{
          height: "100%",
          width: "90%",
        }}
      >
        <div>
          <div className="card shadow" style={{ height: "100%" }}>
            <div className="card-body p-4" style={{ height: "100%" }}>
              <h2 className="text-center mb-4">{label}</h2>
              <form onSubmit={handleSubmit}>
                <div className="row">
                  <div className="col-md-6">
                    <div className="mb-4">
                      <h4 className="fw-bold mb-3">Password Details</h4>
                      <div className="form-floating mb-3">
                        <input
                          type="text"
                          className="form-control"
                          id="title"
                          placeholder="Enter title"
                          required
                          value={title}
                          onChange={(e) => setTitle(e.target.value)}
                          readOnly={editable ? undefined : "readOnly"}
                        />
                        <label htmlFor="title">Title</label>
                      </div>
                      <div className="form-floating mb-3">
                        <input
                          type="text"
                          className="form-control"
                          id="url"
                          placeholder="Enter URL"
                          required
                          value={url}
                          onChange={(e) => setUrl(e.target.value)}
                          readOnly={editable ? undefined : "readOnly"}
                        />
                        <label htmlFor="url">URL</label>
                      </div>
                      <div className="form-floating mb-3">
                        <input
                          type="text"
                          className="form-control"
                          id="username"
                          placeholder="Enter username"
                          required
                          value={username}
                          onChange={(e) => setUsername(e.target.value)}
                          readOnly={editable ? undefined : "readOnly"}
                        />
                        <label htmlFor="username">Username</label>
                      </div>
                      <div className="form-floating mb-3">
                        <input
                          type="email"
                          className="form-control"
                          id="email"
                          placeholder="Enter email"
                          required
                          value={email}
                          onChange={(e) => setEmail(e.target.value)}
                          readOnly={editable ? undefined : "readOnly"}
                        />
                        <label htmlFor="email">Email Id</label>
                      </div>
                    </div>
                  </div>
                  <div className="col-md-6">
                    <div className="mb-4">
                      <h4 className="fw-bold mb-3">Security Keys</h4>
                      <div
                        className="form-floating mb-3"
                        style={{ position: "relative" }}
                      >
                        <input
                          type={showPassword ? "text" : "password"}
                          className="form-control"
                          id="password"
                          placeholder="Enter password"
                          required
                          value={passwordInput}
                          onChange={(e) => setPasswordInput(e.target.value)}
                          readOnly={editable ? undefined : "readOnly"}
                          style={{ paddingRight: "40px" }} // Adjust the padding to leave space for the button
                        />
                        <label htmlFor="password">Password</label>
                        {editable && (
                          <div
                            className="px-2"
                            onClick={togglePasswordVisibility}
                            style={{
                              position: "absolute",
                              right: "10px", // Adjust the right positioning as needed
                              top: "50%",
                              transform: "translateY(-50%)",
                              zIndex: "1",
                            }}
                          >
                            <FontAwesomeIcon
                              icon={showPassword ? faEye : faEyeSlash}
                            />
                          </div>
                        )}
                      </div>

                      <div
                        className="form-floating mb-3"
                        style={{ position: "relative" }}
                      >
                        <input
                          type={showPin ? "text" : "password"}
                          className="form-control"
                          id="pin"
                          placeholder="Enter pin"
                          required
                          value={pin}
                          onChange={(e) => setPin(e.target.value)}
                          readOnly={editable ? undefined : "readOnly"}
                          style={{ paddingRight: "40px" }} // Adjust the padding to leave space for the button
                        />
                        <label htmlFor="pin">Pin</label>
                        {editable && (
                          <div
                            className="px-2"
                            onClick={togglePinVisibility}
                            style={{
                              position: "absolute",
                              right: "10px", // Adjust the right positioning as needed
                              top: "50%",
                              transform: "translateY(-50%)",
                              zIndex: "1",
                            }}
                          >
                            <FontAwesomeIcon
                              icon={showPin ? faEye : faEyeSlash}
                            />
                          </div>
                        )}
                      </div>

                      <div className="mb-4">
                        <h4 className="fw-bold mb-3">Tags</h4>
                        <div className="d-flex flex-wrap">{renderTags()}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <button
                  type="submit"
                  className="btn btn-primary d-block mx-auto"
                  onMouseOver={(e) =>
                    e.target.classList.add("btn-primary-hover")
                  }
                  onMouseOut={(e) =>
                    e.target.classList.remove("btn-primary-hover")
                  }
                  onClick={(e) => {
                    e.preventDefault();
                    handleButtonClick();
                  }}
                >
                  {buttonLabel}
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddDisplayPasswordPage;
