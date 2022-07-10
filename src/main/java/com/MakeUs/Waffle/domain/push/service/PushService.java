package com.MakeUs.Waffle.domain.push.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.DuplicatedResourceException;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PushService {

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    private final InvitationMemberRepository invitationMemberRepository;

    @Value("${fcm.apiurl}")
    private String FCM_API_URL;

    public PushService(InvitationMemberRepository invitationMemberRepository) {
        this.invitationMemberRepository = invitationMemberRepository;
    }

    public void pushLikes(Long id, Long invitationId) {
        invitationMemberRepository.findByUserIdAndInvitationId(id,invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        List<InvitationMember> invitationMembers = invitationMemberRepository.findByInvitationId(invitationId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));

        for(InvitationMember invitationMember : invitationMembers){
            if(invitationMember.getUser().getId() != id){
                send(invitationMember.getUser().getDeviceToken());
            }
        }
    }

    /**
     * HTTP Protocol을 이용한 Push 전송
     * @return
     */
    public void send(String deviceToken) {
        //com.google.gson 사용
        System.out.println("before sends");
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("token", deviceToken);
        JsonObject android = new JsonObject();
        JsonObject data = new JsonObject();
        data.addProperty("body", "\uD83D\uDE03바디V1");
        data.addProperty("title", "\uD83D\uDE03타이틀V1");
        android.add("data",data);

        JsonObject apns = new JsonObject();
        JsonObject payload = new JsonObject();
        JsonObject aps = new JsonObject();

        aps.addProperty("body", "\uD83D\uDE03바디V1");
        aps.addProperty("title", "\uD83D\uDE03타이틀V1");
        payload.add("aps",aps);
        apns.add("payload",payload);

        jsonObj.add("android", android);
        jsonObj.add("apns", apns);

        JsonObject message = new JsonObject();
        message.add("message", jsonObj);

        // 2. create token & send push
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url(FCM_API_URL)
                    .post(RequestBody.create( MediaType.parse("application/json"),message.toString()))
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            System.out.println("### response str : " + response.toString());
            System.out.println("### response result : " + response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() throws IOException {
        ClassPathResource resource = new ClassPathResource("keystore/service-account.json");
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream(resource.getFile()))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

}
