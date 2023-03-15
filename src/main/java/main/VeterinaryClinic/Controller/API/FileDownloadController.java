package main.VeterinaryClinic.Controller.API;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillTool;
import main.VeterinaryClinic.Service.GenerateFileService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.MainBillService;
import main.VeterinaryClinic.Service.SubBill.BillMedicineService;
import main.VeterinaryClinic.Service.SubBill.BillToolService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

    @Autowired
    GenerateFileService generateFileService;
    @Autowired
    MainBillService mainBillService;
    @Autowired
    private BillMedicineService billMedicineService;
    @Autowired
    private BillToolService billToolService;

    @RequestMapping(value = "/export/receipt/{typeDoc}/{billId}", method = RequestMethod.GET)
    public void export(HttpServletResponse response, @PathVariable String typeDoc, @PathVariable long billId, @AuthenticationPrincipal AccountUserDetail accountUserDetail) {
        if (typeDoc.equals("details") || typeDoc.equals("detail")){
            String nameExportFile = GlobalService.getStringCurrentTime()+"_"+typeDoc+"_"+billId;
            Bill bill = mainBillService.findByBillID(billId);
            List<BillMedicine> billMedList = billMedicineService.findByBill(bill);
            List<BillTool> billToolList = billToolService.findByBill(bill);
            boolean validCondition = false;
            if (bill != null){
                if (accountUserDetail != null){
                    Account nowAccount = accountUserDetail.getAccount();
                    if ( (typeDoc.equals("details") && nowAccount != null) ||
//                    if ( (typeDoc.equals("details") && nowAccount != null && (nowAccount.isAdmin() || nowAccount.isOfficer())) ||
                            (typeDoc.equals("detail") && nowAccount != null)){
                        validCondition = true;
                    }
                }
                if (validCondition){
                    JasperPrint jasperPrint = generateFileService.exportReceiptPDF(bill, billMedList, billToolList, bill.getServiceUsed(), typeDoc);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", String.format("attachment; filename="+nameExportFile+".pdf"));
                    try {
                        OutputStream out = response.getOutputStream();
//            jasperPrint = generateFileService.exportReceiptPDF(); // Method for detail in pdf file
                        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                    } catch (Exception e ){
                        System.out.println("FileDownloadController Alert");
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
