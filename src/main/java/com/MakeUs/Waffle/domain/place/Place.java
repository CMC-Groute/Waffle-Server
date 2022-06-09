package com.MakeUs.Waffle.domain.place;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.placeLikes.PlaceLike;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String comment;

    private String link;

    private Boolean isDeleted;

    private Long seq;

    //좌표
    //도로명주소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @OneToMany(mappedBy = "place", orphanRemoval = true)
    private List<PlaceLike> placeLikes = new ArrayList<>();

    public void addPlaceLike(PlaceLike placeLike) {
        this.placeLikes.add(placeLike);
        placeLike.setPlace(this);
    }

    public Place addPlaceLikes(List<PlaceLike> placeLikes) {
        placeLikes.forEach(this::addPlaceLike);
        return this;
    }
}
