package main.VeterinaryClinic.Service.Batch;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.Account.AccountService;
import main.VeterinaryClinic.Service.AppointmentService;
import main.VeterinaryClinic.Service.GlobalService;
import main.VeterinaryClinic.Service.LineSendService;
import main.VeterinaryClinic.Service.WareHouseService;
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
    @Autowired
    WareHouseService wareHouseService;
    @Scheduled(fixedRate = 5000)
//    @Scheduled(cron = "0 0 9 * * *") // Run every day at 9 A.M.
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
            if (account.getLineId() != null && !account.getLineId().isBlank() && !account.getLineId().isEmpty()){
                if (prepareMessage.get(account.getLineId()) == null){
                    prepareMessage.put(account.getLineId(), "สัตว์เลี้ยงของคุณมีนัดในวันที่ "+ formattedDate + "\n*โปรดเช็คภายในระบบอีกครั้ง");
                }
            }
        }
        lineSendService.sendMessageToClient(prepareMessage);
    }

        @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0 9 * * *") // Run every day at 9 A.M.
    public void warehouseAlert() {
        Date today = GlobalService.getCurrentTime();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(today);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(tomorrow.getTime());
        Map<String, String> prepareMessage = new HashMap<>();
//        List<Account> accountList = accountService
        List<WareHouse> wareHouses = wareHouseService.findBySoftDeletedOrderByExpiredDateAsc(false);
        int expiredMedCount = 0;
        int almostMedCount = 0;
        int expiredToolCount = 0;
        int almostToolCount = 0;
        for (WareHouse wh : wareHouses) {
            short expiredType = wh.isExpired();
            if (expiredType == -1 && wh.getType().equals("medicine")){
                expiredMedCount++;
            } else if (expiredType == 1 && wh.getType().equals("medicine")) {
                almostMedCount++;
            }
            if (expiredType == -1 && wh.getType().equals("tool")){
                expiredToolCount++;
            } else if (expiredType == 1 && wh.getType().equals("tool")) {
                almostToolCount++;
            }
        }
        for (Account account : accountService.getAll()){
            if (account.getLineId() != null && !account.getLineId().isBlank() && !account.getLineId().isEmpty() && account.isAdmin()){
                if (prepareMessage.get(account.getLineId()) == null){
                    prepareMessage.put(account.getLineId(), "แจ้งเตือนของในคลังประจำวันที่ "+ formattedDate
                            + "\nยาหมดอายุ : "+expiredMedCount+" รายการ"
                            + "\nยาใกล้หมดอายุ : "+almostMedCount+" รายการ"
                            + "\nอุปกรณ์หมดอายุ : "+expiredToolCount+" รายการ"
                            + "\nอุปกรณ์ใกล้ยาหมดอายุ : "+almostToolCount+" รายการ"
                            + "\n*โปรดเช็คภายในระบบอีกครั้ง");
                }
            }
        }

        lineSendService.sendMessageToClient(prepareMessage);
    }

}
