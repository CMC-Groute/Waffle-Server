package com.MakeUs.Waffle.configures;

import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.invitation.service.InvitationService;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class ScheduledConfig {

    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final InvitationService invitationService;

    @Scheduled(cron = "0 1 0 * *  ?")
    public void updateInvitationExpiration() {
        invitationRepository.updateExpire(true,LocalDate.now().minusDays(1));
    }
}
