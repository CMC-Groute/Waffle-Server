package com.MakeUs.Waffle.configures;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.invitation.service.InvitationService;
import com.MakeUs.Waffle.domain.push.PushType;
import com.MakeUs.Waffle.domain.push.repository.PushRepository;
import com.MakeUs.Waffle.domain.push.service.PushService;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ScheduledConfig {

    private final InvitationRepository invitationRepository;
    private final PushService pushService;
    private final PushRepository pushRepository;

    @Scheduled(cron = "0 1 0 * *  ?")
    public void updateInvitationExpiration() {
        invitationRepository.updateExpire(true, LocalDate.now().minusDays(1));
    }

    @Scheduled(cron = "0 8 0 * *  ?")
    public void updatePushExpiration() {
        pushRepository.deleteExpire( LocalDate.now().minusDays(30));
    }
    //등록 후 7일 이후에도 일시/위치가 미정인 경우
    @Scheduled(cron = "0 2 0 * *  ?")
    public void alertPlaceIsNull() {
        LocalDate comparedDate = LocalDate.now().minusDays(7);
        List<Invitation> invitations = invitationRepository.getByCreatedAt(comparedDate.toString());
        for (Invitation invitation : invitations) {
            Long invitationId = invitation.getId();
            String invitationTitle = invitation.getTitle();
            String message = invitationTitle + " 약속의 만나는 날짜 또는 위치가 아직 정해지지 않았네요. 어서 정해봐요~ \uD83E\uDD17";

            List<InvitationMember> invitationMembers = invitation.getInvitationMembers();
            for (InvitationMember invitationMember : invitationMembers) {
                User nowUser = invitationMember.getUser();
                if (nowUser.isAgreedAlarm()) {
                    String alarmMessage = pushService.makeMessage(nowUser.getDeviceToken(), message,invitationId);
                    pushService.send(alarmMessage, invitationId, invitationTitle, null, PushType.ALARM_NOT_DECIDE, nowUser.getId(),invitation.getInvitationImageCategory());
                }
            }
        }
    }

    //일정 하루 전 오후 3시
    @Scheduled(cron = "0 0 15 * *  ?")
    public void alertBeforeInvitationDate() {
        LocalDate comparedDate = LocalDate.now().minusDays(1);
        List<Invitation> invitations = invitationRepository.getByDate(comparedDate);
        for (Invitation invitation : invitations) {
            Long invitationId = invitation.getId();
            String invitationTitle = invitation.getTitle();

            List<InvitationMember> invitationMembers = invitation.getInvitationMembers();
            for (InvitationMember invitationMember : invitationMembers) {
                String nickName = invitationMember.getUser().getNickname();
                String message = nickName + "내일 " + invitationTitle + " 잊지 않았죠? \uD83D\uDE1D";
                User nowUser = invitationMember.getUser();
                if (nowUser.isAgreedAlarm()) {
                    String alarmMessage = pushService.makeMessage(nowUser.getDeviceToken(), message,invitationId);
                    pushService.send(alarmMessage, invitationId, invitationTitle, nickName, PushType.ALARM_BEFORE_DAY, nowUser.getId(),invitation.getInvitationImageCategory());                }
            }
        }
    }

}
