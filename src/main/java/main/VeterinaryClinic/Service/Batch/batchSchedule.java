package main.VeterinaryClinic.Service.Batch;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.AppointmentService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.LineSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class batchSchedule {
    @Autowired
    AccountService accountService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    LineSendService lineSendService;
//    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 9 * * *") // Run every day at 9 A.M.
    public void runTask() {
        Date today = GlobalService.getCurrentTime();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(today);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        tomorrow.add(Calendar.DATE, 1); // Tomorrow
        List<Appointment> appointmentList = appointmentService.findByDate(tomorrow.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(tomorrow.getTime());
        Map<String, String> prepareMessage = new HashMap<>();
        for (Appointment appointment : appointmentList){
            Pet pet = appointment.getPet();
            Account account = pet.getAccount();
            if (prepareMessage.get(account.getAccId()) == null){
                prepareMessage.put(String.valueOf(account.getLineId()), "สัตว์เลี้ยงของคุณมีนัดในวันที่ "+ formattedDate + "\n*โปรดเช็คภายในระบบอีกครั้ง");
            }
        }
        lineSendService.sendMessageToClient(prepareMessage);
    }
}
