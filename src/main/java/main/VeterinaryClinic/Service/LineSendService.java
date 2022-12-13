package main.VeterinaryClinic.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
class MessageBody{
    @Getter private String type = "text";
    @Getter private String text;
    public MessageBody(String text) {this.text = text;}
}
@Service
public class LineSendService {
    private final String api_url = "https://api.line.me/v2/bot/message/multicast";
    private final String channelAccessToken = "JkFzZ7paIB8BHnQebQMpHVc/mNhE6affsMlGh7dZFVU3UWj8MlIpHKdHqab3xCefY2bWrDOZGzoZH8aQC9RofCageyv52SZWW6MenoPBHZpwe2ITv7Vr5EE4v5slF8Ws9v0yBAr0CsEdOse6ScIIbAdB04t89/1O/w1cDnyilFU=";

    public ResponseEntity<String> sendMessageToClient(List<String> lineIdList, List<String> messagesList) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // application/json
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(channelAccessToken);

        // build body (Important Getter in class)
        Map<String, Object> payload = buildBodyJson(lineIdList, messagesList);
        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        // send POST request
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(api_url, entity, String.class);
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
        return null;
    }

    private Map<String, Object> buildBodyJson(List<String> lineIdList, List<String> messagesList){
        Map<String, Object> mainBody = new LinkedHashMap<>();
        List<MessageBody> messageBodies = new ArrayList<>();
        for (String message : messagesList){
            messageBodies.add(new MessageBody(message));
        }
        mainBody.put("to", lineIdList);
        mainBody.put("messages", messageBodies);
        return mainBody;
    }
}
