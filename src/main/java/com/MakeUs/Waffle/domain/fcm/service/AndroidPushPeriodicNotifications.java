package com.MakeUs.Waffle.domain.fcm.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.service.InvitationService;
import com.MakeUs.Waffle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public class AndroidPushPeriodicNotifications {
    public final UserService userService;
    private final InvitationMemberRepository invitationMemberRepository;

    public String PeriodicNotificationJson(Long invitationId) throws JSONException {

        List<InvitationMember> invitationMembers = invitationMemberRepository.getByInvitationId(invitationId);

        List<String> likePushUserDeviceTokenList = new ArrayList<>();    // 좋아요 조르기 알림가는 유저의 DeviceToken List

        LocalDate today = LocalDate.now();
        LocalDateTime afterDate = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0, 0);
        LocalDateTime beforeDate = afterDate.plusDays(1);

        //초대장에 초대되어있는 멤버들의 deviceToken을 List에 저장
        for (InvitationMember invitationMember : invitationMembers) {
            if (invitationMember.getUser().getDeviceToken() != null && invitationMember.getUser().getDeviceToken() != "") {
                        System.out.println("디바이스 토큰 add");
                        likePushUserDeviceTokenList.add(invitationMember.getUser().getDeviceToken());
                        break;
                    }
        }

        if(likePushUserDeviceTokenList.isEmpty())
            return null;
        else{
            return returnPushMessage(likePushUserDeviceTokenList);
        }


    }

    //pushMessage 생성하여 return
    private String returnPushMessage(List<String> likePushUserDeviceTokenList) {
        JSONObject body = new JSONObject();
        JSONArray array = new JSONArray();
        for (String token : likePushUserDeviceTokenList) {
            array.put(token);
        }

        body.put("registration_ids", array);

        JSONObject notification = new JSONObject();

        try {
            String pushMessage = "보관하신 걱정이 만료되었어요!";
            String titleMessage = "걱정 후기를 남겨주세요";

            pushMessage = URLEncoder.encode(pushMessage, "UTF-8");
            titleMessage = URLEncoder.encode(titleMessage, "UTF-8");
            notification.put("title", titleMessage);
            notification.put("body", pushMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        body.put("notification", notification);

        System.out.println(body.toString());

        return body.toString();
    }
}