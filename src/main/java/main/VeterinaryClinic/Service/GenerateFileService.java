package main.VeterinaryClinic.Service;

import lombok.Data;

import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Bill.BillMedicine;
import main.VeterinaryClinic.Model.Bill.BillServing;
import main.VeterinaryClinic.Model.Bill.BillTool;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;



@Service
public class GenerateFileService {
    private final short receiptFixRowA4 = 25;
    private final short receiptFixRowA5 = 15;
    private final String receiptFormA4 = "receipt";
    private final String receiptFormA5 = "receiptA5";
    @Data
    public class DescriptionBody{
        private String description;
        private double amount;

        public DescriptionBody(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }

        public DescriptionBody() {
            this.description = "";
            this.amount = -1;
        }
    }
    public JasperPrint exportReceiptPDF(Bill bill, List<BillMedicine> billMedicines, List<BillTool> billToolList, List<BillServing> billServings, String typeDoc){
        try {
            String nameJasperFile = "";
            List<DescriptionBody> descriptionBodyList = new ArrayList<>();
            if (typeDoc.equals("details")){ // all detail in bill
                nameJasperFile = receiptFormA4;
                convertBillDetailToDescriptionBody(descriptionBodyList, billMedicines, billToolList, billServings);
                fillDescriptionWithEmpty(descriptionBodyList, receiptFixRowA4);
            } else if (typeDoc.equals("detail")) {
                nameJasperFile = receiptFormA5;
                convertBillSumDetailToDescriptionBody(descriptionBodyList, billMedicines, billToolList, billServings);
                fillDescriptionWithEmpty(descriptionBodyList, receiptFixRowA5);
            }
            File file = ResourceUtils.getFile("classpath:jasper/templates/" + nameJasperFile + ".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(descriptionBodyList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RECEIPT_LOGO_FILE",  ResourceUtils.getFile("classpath:jasper/images/logo.png").getAbsolutePath());
            parameters.put("RECEIPT_HEAD_INFO", "วันที่ออกใบเสร็จ (Date) : " + GlobalService.getStringCurrentTime() + " ใบเสร็จเลขที่ (Bill No.) : " + bill.getBillID());
            parameters.put("RECEIPT_PET_NAME", bill.getTreatmentHistory().getPet().getName());
            parameters.put("RECEIPT_CUSTOMER_NAME", bill.getTreatmentHistory().getPet().getAccount().getFullName());
            parameters.put("RECEIPT_CUSTOMER_ADDRESS", bill.getTreatmentHistory().getPet().getAccount().getAddress());
            parameters.put("RECEIPT_EXTRA_DISCOUNT", bill.getDiscount());
            parameters.put("RECEIPT_NET_TOTAL", bill.getTotal()-bill.getDiscount());
            parameters.put("RECEIPT_PAY_TYPE", bill.getPayType());
            parameters.put("RECEIPT_PAY_RECEIVE", bill.getReceive());
            double returnValue = bill.getReceive()-(bill.getTotal()-bill.getDiscount());
            parameters.put("RECEIPT_PAY_RETURN", returnValue >= 0 ? returnValue : 0 );
            parameters.put("RECEIPT_WHO_RECEIVE",  bill.getCashier());
//            parameters.put("RECEIPT_NEXT_APPOINTMENT",  "2019/12/5");

            JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, parameters, dataSource);
            return jasperPrint;
//            return getUploadPath(jasperPrint, reportForm).toString();
        } catch (JRException | IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void convertBillSumDetailToDescriptionBody(List<DescriptionBody> descriptionBodyList, List<BillMedicine> billMedicines, List<BillTool> billToolList, List<BillServing> billServings){
        double medCost = 0;
        double toolCost = 0;
        for (BillMedicine billMedicine : billMedicines){
            medCost += billMedicine.getMedTotal() * billMedicine.getWareHouse().getMedicine().getPrice();
        }
        for (BillTool billTool  : billToolList){
            toolCost += billTool.getToolTotal() * billTool.getWareHouse().getTool().getPrice();
        }
        for (BillServing billServing : billServings){
            toolCost += billServing.getServingTotal() * billServing.getServing().getPrice();
        }
        if (medCost > 0){
            descriptionBodyList.add(new DescriptionBody("ค่ายาสำหรับตรวจรักษา", medCost));
        }
        if (toolCost > 0){
            descriptionBodyList.add(new DescriptionBody("ค่าอุปกรณ์และเวชภัณฑ์", toolCost));
        }
    }

    private void convertBillDetailToDescriptionBody(List<DescriptionBody> descriptionBodyList, List<BillMedicine> billMedicines, List<BillTool> billToolList, List<BillServing> billServings){
        for (BillMedicine billMedicine : billMedicines){
            System.out.println(billMedicine.getWareHouse().getMedicine().getName());
            descriptionBodyList.add(new DescriptionBody(billMedicine.getWareHouse().getMedicine().getName(), billMedicine.getMedTotal() * billMedicine.getWareHouse().getMedicine().getPrice()));
        }
        for (BillTool billTool  : billToolList){
            descriptionBodyList.add(new DescriptionBody(billTool.getWareHouse().getTool().getName(), billTool.getToolTotal() * billTool.getWareHouse().getTool().getPrice()));
        }
        for (BillServing billServing : billServings){
            descriptionBodyList.add(new DescriptionBody(billServing.getServing().getName(), billServing.getServingTotal() * billServing.getServing().getPrice()));
        }
    }

    private void fillDescriptionWithEmpty(List<DescriptionBody> descriptionBodyList, short fixRowNumber){
        int sizeList = descriptionBodyList.size();
        if (sizeList < fixRowNumber) {
            for (int i=0; i<fixRowNumber-sizeList; i++){
                descriptionBodyList.add(new DescriptionBody());
            }
        }
    }

    private Path getUploadPath(JasperPrint jasperPrint, String reportForm) throws JRException, IOException {
        String uploadDir = StringUtils.cleanPath("./src/main/resources/jasper/export/"+reportForm);
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss") ;
        String fileName = reportForm + "_" + dateFormat.format(new Date());
        JasperExportManager.exportReportToPdfFile(jasperPrint, uploadDir+"/"+fileName+".pdf");
        return uploadPath;
    }
}

