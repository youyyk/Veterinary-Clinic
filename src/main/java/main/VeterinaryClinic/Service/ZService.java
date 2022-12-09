package main.VeterinaryClinic.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ZService {
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}