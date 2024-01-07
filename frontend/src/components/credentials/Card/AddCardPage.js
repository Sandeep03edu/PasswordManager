import React, { useState } from "react";
import AddCreditCard from "../../utils/AddCreditCard";

const AddCardPage = () => {
  return (
    <>
      <AddCreditCard
        cardHolderName={"Sandeep Mishra"}
        cardNumber={"1234123412341234"}
        issueDate={"12/20"}
        expiryDate={"12/26"}
        issuerName={"Master Card"}
        bankName={"State Bank of India"}
        cvv={"123"}
        pin={"1234"}
      />
    </>
  );
};

export default AddCardPage;
