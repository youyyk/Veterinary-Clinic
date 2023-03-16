function payloadValid(fieldId, errorMessage) {
    this.fieldId = fieldId;
    this.errorMessage = errorMessage;
}
const regexOnlyCharacter = /^[A-Za-zก-๙ ]+$/;
const regexAddress = /^[0-9A-Za-zก-๙ ,]+$/;
const regexOnlyCharacterWithNumber = /^[A-Za-z0-9ก-๙ ]+$/;
const regexPhoneNumber = /^0\d{9}$/;
const regexDate = /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/
const titleChoice = ["Mr.", "Mrs.", "Ms.", "Miss"];
const errMessageCharacter = "Input Only character";
/*
* Use on Files
* - infoAccount for form in editInfoAccountPopUp
* - registerAccount
* */
function serviceValidEditAccount(formEditAccount, option){
    const payload = [];
    if (formEditAccount != null || formEditAccount != undefined){
        if (option === "title" || option === "all") {
            validIncludeList(formEditAccount.title.value, titleChoice, payload, "title", "Choose your title")
        }
        if (option === "firstName" || option === "all") {
            validRegexFormat(formEditAccount.firstName.value, regexOnlyCharacter, payload, "firstName", errMessageCharacter);
        }
        if (option === "lastName" || option === "all") {
            validRegexFormat(formEditAccount.lastName.value, regexOnlyCharacter, payload, "lastName", errMessageCharacter);
        }
        if (option === "address" || option === "all") {
            validRegexFormat(formEditAccount.address.value, regexAddress, payload, "address", errMessageCharacter);
        }
        if (option === "phone" || option === "all") {
            validRegexFormat(formEditAccount.phone.value, regexPhoneNumber, payload, "phone", "Input format 0xxxxxxxxx");
        }
    }
    return payload;
}

/*
* Use on Files
* - warehouse for form in createWarehousePopUp, editWarehouseMedicinePopUp
* */
function serviceValidNewMedicine(formNewMedicine, option){
    const payload = [];
    const fields = ["name", "price", "dose", "unit", "description"];
    if (formNewMedicine != null || formNewMedicine != undefined){
        if (option === fields[0] || option === "all") { // name
            validRegexFormat(formNewMedicine.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // price
            validNumberDouble(formNewMedicine.elements[fields[1]].value, payload, fields[1], errMessageCharacter);
        }
        if (option === fields[2] || option === "all") { // dose
            validRegexFormat(formNewMedicine.elements[fields[2]].value, regexOnlyCharacterWithNumber, payload, fields[2], errMessageCharacter);
        }
        if (option === fields[3] || option === "all") { // unit
            validRegexFormat(formNewMedicine.elements[fields[3]].value, regexOnlyCharacter, payload, fields[3], errMessageCharacter);
        }
        // if (option === fields[4] || option === "all") { // description
        //     validRegexFormat(formNewMedicine.description.value, regexOnlyCharacterWithNumber, payload, fields[4], errMessageCharacter);
        // }
    }
    return payload;
}

/*
* Use on Files
* - warehouse for form in createWarehousePopUp
* */
function serviceValidNewWarehouseMedicine(formNewWarehouseMedicine, option){
    const payload = [];
    const fields = ["medicine", "quantityIn", "paidTotal", "expiredDate"];
    if (formNewWarehouseMedicine != null || formNewWarehouseMedicine != undefined){
        if (option === fields[0] || option === "all") { // medicine
            validRegexFormat(formNewWarehouseMedicine.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // quantityIn
            validNumberInt(formNewWarehouseMedicine.elements[fields[1]].value, payload, fields[1], "Input quantityIn");
        }
        if (option === fields[2] || option === "all") { // paidTotal
            validNumberInt(formNewWarehouseMedicine.elements[fields[2]].value, payload, fields[2], "Input quantityIn");
        }
        if (option === fields[3] || option === "all") { // expiredDate
            validRegexFormat(formNewWarehouseMedicine.elements[fields[3]].value, regexDate, payload, fields[3], "Choose Date");
        }
    }
    return payload;
}

/*
* Use on Files
* - warehouse for form in createWarehousePopUp
* */
function serviceValidNewTool(formNewTool, option){
    const payload = [];
    const fields = ["name", "price", "description"];
    if (formNewTool != null || formNewTool != undefined){
        if (option === fields[0] || option === "all") { // name
            validRegexFormat(formNewTool.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // price
            validNumberInt(formNewTool.elements[fields[1]].value, payload, fields[1], "Input quantityIn");
        }
        // if (option === fields[2] || option === "all") { // description
        //     validRegexFormat(formNewTool.elements[fields[2]].value, regexOnlyCharacterWithNumber, payload, fields[2], errMessageCharacter);
        // }
    }
    return payload;
}

/*
* Use on Files
* - warehouse for form in createWarehousePopUp
* */
function serviceValidNewWarehouseTool(formNewWarehouseTool, option){
    const payload = [];
    const fields = ["tool", "quantityIn", "paidTotal", "expiredDate"];
    if (formNewWarehouseTool != null || formNewWarehouseTool != undefined){
        if (option === fields[0] || option === "all") { // tool
            validRegexFormat(formNewWarehouseTool.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // quantityIn
            validNumberInt(formNewWarehouseTool.elements[fields[1]].value, payload, fields[1], "Input quantityIn");
        }
        if (option === fields[2] || option === "all") { // paidTotal
            validNumberInt(formNewWarehouseTool.elements[fields[2]].value, payload, fields[2], "Input quantityIn");
        }
        if (option === fields[3] || option === "all") { // expiredDate
            validRegexFormat(formNewWarehouseTool.elements[fields[3]].value, regexDate, payload, fields[3], "Choose Date");
        }
    }
    return payload;
}

/*
* Use on Files
* - warehouse for form in editWarehouseOrderPopUp
* */
function serviceValidEditWarehouseOrder(formEditWarehouseOrder, option){
    const payload = [];
    const fields = ["medicine", "tool", "quantityIn", "quantityLeft", "paidTotal", "stockInDate", "expiredDate"];
    if (formEditWarehouseOrder != null || formEditWarehouseOrder != undefined){
        if ((option === fields[0] || option === "all") && formEditWarehouseOrder.elements[fields[0]] != null) { // medicine
            validRegexFormat(formEditWarehouseOrder.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if ((option === fields[1] || option === "all") && formEditWarehouseOrder.elements[fields[1]] != null) { // tool
            validRegexFormat(formEditWarehouseOrder.elements[fields[1]].value, regexOnlyCharacterWithNumber, payload, fields[1], errMessageCharacter)
        }
        if (option === fields[2] || option === "all") { // quantityIn
            validNumberInt(formEditWarehouseOrder.elements[fields[2]].value, payload, fields[2], "Input quantityIn");
        }
        if (option === fields[3] || option === "all") { // quantityLeft
            validNumberInt(formEditWarehouseOrder.elements[fields[3]].value, payload, fields[3], "Input quantityLeft");
        }
        if (option === fields[4] || option === "all") { // paidTotal
            validNumberDouble(formEditWarehouseOrder.elements[fields[4]].value, payload, fields[4], "Choose Date");
        }
        if (option === fields[5] || option === "all") { // stockInDate
            validRegexFormat(formEditWarehouseOrder.elements[fields[5]].value, regexDate, payload, fields[5], "Choose Date");
        }
        if (option === fields[6] || option === "all") { // expiredDate
            validRegexFormat(formEditWarehouseOrder.elements[fields[6]].value, regexDate, payload, fields[6], "Choose Date");
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
        if (fieldName === "description" && value === ""){
            return payload.push(new payloadValid(fieldName, "none"))
        }
        else if (!regex.test(value)){
            return payload.push(new payloadValid(fieldName, errorMessage))
        }
        return payload.push(new payloadValid(fieldName, "valid"))
    }
}

function validNumberInt(value, payload, fieldName, errorMessage){
    if (value != null && value != undefined){
        try {
            if (parseInt(value) && parseInt(value) > 0){
                return payload.push(new payloadValid(fieldName, "valid"))
            }
        } catch (e) {
            return payload.push(new payloadValid(fieldName, errorMessage))
        }
        return payload.push(new payloadValid(fieldName, errorMessage))
    }
}

function validNumberDouble(value, payload, fieldName, errorMessage){
    if (value != null && value != undefined){
        try {
            if (parseFloat(value) && parseFloat(value) > 0){
                return payload.push(new payloadValid(fieldName, "valid"))
            }
        } catch (e) {
            return payload.push(new payloadValid(fieldName, errorMessage))
        }
        return payload.push(new payloadValid(fieldName, errorMessage))
    }
}

function addClassValidAndInValid(element, errorMessage){
    if (element != null && element != undefined && errorMessage != null && errorMessage != undefined) {
        if (errorMessage === "none") {
            element.classList.remove("is-valid");
            element.classList.remove("is-invalid");
        } else {
            element.classList.remove(errorMessage === "valid" ? "is-invalid" : "is-valid")
            element.classList.add(errorMessage === "valid" ? "is-valid" : "is-invalid")
        }
    }
}
