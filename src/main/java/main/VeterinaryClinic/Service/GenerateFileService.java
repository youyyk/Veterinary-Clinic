package main.VeterinaryClinic.Service;

import lombok.Data;

import main.VeterinaryClinic.Service.Account.AccountService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
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
    private final short normalFixRow = 20;
    private final String receiptForm = "receipt";
    @Data
    public class DescriptionBody{
        private String description;
        private double amount;

        public DescriptionBody(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
    }
    public JasperPrint exportReceiptPDF(){
        try {
            List<DescriptionBody> descriptionBodyList = new ArrayList<>();
            for (int i=0; i<25; i++){
                descriptionBodyList.add(new DescriptionBody("Test"+i, 100));
            }
            File file = ResourceUtils.getFile("classpath:jasper/templates/" + receiptForm + ".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(descriptionBodyList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RECEIPT_HEAD_INFO", "วันที่ออกใบเสร็จ (Date) : " + GlobalService.getStringCurrentTime() + " ใบเสร็จเลขที่ (Bill No.) : " + "12345678");
            parameters.put("RECEIPT_PET_NAME", "Hi Pet Name");
            parameters.put("RECEIPT_CUSTOMER_NAME", "My Name Customer");
            parameters.put("RECEIPT_CUSTOMER_ADDRESS", "aaaaaaaaaaaaaaaaaaaaaa");
            parameters.put("RECEIPT_EXTRA_DISCOUNT", Double.parseDouble("100"));
            parameters.put("RECEIPT_NET_TOTAL", Double.parseDouble("1100"));
            parameters.put("RECEIPT_PAY_TYPE", "เงินสด");
            parameters.put("RECEIPT_PAY_RECEIVE", Double.parseDouble("500"));
            parameters.put("RECEIPT_PAY_RETURN", Double.parseDouble("600"));
            parameters.put("RECEIPT_CASHIER_NAME", "Hi Cashier");
            parameters.put("RECEIPT_LOGO_FILE",  ResourceUtils.getFile("classpath:jasper/images/logo.png").getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, parameters, dataSource);
            return jasperPrint;
//            return getUploadPath(jasperPrint, reportForm).toString();
        } catch (JRException | IOException e){
            System.out.println(e.getMessage());
        }
        return null;
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
