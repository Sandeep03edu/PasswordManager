package com.sandeep03edu.passwordmanager.manager.credentials.domain

object CardValidator {

    fun validateCard(card: Card) : CardValidationResult{
        var res = CardValidationResult()
        if(card.issuerName.isBlank()){
            res = res.copy(issuerNameError = "Card issuer name can't be empty!!")
        }
        else{
            if(card.issuerName.length<3){
                res = res.copy(issuerNameError  = "Card issuer name invalid!!")
            }
        }

        if(card.cardHolderName.isBlank()){
            res = res.copy(cardHolderNameError = "Card holder name can't be empty!!")
        }
        else{
            if(card.cardHolderName.length<3){
                res = res.copy(cardHolderNameError = "Card holder name invalid!!")
            }
        }

        if(card.cardNumber.isBlank()){
            res = res.copy(cardNumberError = "Card number can't be empty!!")
        }
        else{
            if(card.cardNumber.length<6){
                res = res.copy(cardNumberError = "Card number should be 6 digit long")
            }
        }

        if(card.cardType==null || card.cardType?.isBlank()==true){
            res = res.copy(cardTypeError = "Card type can't be empty!!")
        }
        else{
            if(card.cardType?.length!! <3){
                res = res.copy(cardTypeError = "Invalid card type")
            }
        }

        if((card.issueDate==null || card.issueDate?.isBlank() == true)){
            if((card.expiryDate==null || card.expiryDate?.isBlank() == true)){
                res = res.copy(dateError = "Issue date or Expiry date is compulsory!!")
            }
        }

        if((card.pin.isBlank())){
            if((card.cvv.isBlank())){
                res = res.copy(securityKeyError = "Pin or Cvv is compulsory!!")
            }
        }

        return res
    }

    data class CardValidationResult(
        val issuerNameError: String? = null,
        val cardHolderNameError: String? = null,
        val cardNumberError: String? = null,

        val cardTypeError : String? = null,
        // Issue Date or Expiry Date
        val dateError : String? = null,

        // Pin or Cvv
        val securityKeyError : String? = null,
    )
}