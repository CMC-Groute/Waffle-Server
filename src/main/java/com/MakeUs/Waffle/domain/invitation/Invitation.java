package com.MakeUs.Waffle.domain.invitation;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    private LocalDateTime date;

    private String comment;

    private String invitationCode;

    private String invitationPlace;

    private Long organizerId;

    @OneToMany(mappedBy = "invitation", orphanRemoval = true)
    private List<InvitationMember> invitationMembers;

    @Builder
    public Invitation(Long id, String title, LocalDateTime date, String comment, String invitationCode, String invitationPlace, Long organizerId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationCode = invitationCode;
        this.invitationPlace = invitationPlace;
        this.organizerId = organizerId;
    }

    public void addInvitationMember(InvitationMember invitationMember) {
        this.invitationMembers.add(invitationMember);
        invitationMember.setInvitation(this);
    }

    public Invitation addMissions(List<InvitationMember> invitationMembers) {
        invitationMembers.forEach(this::addInvitationMember);
        return this;
    }

}
