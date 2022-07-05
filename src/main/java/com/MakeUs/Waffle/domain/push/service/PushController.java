package com.MakeUs.Waffle.domain.push.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("push")
public class PushController {
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    @Value("${fcm.devicetoken}")
    private String FCM_DEVICE_TOKEN;
//
//    @Value("${fcm.apiurl}")
//    private String FCM_API_URL;

    private String FCM_API_URL = "https://fcm.googleapis.com/v1/projects/wapple-188a1/messages:send";


    /**
     * Admin SDK를 이용한 Push 전송
     *
     * @return
     * @throws FirebaseMessagingException
     */
    @PostMapping("/send/token")
    public String sendToToken() throws FirebaseMessagingException {

        Message message = Message.builder()
                .putData("title", "메시지를 보낼거야")
                .putData("content", "너에게")
                .setToken(FCM_DEVICE_TOKEN)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);

        return response;
    }
}
