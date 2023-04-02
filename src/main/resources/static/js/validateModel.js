function payloadValid(fieldId, errorMessage) {
    this.fieldId = fieldId;
    this.errorMessage = errorMessage;
}
const regexOnlyCharacter = /^[A-Za-zก-๙ ]+$/;
const regexOnlyCharacterWithNumberWithSlash = /^[A-Za-zก-๙ /]+$/;
const regexAddress = /^[0-9A-Za-zก-๙ ,/.-]+$/;
const regexOnlyCharacterWithNumber = /^[A-Za-z0-9ก-๙ ]+$/;
const regexOnlyNumber = /^[0-9]+$/;
const regexPhoneNumber = /^0\d{9}$/;
const regexDouble = /^[[1-9]\+0\*[0-9]\*[^.0-9]]$/;
const regexDate = /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/
const titleChoice = ["Mr.", "Mrs.", "Ms.", "Miss"];
const genderChoice = ["female", "male"];
const booleanChoice = ["true", "false"];
const periodChoice = ["morning", "afternoon"];
const errMessageCharacter = "Input Only character";
/*
* Use on Files
* - infoAccount for form in editInfoAccountPopUp
* - registerAccount
* */
function serviceValidEditAccount(formEditAccount, option) {
    const payload = [];
    const fields = ["title", "firstName", "lastName", "address", "phone"];
    if (formEditAccount != null || formEditAccount != undefined) {
        if (option === "reset") {
            realTimeValid = false;
            resetForm(formEditAccount, fields, true, true);
            return payload;
        }
        if (option === "title" || option === "all") {
            validIncludeList(formEditAccount.elements[fields[0]].value, titleChoice, payload, fields[0], "Choose your title")
        }
        if (option === "firstName" || option === "all") {
            validRegexFormat(formEditAccount.elements[fields[1]].value, regexOnlyCharacter, payload, fields[1], errMessageCharacter);
        }
        if (option === "lastName" || option === "all") {
            validRegexFormat(formEditAccount.elements[fields[2]].value, regexOnlyCharacter, payload, fields[2], errMessageCharacter);
        }
        if (option === "address" || option === "all") {
            validRegexFormat(formEditAccount.elements[fields[3]].value, regexAddress, payload, fields[3], errMessageCharacter);
        }
        if (option === "phone" || option === "all") {
            validRegexFormat(formEditAccount.elements[fields[4]].value, regexPhoneNumber, payload, fields[4], "Input format 0xxxxxxxxx");
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
        if (option === "reset") {
            realTimeValid_formNewMedicine = false;
            realTimeValid_formEditMedicineInfo = false;
            resetForm(formNewMedicine, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // name
            validRegexFormat(formNewMedicine.elements[fields[0]].value, regexOnlyCharacterWithNumberWithSlash, payload, fields[0], errMessageCharacter)
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
        if (option === "reset") {
            realTimeValid_formNewWarehouseMedicine = false;
            resetForm(formNewWarehouseMedicine, fields, true, true);
            return payload;
        }
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
            let todayOrFuture = false;
            if (regexDate.test(formNewWarehouseMedicine.elements[fields[3]].value) && new Date(formNewWarehouseMedicine.elements[fields[3]].value).setHours(0,0,0,0) >= new Date($.now()).setHours(0,0,0,0)){
                todayOrFuture = true;
            }
            validRegexFormat(formNewWarehouseMedicine.elements[fields[3]].value, todayOrFuture ? regexDate : regexPhoneNumber, payload, fields[3], errMessageCharacter);
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
        if (option === "reset") {
            realTimeValid_formNewTool = false;
            realTimeValid_formEditToolInfo = false;
            resetForm(formNewTool, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // name
            validRegexFormat(formNewTool.elements[fields[0]].value, regexOnlyCharacterWithNumberWithSlash, payload, fields[0], errMessageCharacter)
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
        if (option === "reset") {
            realTimeValid_formNewWarehouseTool = false;
            resetForm(formNewWarehouseTool, fields, true, true);
            return payload;
        }
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
            let todayOrFuture = false;
            if (regexDate.test(formNewWarehouseTool.elements[fields[3]].value) && new Date(formNewWarehouseTool.elements[fields[3]].value).setHours(0,0,0,0) >= new Date($.now()).setHours(0,0,0,0)){
                todayOrFuture = true;
            }
            validRegexFormat(formNewWarehouseTool.elements[fields[3]].value, todayOrFuture ? regexDate : regexPhoneNumber, payload, fields[3], errMessageCharacter);

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
        if (option === "reset") {
            realTimeValid_formEditOrder = false;
            resetForm(formEditWarehouseOrder, fields, true, true);
            return payload;
        }
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
        if (option === fields[6] || option === "all" || option === fields[5]) { // expiredDate
            let todayOrFuture = false;
            if (regexDate.test(formEditWarehouseOrder.elements[fields[6]].value) && new Date(formEditWarehouseOrder.elements[fields[6]].value).setHours(0,0,0,0) >= new Date(formEditWarehouseOrder.elements[fields[5]].value).setHours(0,0,0,0)){
                todayOrFuture = true;
            }
            validRegexFormat(formEditWarehouseOrder.elements[fields[6]].value, todayOrFuture ? regexDate : regexPhoneNumber, payload, fields[6], errMessageCharacter);

        }

    }
    return payload;
}

/*
* Use on Files
* - services for form in createServicePopUp
* */
function serviceValidNewService(formNewService, option){
    const payload = [];
    const fields = ["name", "price"];
    if (formNewService != null || formNewService != undefined){
        if (option === "reset") {
            realTimeValid_formNewService = false;
            realTimeValid_formEditService = false;
            resetForm(formNewService, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // name
            validRegexFormat(formNewService.elements[fields[0]].value, regexOnlyCharacterWithNumberWithSlash, payload, fields[0], errMessageCharacter);
        }
        if (option === fields[1] || option === "all") { // price
            validNumberInt(formNewService.elements[fields[1]].value, payload, fields[1], "Input quantityIn");
        }
    }
    return payload;
}

/*
* Use on Files
* - infoAccount for form in createPetPopUp
* */
function serviceValidNewPet(formNewPet, option){
    const payload = [];
    const fields = ["name", "image", "doB", "petType", "breed", "gender", "sterilization", "remark"];
    if (formNewPet != null || formNewPet != undefined){
        if (option === "reset") {
            realTimeValid_formNewPet = false;
            realTimeValid_formEditPetInfo = false;
            resetForm(formNewPet, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // name
            validRegexFormat(formNewPet.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter);
        }
        // if (option === fields[1] || option === "all") { // image
        //     validRegexFormat(formNewPet.elements[fields[1]].value, regexOnlyCharacterWithNumber, payload, fields[1], errMessageCharacter);
        // }
        if (option === fields[2] || option === "all") { // dob
            validRegexFormat(formNewPet.elements[fields[2]].value, regexDate, payload, fields[2], errMessageCharacter);
        }
        if (option === fields[3] || option === "all") { // petType
            validRegexFormat(formNewPet.elements[fields[3]].value, regexOnlyCharacter, payload, fields[3], errMessageCharacter);
        }
        if (option === fields[4] || option === "all") { // breed
            validRegexFormat(formNewPet.elements[fields[4]].value, regexOnlyCharacter, payload, fields[4], errMessageCharacter);
        }
        if (option === fields[5] || option === "all") { // gender
            validIncludeList(formNewPet.elements[fields[5]].value, genderChoice, payload, fields[5], errMessageCharacter);
        }
        if (option === fields[6] || option === "all") { // sterilization
            validIncludeList(formNewPet.elements[fields[6]].value, booleanChoice, payload, fields[6], errMessageCharacter);
        }
    }
    return payload;
}

/*
* Use on Files
* - infoAccount for form in createAppointmentPopUp
* - appointment for form in editAppointment
* */
function serviceValidNewAppointment(formNewAppointment, option){
    const payload = [];
    const fields = ["appDate", "period"];
    if (formNewAppointment != null || formNewAppointment != undefined){
        if (option === "reset") {
            realTimeValid_formNewAppointment = false;
            realTimeValid_formEditAppointment = false;
            resetForm(formNewAppointment, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // appDate
            let todayOrFuture = false
            if (regexDate.test(formNewAppointment.elements[fields[0]].value)){
                if (new Date(formNewAppointment.elements[fields[0]].value).setHours(0,0,0,0) >= new Date($.now()).setHours(0,0,0,0)){
                    todayOrFuture = true
                }
            }
            validRegexFormat(formNewAppointment.elements[fields[0]].value, todayOrFuture ? regexDate : regexPhoneNumber, payload, fields[0], errMessageCharacter);
        }
        if (option === fields[1] || option === "all") { // image
            validIncludeList(formNewAppointment.elements[fields[1]].value, periodChoice, payload, fields[1], errMessageCharacter);
        }
    }
    return payload;
}

/*
* Use on Files
* - billDetail for form in addMedToBill
* - billDetail for form in editBillMed
* */
function serviceValidAddMedToBill(formMedToBill, option){
    const payload = [];
    const fields = ["medID", "newDescription", "cureAmount"];
    if (formMedToBill != null || formMedToBill != undefined){
        if (option === "reset") {
            realTimeValid_formAddMedToBill = false;
            realTimeValid_formEditBillMed = false;
            resetForm(formMedToBill, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // medId
            validRegexFormat(formMedToBill.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // Description
            // validRegexFormat(formMedToBill.elements[fields[1]].value, regexOnlyCharacterWithNumber, payload, fields[1], errMessageCharacter);
            payload.push(new payloadValid(fields[1],"valid"))
        }
        if (option === fields[2] || option === "all") { // cureAmount
            validNumberInt(formMedToBill.elements[fields[2]].value, payload, fields[2], "Input quantityIn");
        }
    }
    return payload;
}

/*
* Use on Files
* - billDetail for form in addToolToBill
* - billDetail for form in editBillTool
* */
function serviceValidAddToolToBill(formToolToBill, option){
    const payload = [];
    const fields = ["toolID", "cureAmount"];
    if (formToolToBill != null || formToolToBill != undefined){
        if (option === "reset") {
            realTimeValid_formAddToolToBill = false;
            realTimeValid_formEditBillTool = false;
            resetForm(formToolToBill, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // medId
            validRegexFormat(formToolToBill.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // cureAmount
            validNumberInt(formToolToBill.elements[fields[1]].value, payload, fields[1], "Input quantityIn");
        }
    }
    return payload;
}

/*
* Use on Files
* - billDetail for form in addToolToBill
* - billDetail for form in editBillService
* */
function serviceValidAddServiceToBill(formServiceToBill, option){
    const payload = [];
    const fields = ["servingID", "cureAmount"];
    if (formServiceToBill != null || formServiceToBill != undefined){
        if (option === "reset") {
            realTimeValid_formAddServiceToBill = false;
            realTimeValid_formEditBillService = false;
            resetForm(formServiceToBill, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // medId
            validRegexFormat(formServiceToBill.elements[fields[0]].value, regexOnlyCharacterWithNumber, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // cureAmount
            validNumberInt(formServiceToBill.elements[fields[1]].value, payload, fields[1], "Input quantityIn");
        }
    }
    return payload;
}

/*
* Use on Files
* - billDetail for form in editWeight
* */
function serviceValidReceiveTreatment(formReceiveTreatment, option){
    const payload = [];
    const fields = ["cc", "ht","weight","temp"];
    if (formReceiveTreatment != null || formReceiveTreatment != undefined){
        if (option === "reset") {
            realTimeValid_formReceiveTreatment = false;
            resetForm(formReceiveTreatment, fields, true, true);
            return payload;
        }
        if (option === 'all'){
            payload.push(new payloadValid(fields[0], 'valid'));
            payload.push(new payloadValid(fields[1], 'valid'));
        }
        if (option === fields[2] || option === "all") { // medId
            validNumberDouble(formReceiveTreatment.elements[fields[2]].value, payload, fields[2], errMessageCharacter)
        }
        if (option === fields[3] || option === "all") { // medId
            validNumberDouble(formReceiveTreatment.elements[fields[3]].value, payload, fields[3], errMessageCharacter)
        }

    }
    return payload;
}

function serviceValidEditTreat(formEditTreat, option){
    const payload = [];
    const fields = ["weight","temp"];
    if (formEditTreat != null || formEditTreat != undefined){
        if (option === "reset") {
            realTimeValid_formEditTreatment = false;
            resetForm(formEditTreat, fields, true, true);
            return payload;
        }
        if (option === fields[0] || option === "all") { // medId
            validNumberDouble(formEditTreat.elements[fields[0]].value, payload, fields[0], errMessageCharacter)
        }
        if (option === fields[1] || option === "all") { // medId
            validNumberDouble(formEditTreat.elements[fields[1]].value, payload, fields[1], errMessageCharacter)
        }

    }
    return payload;
}

/*
* Use on Files
* - billDetail for form in editWeight
* */
function serviceValidEditTreatment(formEditTreatment, option){
    const payload = [];
    const fields = ["diagnosis", "weight"];
    if (formEditTreatment != null || formEditTreatment != undefined){
        if (option === "reset") {
            realTimeValid_formEditTreatment = false;
            resetForm(formEditTreatment, fields, true, true);
            return payload;
        }
        if (option === 'all'){
            payload.push(new payloadValid(fields[0], 'valid'));
        }
        if (option === fields[1] || option === "all") { // medId
            validNumberDouble(formEditTreatment.elements[fields[1]].value, payload, fields[1], errMessageCharacter)
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
            if (parseInt(value) > 0){
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

function resetForm(formName, fields, removeClassValid, clearTextInput){
    let form = document.getElementById(formName);
    fields.forEach((fieldName) => {
        if (removeClassValid) {
            if (form.elements[fieldName] != null || form.elements[fieldName] != undefined){
                form.elements[fieldName].classList.remove("is-valid");
                form.elements[fieldName].classList.remove("is-invalid");
            }
        }
    });
    if (clearTextInput) {
        form.reset();
    }
}
