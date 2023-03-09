package main.VeterinaryClinic.Controller.API;

import main.VeterinaryClinic.Service.GenerateFileService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

    @Autowired
    GenerateFileService generateFileService;

    @RequestMapping(value = "/export/receipt", method = RequestMethod.GET)
    public void export(HttpServletResponse response) {
        JasperPrint jasperPrint = null;
        String nameExportFile = "test.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename="+nameExportFile));
        try {
            OutputStream out = response.getOutputStream();
            jasperPrint = generateFileService.exportReceiptPDF();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        } catch (Exception e ){
            System.out.println("FileDownloadController Alert");
            System.out.println(e.getMessage());
        }
    }
}