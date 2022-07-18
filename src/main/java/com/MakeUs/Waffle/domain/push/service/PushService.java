package com.MakeUs.Waffle.domain.push.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.InvitationImageCategory;
import com.MakeUs.Waffle.domain.push.Push;
import com.MakeUs.Waffle.domain.push.PushType;
import com.MakeUs.Waffle.domain.push.dto.NotificationRequest;
import com.MakeUs.Waffle.domain.push.dto.getPushResponse;
import com.MakeUs.Waffle.domain.push.repository.PushRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import com.MakeUs.Waffle.error.exception.OpenApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@Slf4j
public class PushService {

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    private final InvitationMemberRepository invitationMemberRepository;
    private final PushRepository pushRepository;

    @Value("${fcm.apiurl}")
    private String FCM_API_URL;
    ObjectMapper objectMapper = new ObjectMapper();

    public PushService(InvitationMemberRepository invitationMemberRepository,PushRepository pushRepository) {
        this.invitationMemberRepository = invitationMemberRepository;
        this.pushRepository = pushRepository;
    }

    public void pushLikes(Long id, Long invitationId) {
        //InvitationMember invitationMember1 = invitationMemberRepository.findByUserIdAndInvitationId(id, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        List<InvitationMember> invitationMembers = invitationMemberRepository.findByInvitationId(invitationId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));
        InvitationMember first = invitationMembers.stream().filter(invitationMember -> invitationMember.getUser().getId().equals(id)).findFirst().orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));;

        User user = first.getUser();
        String nickName = user.getNickname();
        Invitation invitation = first.getInvitation();
        String invitationTitle = invitation.getTitle();
        String message = nickName + "님이 좋아요 조르기를 시전! " + invitationTitle + "을 위해 가고 싶은 장소에 좋아요를 눌러주세요. ❤️\uD83D\uDC47";
        for (InvitationMember invitationMember : invitationMembers) {
            User nowUser = invitationMember.getUser();
            if (!nowUser.getId().equals(id)) {
                if (nowUser.isAgreedAlarm()) {
                    String alarmMessage = makeMessage(nowUser.getDeviceToken(), message, invitationId);
                    send(alarmMessage, invitationId, invitationTitle, nickName, PushType.ALARM_LIKES, nowUser.getId(), invitation.getInvitationImageCategory());
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
    public void send(String alarmMessage, Long invitationId, String invitationTitle, String nickName, PushType pushType, Long userId, InvitationImageCategory invitationImageCategory) {
        // 2. create token & send push
        Response response;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url(FCM_API_URL)
                    .post(RequestBody.create(MediaType.parse("application/json"),alarmMessage))
                    .build();
            response = okHttpClient.newCall(request).execute();

        }catch (IOException e){
            throw new OpenApiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("### response result: {}",response.isSuccessful());
        log.info("### response message: {}",response.message());

        if (response.isSuccessful()) {
            Push push = Push.builder()
                    .invitationImageCategory(invitationImageCategory)
                    .invitationTitle(invitationTitle)
                    .pushType(pushType)
                    .nickName(nickName)
                    .invitationId(invitationId)
                    .userId(userId)
                    .build();

            pushRepository.save(push);
        } else {
            throw new OpenApiException(ErrorCode.INTERNAL_SERVER_ERROR);
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

    public Long updateIsRead(Long userId, Long pushId) {
        Push push = pushRepository.findById(pushId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PUSH));
        push.updateIsRead(true);
        return pushId;
    }
}
