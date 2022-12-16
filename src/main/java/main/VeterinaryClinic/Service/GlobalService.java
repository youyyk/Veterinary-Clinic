package main.VeterinaryClinic.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GlobalService {
    public static String getStringCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public static Date getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(formatter.format(date));
        return date;
    }
    public static Date convertStringToDate(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date date = formatter.parse(strDate);
            System.out.println(date);
            return date;
        }
        catch(Exception e){
            System.out.println("Cannot Convert String to Date");
        }
        return null;
    }


}