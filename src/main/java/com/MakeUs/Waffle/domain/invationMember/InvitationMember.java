package com.MakeUs.Waffle.domain.invationMember;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitation_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @Builder
    public InvitationMember(Long id, User user, Invitation invitation) {
        this.id = id;
        this.user = user;
        this.invitation = invitation;
    }

    public void setInvitation(Invitation invitation) {
        if (Objects.nonNull(this.invitation)) {
            this.invitation.getInvitationMembers()
                    .remove(this);
        }
        this.invitation = invitation;
    }
}
