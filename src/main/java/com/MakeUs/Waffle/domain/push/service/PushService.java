package com.MakeUs.Waffle.domain.push.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.push.Push;
import com.MakeUs.Waffle.domain.push.PushType;
import com.MakeUs.Waffle.domain.push.dto.NotificationRequest;
import com.MakeUs.Waffle.domain.push.dto.getPushResponse;
import com.MakeUs.Waffle.domain.push.repository.PushRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class PushService {

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    private final InvitationMemberRepository invitationMemberRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final PushRepository pushRepository;

    @Value("${fcm.apiurl}")
    private String FCM_API_URL;
    ObjectMapper objectMapper = new ObjectMapper();
    public PushService(InvitationMemberRepository invitationMemberRepository, UserRepository userRepository, InvitationRepository invitationRepository, PushRepository pushRepository) {
        this.invitationMemberRepository = invitationMemberRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.pushRepository = pushRepository;
    }

    public void pushLikes(Long id, Long invitationId) {
        invitationMemberRepository.findByUserIdAndInvitationId(id,invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        List<InvitationMember> invitationMembers = invitationMemberRepository.findByInvitationId(invitationId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        String nickName = user.getNickname();
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));
        String invitationTitle = invitation.getTitle();
        String message = nickName + "님이 좋아요 조르기를 시전! " + invitationTitle + "을 위해 가고 싶은 장소에 좋아요를 눌러주세요. ❤️\uD83D\uDC47";
        for(InvitationMember invitationMember : invitationMembers){
            User nowUser = invitationMember.getUser();
            if(!nowUser.getId().equals(id)) {
                if (nowUser.isAgreedAlarm()) {
                    String alarmMessage = makeMessage(nowUser.getDeviceToken(), message,invitationId);
                    send(alarmMessage, invitationId, invitationTitle, nickName, PushType.ALARM_LIKES, nowUser.getId());
                }
            }
        }
    }

    public String makeMessage(String pushToken, String title, Long invitationId) {

        NotificationRequest fcmMessage =
                NotificationRequest.builder()
                        .message(
                                NotificationRequest.Message.builder()
                                        .token(pushToken)
                                        .notification(
                                                NotificationRequest.Notification.builder()
                                                        .title(title)
                                                        //.body(body)
                                                        .image(null)
                                                        .build())
                                        .android(
                                                NotificationRequest.Android.builder()
                                                        .data(
                                                                NotificationRequest.Data.builder()
                                                                        .title(title)
                                                                        .invitationId(invitationId.toString())
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .apns(
                                                NotificationRequest.Apns.builder()
                                                        .payload(
                                                                NotificationRequest.Payload.builder()
                                                                        .aps(NotificationRequest.Aps.builder()
                                                                                .invitationId(invitationId)
                                                                                .sound("default").build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .validate_only(false)
                        .build();


        String fcmMessageString = "";

        try {
            fcmMessageString = objectMapper.writeValueAsString(fcmMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return fcmMessageString;
    }

    /**
     * HTTP Protocol을 이용한 Push 전송
     */
    public void send(String alarmMessage,Long invitationId, String invitationTitle,String nickName, PushType pushType, Long userId) {
        // 2. create token & send push
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url(FCM_API_URL)
                    .post(RequestBody.create( MediaType.parse("application/json"),alarmMessage))
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            System.out.println(alarmMessage);
            System.out.println("### response str : " + response.toString());
            System.out.println("### response result : " + response.isSuccessful());
            System.out.println("### response message : " + response.message().toString());
            if(response.isSuccessful()){
                Push push = Push.builder()
                        .invitationTitle(invitationTitle)
                        .pushType(pushType)
                        .nickName(nickName)
                        .invitationId(invitationId)
                        .userId(userId)
                        .build();

                pushRepository.save(push);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() throws IOException {
        Resource resource = new ClassPathResource("keystore/service-account.json");
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(resource.getInputStream())
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

    public List<getPushResponse> getAlarm(Long id) {
        List<Push> pushes = pushRepository.getByUserId(id);
        return pushes.stream().map(Push::toGetPushResponse).collect(toList());
    }
}
