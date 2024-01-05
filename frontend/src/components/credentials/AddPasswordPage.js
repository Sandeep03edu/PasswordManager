import React, { useState } from "react";

const AddPasswordPage = () => {
  const [selectedTags, setSelectedTags] = useState([]);
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

  const renderTags = () => {
    return tags.map((tag, index) => (
      <button
        key={index}
        className={`btn me-2 mb-2 ${
          selectedTags.includes(tag) ? "btn-info" : "btn-secondary"
        }`}
        style={{ borderRadius: "5px" }}
        onClick={(e) => handleTagClick(e, tag)}
      >
        {tag}
      </button>
    ));
  };

  return (
    <div
      className="container-fluid p-3"
      style={{
        background: "linear-gradient(135deg, #3949ab, #1e88e5)",
          minHeight: "100vh",
          display: "flex",
          flexDirection: "column",
        justifyContent:"center"
      }}
    >
      <div
        className=""
        style={{
          height: "100%",
          width: "100%",
          
        }}
      >
        <div>
          <div className="card shadow" style={{ height: "100%" }}>
            <div className="card-body p-4" style={{ height: "100%" }}>
              <h2 className="text-center mb-4">Add Password</h2>
              <form>
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
                        />
                        <label htmlFor="email">Email Id</label>
                      </div>
                    </div>
                  </div>
                  <div className="col-md-6">
                    <div className="mb-4">
                      <h4 className="fw-bold mb-3">Security Keys</h4>
                      <div className="form-floating mb-3">
                        <input
                          type="password"
                          className="form-control"
                          id="password"
                          placeholder="Enter password"
                          required
                        />
                        <label htmlFor="password">Password</label>
                      </div>
                      <div className="form-floating mb-3">
                        <input
                          type="password"
                          className="form-control"
                          id="pin"
                          placeholder="Enter pin"
                          required
                        />
                        <label htmlFor="pin">Pin</label>
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
                >
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddPasswordPage;
