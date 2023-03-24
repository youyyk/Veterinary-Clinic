package main.VeterinaryClinic.Service;

import lombok.Getter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
class MessageBody{
    @Getter private String type = "text";
    @Getter private String text;
    public MessageBody(String text) {this.text = text;}
    @Override
    public String toString() {
        return type + ":" + text;
    }
}
@Service
public class LineSendService {
    private final short MAX_SEND_EACH = 5;
    private final String API_URL = "https://api.line.me/v2/bot/message/multicast";
    private final String CHANNEL_ACCESS_TOKEN = "JkFzZ7paIB8BHnQebQMpHVc/mNhE6affsMlGh7dZFVU3UWj8MlIpHKdHqab3xCefY2bWrDOZGzoZH8aQC9RofCageyv52SZWW6MenoPBHZpwe2ITv7Vr5EE4v5slF8Ws9v0yBAr0CsEdOse6ScIIbAdB04t89/1O/w1cDnyilFU=";

    public ResponseEntity<String> sendMessageToClient(Map<String, String> messagesMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // application/json
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(CHANNEL_ACCESS_TOKEN);
        for (Map<String, Object> payload : splitListToMaxAPIMulticastLine(messagesMap)){
            System.out.println(payload.get("to"));
            System.out.println(payload.get("messages"));
            // build the request
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            // send POST request
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);
                // check response
                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Send Line Message Success");
                    System.out.println(response.getBody());
                } else {
                    System.out.println("Send Line Message Failed");
                    System.out.println(response.getStatusCode());
                    System.out.println(response.getBody());
                }
                return response;
            } catch (Exception e) {
                System.out.println("Send Line Message Failed");
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private List<Map<String, Object>> splitListToMaxAPIMulticastLine(Map<String, String> messagesMap){
        List<Map<String, Object>> fullList = new ArrayList<>();
        short count = 0;
        Map<String, String> temp = new LinkedHashMap<>();
        for (String key : messagesMap.keySet()){
            if (count == MAX_SEND_EACH){
                fullList.add(buildBodyJson(temp));
                temp = new LinkedHashMap<>();
                count = 0;
            }
            temp.put(key, messagesMap.get(key));
            count++;
        }
        // if message % 5 != 0
        if (count != 0){
            fullList.add(buildBodyJson(temp));
        }
        return fullList;
    }

    private Map<String, Object> buildBodyJson(Map<String, String> messagesMap){
        Map<String, Object> mainBody = new LinkedHashMap<>();
        List<String> lineIdList = new ArrayList<>();
        List<MessageBody> messageBodies = new ArrayList<>();
        for (String key : messagesMap.keySet()){
            lineIdList.add(key);
            messageBodies.add(new MessageBody(messagesMap.get(key)));
        }
        mainBody.put("to", lineIdList);
        mainBody.put("messages", messageBodies);
        return mainBody;
    }

}
