package main.VeterinaryClinic.Service;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Service.Account.AccountService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerateFileService {
    @Autowired
    private AccountService accountService;
    public String exportReport(String reportForm){
        try {
            List<Account> accountList = accountService.getAll();
            File file = ResourceUtils.getFile("classpath:jasper/templates/" + reportForm + ".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(accountList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Java Spring");
            JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, parameters, dataSource);
            return getUploadPath(jasperPrint, reportForm).toString();
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
