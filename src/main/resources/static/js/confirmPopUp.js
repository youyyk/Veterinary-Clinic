// ------------- Don't allow "Enter" to submit form ------------
window.addEventListener('keydown', function(event)
{
    if (event.key === "Enter" && event.target.tagName !== 'TEXTAREA')
    {
        if(event.target.type !== 'submit')
        {
            event.preventDefault();
            return false;
        }
    }
});
// -------------- Create Pet --------------
function clickCreatePet(){
    if (!validSubmitNewPetForm(document.getElementById("createPetForm"),'all')){
        return false;
    }
    let createPane = document.getElementById("createPetPane");
    let createConfirm = document.getElementById("createPetConfirm");
    let header = document.getElementById("createPetHeader");
    let createName = document.getElementById("createName");

    header.innerHTML = "Add Pet (Confirmation)";
    createName.innerHTML = document.getElementById("name").value;
    createConfirm.style.display = show;
    createPane.style.display = hide;
}
function clickCreatePetNo() {
    setTimeout(function (){
        validSubmitNewPetForm('createPetForm','reset');
        let createPane = document.getElementById("createPetPane");
        let createConfirm = document.getElementById("createPetConfirm");
        let header = document.getElementById("createPetHeader");

        header.innerHTML = "Add Pet";
        createConfirm.style.display = hide;
        createPane.style.display = show;
    },1000);
}
// ------------- Edit Pet --------------
function clickEditPet(petID){
    if (!serviceValidNewPet(document.getElementById('editPetForm'+petID), 'all')){
        return false;
    }
    const formEdit = document.getElementById("editPetForm"+petID);
    let editPane = document.getElementById("editPetPane"+petID);
    let header = document.getElementById("editPetHeader"+petID);
    let editConfirm = document.getElementById("editPetConfirm"+petID);

    header.innerHTML = "Edit Pet Confirmation";
    console.log("check : "+formEdit.name.value)

    editConfirm.style.display = show;
    editPane.style.display = hide;

}
function clickEditPetNo(petID) {
    setTimeout(function (){
        serviceValidNewPet('editPetForm'+petID,'reset');
        let editPane = document.getElementById("editPetPane"+petID);
        let header = document.getElementById("editPetHeader"+petID);
        let editConfirm = document.getElementById("editPetConfirm"+petID);

        header.innerHTML = "Edit Pet";
        editConfirm.style.display = hide;
        editPane.style.display = show;
    },1000);
}

// ------------- Receive Treatment --------------
function clickReceiveTreat(petID){
    if (!validSubmitReceiveTreatmentForm(document.getElementById("receiveTreatForm"+petID),'all')){
        return false;
    }
    let receiveTreatPane = document.getElementById("receiveTreatPane"+petID);
    let receiveTreatConfirm = document.getElementById("receiveTreatConfirm"+petID);
    let header = document.getElementById("receiveTreatHeader"+petID);

    header.innerHTML = "Receive Treatment (Confirmation)";
    receiveTreatConfirm.style.display = show;
    receiveTreatPane.style.display = hide;
}
function clickReceiveTreatNo(petID) {
    setTimeout(function (){
        validSubmitReceiveTreatmentForm("receiveTreatForm"+petID,'reset');
        let receiveTreatForm = document.getElementById("receiveTreatForm"+petID);
        receiveTreatForm.reset();
        let receiveTreatPane = document.getElementById("receiveTreatPane"+petID);
        let receiveTreatConfirm = document.getElementById("receiveTreatConfirm"+petID);
        let header = document.getElementById("receiveTreatHeader"+petID);

        header.innerHTML = "Receive Treatment";
        receiveTreatConfirm.style.display = hide;
        receiveTreatPane.style.display = show;
    },1000);
}

// ------------- Edit Treatment --------------
function clickEditTreat(treatID){
    if (!validSubmitEditTreatmentForm(document.getElementById("editTreatForm"+treatID),'all')){
        return false;
    }
    let editTreatPane = document.getElementById("editTreatPane"+treatID);
    let editTreatConfirm = document.getElementById("editTreatConfirm"+treatID);
    let header = document.getElementById("editTreatHeader"+treatID);

    header.innerHTML = "Edit Treatment (Confirmation)";
    editTreatConfirm.style.display = show;
    editTreatPane.style.display = hide;
}
function clickEditTreatNo(treatID) {
    setTimeout(function (){
        validSubmitEditTreatmentForm("editTreatForm"+treatID,'reset');
        let editTreatPane = document.getElementById("editTreatPane"+treatID);
        let editTreatConfirm = document.getElementById("editTreatConfirm"+treatID);
        let header = document.getElementById("editTreatHeader"+treatID);

        header.innerHTML = "Edit Treatment";
        editTreatConfirm.style.display = hide;
        editTreatPane.style.display = show;
    },1000);
}
// ------------- Create Service --------------
function clickCreateService(){
    if (!validSubmitNewServiceForm(document.getElementById("createServiceForm"),'all')){
        return false;
    }
    let createPane = document.getElementById("createServicePane");
    let createConfirm = document.getElementById("createServiceConfirm");
    let header = document.getElementById("createServiceHeader");
    let createName = document.getElementById("createServiceName");

    header.innerHTML = "Add Service (Confirmation)";
    createName.innerHTML = document.getElementById("createServiceForm").name.value;
    createConfirm.style.display = show;
    createPane.style.display = hide;
}
function clickCreateServiceNo() {
    setTimeout(function (){
        validSubmitNewServiceForm('createServiceForm','reset');
        let createPane = document.getElementById("createServicePane");
        let createConfirm = document.getElementById("createServiceConfirm");
        let header = document.getElementById("createServiceHeader");

        header.innerHTML = "Add Service";
        createConfirm.style.display = hide;
        createPane.style.display = show;
    },1000);
}
// ------------- Edit Service --------------
function clickEditService(serviceID){
    if (!validSubmitEditServiceForm(document.getElementById("editServiceForm"+serviceID),'all')){
        return false;
    }
    let editTreatPane = document.getElementById("editServicePane"+serviceID);
    let editTreatConfirm = document.getElementById("editServiceConfirm"+serviceID);
    let header = document.getElementById("editServiceHeader"+serviceID);

    header.innerHTML = "Edit Service (Confirmation)";
    editTreatConfirm.style.display = show;
    editTreatPane.style.display = hide;
}
function clickEditServiceNo(serviceID) {
    setTimeout(function (){
        validSubmitEditServiceForm('editServiceForm'+serviceID,'reset');
        let editTreatPane = document.getElementById("editServicePane"+serviceID);
        let editTreatConfirm = document.getElementById("editServiceConfirm"+serviceID);
        let header = document.getElementById("editServiceHeader"+serviceID);

        header.innerHTML = "Edit Service";
        editTreatConfirm.style.display = hide;
        editTreatPane.style.display = show;
    },1000);
}
// ------------- Create Appointment --------------
function clickCreateAppointment(petID){
    if (!validSubmitNewAppointmentForm(document.getElementById("createAppointmentForm"+petID),'all')){
        return false;
    }
    let createPane = document.getElementById("createAppointmentPane"+petID);
    let createConfirm = document.getElementById("createAppointmentConfirm"+petID);
    let header = document.getElementById("createAppointmentHeader"+petID);
    let createName = document.getElementById("createAppointmentName"+petID);

    console.log(document.getElementById("createAppointmentForm"+petID).name.value)

    header.innerHTML = "Create Appointment (Confirmation)";
    // createName.innerHTML = document.getElementById("createAppointmentForm"+petID).name.value;
    createConfirm.style.display = show;
    createPane.style.display = hide;
}
function clickCreateAppointmentNo(petID) {
    setTimeout(function (){
        validSubmitNewAppointmentForm('createAppointmentForm'+petID,'reset');
        let createPane = document.getElementById("createAppointmentPane"+petID);
        let createConfirm = document.getElementById("createAppointmentConfirm"+petID);
        let header = document.getElementById("createAppointmentHeader"+petID);

        header.innerHTML = "Create Appointment ( "+document.getElementById("createAppointmentForm"+petID).name.value+" )";
        createConfirm.style.display = hide;
        createPane.style.display = show;
    },1000);
}
// ------------- Edit Appointment --------------
function clickEditAppointment(appID){
    if (!validSubmitEditAppointmentForm(document.getElementById("editAppointmentForm"+appID),'all')){
        return false;
    }
    let editAppointmentPane = document.getElementById("editAppointmentPane"+appID);
    let editAppointmentConfirm = document.getElementById("editAppointmentConfirm"+appID);
    let header = document.getElementById("editAppointmentHeader"+appID);

    header.innerHTML = "Edit Appointment (Confirmation)";
    editAppointmentConfirm.style.display = show;
    editAppointmentPane.style.display = hide;
}
function clickEditAppointmentNo(appID) {
    setTimeout(function (){
        validSubmitEditAppointmentForm('editAppointmentForm'+appID,'reset');
        let editAppointmentPane = document.getElementById("editAppointmentPane"+appID);
        let editAppointmentConfirm = document.getElementById("editAppointmentConfirm"+appID);
        let header = document.getElementById("editAppointmentHeader"+appID);

        header.innerHTML = "Edit Appointment";
        editAppointmentConfirm.style.display = hide;
        editAppointmentPane.style.display = show;
    },1000);
}
// ------------- Edit Account --------------
function clickEditAccount(){
    if (!validSubmitEditAccountForm(document.getElementById("editAccountForm"),'all')){
        return false;
    }
    let createPane = document.getElementById("editAccountPane");
    let createConfirm = document.getElementById("editAccountConfirm");
    let header = document.getElementById("editAccountHeader");

    header.innerHTML = "Edit Account (Confirmation)";
    createConfirm.style.display = show;
    createPane.style.display = hide;
}
function clickEditAccountNo() {
    setTimeout(function (){
        validSubmitEditAccountForm('editAccountForm','reset');
        let createPane = document.getElementById("editAccountPane");
        let createConfirm = document.getElementById("editAccountConfirm");
        let header = document.getElementById("editAccountHeader");

        header.innerHTML = "Edit Account";
        createConfirm.style.display = hide;
        createPane.style.display = show;
    },1000);
}
// ------------- Create Warehouse --------------
function clickCreateItem(wareType){
    if (wareType === "Medicine"){
        if (!validSubmitNewMedicineForm(document.getElementById("create"+wareType+"Form"),'all')){
            return false;
        }
    } else if (wareType === "MedicineOrder") {
        if (!validSubmitNewWarehouseMedicineForm(document.getElementById("create"+wareType+"Form"),'all')){
            return false;
        }
    } else if (wareType === "Tool") {
        if (!validSubmitNewToolForm(document.getElementById("create"+wareType+"Form"),'all')){
            return false;
        }
    } else if (wareType === "ToolOrder") {
        if (!validSubmitNewWarehouseToolForm(document.getElementById("create"+wareType+"Form"),'all')){
            return false;
        }
    }
    let header = document.getElementById("createWarehouseHeader");
    let createPane = document.getElementById("create"+wareType+"Pane");
    let createConfirm = document.getElementById("create"+wareType+"Confirm");
    let createName = document.getElementById("create"+wareType+"Name");
    let chooseDropdown = document.getElementById("chooseDropdown");

    if (wareType.includes("Medicine")){
        header.innerHTML = "Add Medicine (Confirmation)";
    }
    else if(wareType.includes("Tool")){
        header.innerHTML = "Add Tool (Confirmation)";
    }
    createName.innerHTML = document.getElementById("create"+wareType+"Form").name.value;
    createConfirm.style.display = show;
    createPane.style.display = hide;
    chooseDropdown.style.display = hide;
}
function clickCreateItemNo(wareType) {
    setTimeout(function (){
        if (wareType === "Medicine" || wareType === "allAddWH"){
            validSubmitNewMedicineForm("createMedicineForm",'reset');
            warehouseConfirmHelper("Medicine");
;        }
        if (wareType === "MedicineOrder" || wareType === "allAddWH") {
            validSubmitNewWarehouseMedicineForm("createMedicineOrderForm",'reset');
            warehouseConfirmHelper("MedicineOrder");
        }
        if (wareType === "Tool" || wareType === "allAddWH") {
            validSubmitNewToolForm("createToolForm",'reset');
            warehouseConfirmHelper("Tool");
        }
        if (wareType === "ToolOrder" || wareType === "allAddWH") {
            validSubmitNewWarehouseToolForm("createToolOrderForm",'reset');
            warehouseConfirmHelper("ToolOrder");
        }
    },1000);
}

function warehouseConfirmHelper(wareType){
    let header = document.getElementById("createWarehouseHeader");
    let createPane = document.getElementById("create"+wareType+"Pane");
    let createConfirm = document.getElementById("create"+wareType+"Confirm");
    let chooseDropdown = document.getElementById("chooseDropdown");

    header.innerHTML = "Add Medicine / Tool ";
    createConfirm.style.display = hide;
    createPane.style.display = show;
    chooseDropdown.style.display = show;
}
// ------------- Edit Item --------------
function clickEditItem(wareType,id){
    if (wareType === "Order"){
        if (!validSubmitEditOrderWarehouseForm(document.getElementById("editOrderForm"+id),'all')){
            return false;
        }
    }
    else if(wareType === "Medicine"){
        if (!validSubmitEditMedicineInfoForm(document.getElementById("editMedicineForm"+id),'all')){
            return false;
        }
    }
    else if(wareType === "Tool"){
        if (!validSubmitEditToolInfoForm(document.getElementById("editToolForm"+id),'all')){
            return false;
        }
    }
    let editItemPane = document.getElementById("edit"+wareType+"Pane"+id);
    let editItemConfirm = document.getElementById("edit"+wareType+"Confirm"+id);
    let header = document.getElementById("edit"+wareType+"Header"+id);

    header.innerHTML = "Edit "+wareType+" (Confirmation)";
    editItemConfirm.style.display = show;
    editItemPane.style.display = hide;
}
function clickEditItemNo(wareType,id) {
    setTimeout(function (){
        let editItemPane = document.getElementById("edit"+wareType+"Pane"+id);
        let editItemConfirm = document.getElementById("edit"+wareType+"Confirm"+id);
        let header = document.getElementById("edit"+wareType+"Header"+id);

        if (wareType === "Order"){
            header.innerHTML = "Edit Order ID "+id;
            validSubmitEditOrderWarehouseForm("editOrderForm"+id,'reset');
        }
        else if(wareType === "Medicine" || wareType === "Tool"){
            if (wareType === "Medicine"){
                validSubmitEditMedicineInfoForm("editMedicineForm"+id,'reset');
            } else {
                validSubmitEditToolInfoForm("editToolForm"+id,'reset');
            }
            header.innerHTML = "Edit "+wareType+" ("+document.getElementById("edit"+wareType+"Form"+id).name.value+")";
        }
        editItemConfirm.style.display = hide;
        editItemPane.style.display = show;
    },1000);
}
// ------------- Add Item to Bill --------------
function clickAddItemToBill(item){
    let header = document.getElementById("add"+item+"ToBillHeader");
    let addItemPane = document.getElementById("add"+item+"ToBillPane");
    let addItemConfirm = document.getElementById("add"+item+"ToBillConfirm");
    let addItemName = document.getElementById("add"+item+"ToBillName");

    if (item.includes("Med")){
        if (!validSubmitAddMedToBillForm(document.getElementById("addMedToBillForm"),'all')){
            return false;
        }
        header.innerHTML = "Add Medicine (Confirmation)";
        let chooseDropdown = document.getElementById("add"+item+"ToBillForm").medID;
        chooseDropdown = (chooseDropdown[chooseDropdown.selectedIndex].text).split("(")[0].trim();
        addItemName.innerHTML = chooseDropdown;
        console.log(chooseDropdown)
    }
    else if(item.includes("Tool")){
        if (!validSubmitAddToolToBillForm(document.getElementById("addToolToBillForm"),'all')){
            return false;
        }
        header.innerHTML = "Add Tool (Confirmation)";
        let chooseDropdown = document.getElementById("add"+item+"ToBillForm").toolID;
        chooseDropdown = (chooseDropdown[chooseDropdown.selectedIndex].text).split("(")[0].trim();
        addItemName.innerHTML = chooseDropdown;
        console.log(chooseDropdown)
    }
    else if(item.includes("Service")){
        if (!validSubmitAddServiceToBillForm(document.getElementById("addServiceToBillForm"),'all')){
            return false;
        }
        header.innerHTML = "Add Service (Confirmation)";
        let chooseDropdown = document.getElementById("add"+item+"ToBillForm").servingID;
        chooseDropdown = (chooseDropdown[chooseDropdown.selectedIndex].text).trim();
        addItemName.innerHTML = chooseDropdown;
        console.log(chooseDropdown)
    }

    addItemConfirm.style.display = show;
    addItemPane.style.display = hide;
}
function clickAddItemToBillNo(item) {
    setTimeout(function (){
        let header = document.getElementById("add"+item+"ToBillHeader");
        let addItemPane = document.getElementById("add"+item+"ToBillPane");
        let addItemConfirm = document.getElementById("add"+item+"ToBillConfirm");

        if (item.includes("Med")){
            validSubmitAddMedToBillForm('addMedToBillForm','reset');
            header.innerHTML = "Add Medicine";
        }
        else if(item.includes("Tool")){
            validSubmitAddToolToBillForm('addToolToBillForm', 'reset');
            header.innerHTML = "Add Tool";
        }
        else if(item.includes("Service")){
            validSubmitAddServiceToBillForm('addServiceToBillForm', 'reset');
            header.innerHTML = "Add Service";
        }
        addItemConfirm.style.display = hide;
        addItemPane.style.display = show;
    },1000);
}
// ------------- Edit Bill --------------
function clickEditBill(item,id){
    let header = document.getElementById("edit"+item+"BillHeader"+id);
    let addItemPane = document.getElementById("edit"+item+"BillPane"+id);
    let addItemConfirm = document.getElementById("edit"+item+"BillConfirm"+id);
    let addItemName = document.getElementById("edit"+item+"BillName"+id);

    if (item.includes("Med")){
        if (!validSubmitEditBillMedForm(document.getElementById("editMedBillForm"+id),'all')){
            return false;
        }
        header.innerHTML = "Edit Medicine (Confirmation)";
    }
    else if(item.includes("Tool")){
        if (!validSubmitEditBillToolForm(document.getElementById("editToolBillForm"+id),'all')){
            return false;
        }
        header.innerHTML = "Edit Tool (Confirmation)";
    }
    else if(item.includes("Service")){
        if (!validSubmitEditBillServiceForm(document.getElementById("editServiceBillForm"+id),'all')){
            return false;
        }
        header.innerHTML = "Edit Service (Confirmation)";
    }

    addItemConfirm.style.display = show;
    addItemPane.style.display = hide;
}
function clickEditBillNo(item,id,name) {
    setTimeout(function (){
        let header = document.getElementById("edit"+item+"BillHeader"+id);
        let addItemPane = document.getElementById("edit"+item+"BillPane"+id);
        let addItemConfirm = document.getElementById("edit"+item+"BillConfirm"+id);

        if (item.includes("Med")){
            validSubmitEditBillMedForm("editMedBillForm"+id,'reset');
        }
        else if(item.includes("Tool")){
            validSubmitEditBillToolForm("editToolBillForm"+id, 'reset');
        }
        else if(item.includes("Service")){
            validSubmitEditBillServiceForm("editServiceBillForm"+id, 'reset');
        }

        header.innerHTML = "Edit "+name;

        addItemConfirm.style.display = hide;
        addItemPane.style.display = show;
    },1000);
}
// ------------- Edit Treat Bill --------------
function clickEditTreatBill(detail){
    if (!validSubmitReceiveTreatmentForm(document.getElementById('editWeightForm'),'all')){
        return false;
    }
    let header = document.getElementById("edit"+detail+"Header");
    let addItemPane = document.getElementById("edit"+detail+"Pane");
    let addItemConfirm = document.getElementById("edit"+detail+"Confirm");

    header.innerHTML = "Edit Temperature & Weight" + " (Confirmation)";

    addItemConfirm.style.display = show;
    addItemPane.style.display = hide;
}
function clickEditTreatBillNo(detail) {
    setTimeout(function (){
        validSubmitReceiveTreatmentForm('editWeightForm','reset');
        let header = document.getElementById("edit"+detail+"Header");
        let addItemPane = document.getElementById("edit"+detail+"Pane");
        let addItemConfirm = document.getElementById("edit"+detail+"Confirm");

        header.innerHTML = "Edit Temperature & Weight";

        addItemConfirm.style.display = hide;
        addItemPane.style.display = show;
    },1000);
}
// ------------- Payment --------------
function clickPay(billID){
    if (!validSubmitPaymentPopUp(document.getElementById("paymentForm"+billID),'all')){
        return false;
    }
    let paymentPane = document.getElementById("paymentPane"+billID);
    let paymentConfirm = document.getElementById("paymentConfirm"+billID);
    let header = document.getElementById("paymentHeader"+billID);

    header.innerHTML = "Payment (Confirmation)";
    paymentConfirm.style.display = show;
    paymentPane.style.display = hide;
}
function clickPayNo(billID) {
    setTimeout(function (){
        clearPaymentForm();
        let paymentPane = document.getElementById("paymentPane"+billID);
        let paymentConfirm = document.getElementById("paymentConfirm"+billID);
        let header = document.getElementById("paymentHeader"+billID);

        header.innerHTML = "Payment Details";
        paymentConfirm.style.display = hide;
        paymentPane.style.display = show;
    },1000);
}

// -------------- Register without LINE --------------
function clickRegister(){
    if (!validSubmitEditAccountForm(document.getElementById("registerForm"),'all')){
        return false;
    }
    let registerPane = document.getElementById("registerPane");
    let registerConfirm = document.getElementById("registerConfirm");
    let header = document.getElementById("registerHeader");
    let save = document.getElementById("registerSave");

    header.innerHTML = "Register (Confirmation)";
    registerConfirm.style.display = show;
    registerPane.style.display = hide;
    save.style.display = hide;
}
function clickRegisterNo() {
    setTimeout(function (){
        validSubmitEditAccountForm('registerForm','reset');
        let registerPane = document.getElementById("registerPane");
        let registerConfirm = document.getElementById("registerConfirm");
        let header = document.getElementById("registerHeader");
        let save = document.getElementById("registerSave");

        header.innerHTML = "Register";
        registerConfirm.style.display = hide;
        registerPane.style.display = show;
        save.style.display = show;
    },1000);
}
// ------------- Merge Account --------------
function clickMerge(mergeAccId){
    let mergeAccountId= document.getElementById("mergeAccId");
    mergeAccountId.value = mergeAccId;
    let mergeAccountFormPane = document.getElementById("mergeAccountFormPane");
    let mainAccountTable = document.getElementById("mainAccountTable");
    let textMainAccount = document.getElementById("textMainAccount");
    let popUpMergeAccount = document.getElementById("popUpMergeAccount");
    let mergeAccountConfirm = document.getElementById("mergeAccountConfirm");
    let mergeAccountFormHeader = document.getElementById("mergeAccountFormHeader");

    popUpMergeAccount.style.maxWidth = "500px";
    mergeAccountFormHeader.innerHTML = "Merge Account (Confirmation)";
    mergeAccountConfirm.style.display = show;
    mergeAccountFormPane.style.display = hide;
    mainAccountTable.style.display = hide;
    textMainAccount.style.display = hide;
}
function clickMergeNo() {
    setTimeout(function (){
        let mergeAccountFormPane = document.getElementById("mergeAccountFormPane");
        let mainAccountTable = document.getElementById("mainAccountTable");
        let textMainAccount = document.getElementById("textMainAccount");
        let popUpMergeAccount = document.getElementById("popUpMergeAccount");
        let mergeAccountConfirm = document.getElementById("mergeAccountConfirm");
        let mergeAccountFormHeader = document.getElementById("mergeAccountFormHeader");
        popUpMergeAccount.style.maxWidth = "700px";
        mergeAccountFormHeader.innerHTML = "Merge Account";
        mergeAccountConfirm.style.display = hide;
        mergeAccountFormPane.style.display = show;
        mainAccountTable.style.display = '';
        textMainAccount.style.display = '';
    },1000);
}