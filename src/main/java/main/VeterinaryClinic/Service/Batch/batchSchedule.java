package main.VeterinaryClinic.Service.Batch;

import main.VeterinaryClinic.Model.Account.Account;
import main.VeterinaryClinic.Model.Appointment;
import main.VeterinaryClinic.Model.Bill.Bill;
import main.VeterinaryClinic.Model.Pet;
import main.VeterinaryClinic.Model.WareHouse;
import main.VeterinaryClinic.Service.*;
import main.VeterinaryClinic.Service.Account.AccountService;
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
    @Autowired
    MainBillService mainBillService;
//    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 9 * * *") // Run every day at 9 A.M.
    public void runTask() {
        Date tomorrow = GlobalService.getDefaultTodayDateZeroTime(1);
        List<Appointment> appointmentList = appointmentService.findByDate(tomorrow);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(tomorrow);
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

//        @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 9 * * *") // Run every day at 9 A.M.
    public void warehouseAlert() {
        Date today = GlobalService.getCurrentTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(today);
        Map<String, String> prepareMessage = new HashMap<>();
//        List<Account> accountList = accountService
        List<WareHouse> wareHouses = wareHouseService.findBySoftDeletedOrderByExpiredDateAsc(false);
        int expiredMedCount = 0;
        int almostMedCount = 0;
        int expiredToolCount = 0;
        int almostToolCount = 0;
        for (WareHouse wh : wareHouses) {
            short expiredType = wh.isExpired();
            if (expiredType == -1 && wh.getType().trim().equals("medicine")){
                expiredMedCount++;
            } else if (expiredType == 1 && wh.getType().trim().equals("medicine")) {
                almostMedCount++;
            }
            if (expiredType == -1 && wh.getType().trim().equals("tool")){
                expiredToolCount++;
            } else if (expiredType == 1 && wh.getType().trim().equals("tool")) {
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


    @Scheduled(cron = "0 0 6 * * *") // Run every day at 6 A.M.
    public void clearBillNotQueue() {
        // Delete bill create from receiveTreatment but not getIn queue
        List<Bill> bills = mainBillService.findByQueueStatusIsFalse();
        for (Bill bill : bills){
            mainBillService.deleteBillByBillID(bill.getBillID());
        }
    }
}
