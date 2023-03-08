function payloadValid(fieldId, errorMessage) {
    this.fieldId = fieldId;
    this.errorMessage = errorMessage;
}
const regexOnlyCharacter = /^[A-Za-zก-๙]+$/;
const regexPhoneNumber = /^0\d{9}$/;
const titleChoice = ["Mr.", "Mrs.", "Ms.", "Miss"];
function serviceValidEditAccount(formEditAccount, option){
    const payload = [];
    if (formEditAccount != null || formEditAccount != undefined){
        if (option === "title" || option === "all") {
            validIncludeList(formEditAccount.title.value, titleChoice, payload, "title", "Choose your title")
        }
        if (option === "firstName" || option === "all") {
            validRegexFormat(formEditAccount.firstName.value, regexOnlyCharacter, payload, "firstName", "Input Only character");
        }
        if (option === "lastName" || option === "all") {
            validRegexFormat(formEditAccount.lastName.value, regexOnlyCharacter, payload, "lastName", "Input Only character");
        }
        if (option === "address" || option === "all") {
            validRegexFormat(formEditAccount.address.value, regexOnlyCharacter, payload, "address", "Input Only character");
        }
        if (option === "phone" || option === "all") {
            validRegexFormat(formEditAccount.phone.value, regexPhoneNumber, payload, "phone", "Input format 0xxxxxxxxx");
        }
    }
    return payload;
}

function validIncludeList(value, listCheck, payload, fieldName, errorMessage){
    if (value != null && value != undefined){
        if (!listCheck.includes(value)){
            return payload.push(new payloadValid(fieldName, errorMessage))
        }
        return payload.push(new payloadValid(fieldName, "valid"))
    }
}

function validRegexFormat(value, regex, payload, fieldName, errorMessage){
    if (value != null && value != undefined){
        if (!regex.test(value)){
            return payload.push(new payloadValid(fieldName, errorMessage))
        }
        return payload.push(new payloadValid(fieldName, "valid"))
    }
}

function addClassValidAndInValid(element, errorMessage){
    if (element != null && element != undefined && errorMessage != null && errorMessage != undefined) {
        element.classList.remove(errorMessage === "valid" ? "is-invalid" : "is-valid")
        element.classList.add(errorMessage === "valid" ? "is-valid" : "is-invalid")
    }
}