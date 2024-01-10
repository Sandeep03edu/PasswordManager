import React from "react";
import { cards } from "../../utils/DummyData";
import DisplayUpperCreditCard from "../../utils/DisplayUpperCreditCard";

const DisplayCards = () => {
  const styles = {
    title: {
      fontFamily: "'Roboto', sans-serif", // Change the font family as needed
      fontSize: "1.5rem",
    },
  };
  return (
    <div className="p-0 m-0">
      <nav className="navbar navbar-expand-lg navbar-light bg-light pt-1 pb-1">
        <div className="container-fluid">
          <p className="navbar-brand m-0 p-0">
            <i className="fa-solid fa-credit-card p-0 me-2"></i>
            Your Cards
          </p>
          <div
            className="navbar-toggler m-0 p-0"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#addCredentailDiv"
            aria-controls="addCredentailDiv"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <button className="btn btn-primary py-1 px-2">
              <i className="fa-solid fa-circle-plus"></i>
            </button>
          </div>
          <div
            className="collapse navbar-collapse justify-content-end "
            id="addCredentailDiv"
          >
            <ul className="navbar-nav">
              <li className="nav-item me-1">
                <button className="btn btn-primary m-2">
                  <i className="fa-solid fa-circle-plus me-2"></i>
                  Card
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div
        className="row my-2 mx-0 p-0"
        style={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "start",
        }}
      >
        {cards.map((card) => {
          return (
            <div className="col m-2" style={{ maxWidth: "250px" }}>
              <DisplayUpperCreditCard card={card} />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default DisplayCards;
