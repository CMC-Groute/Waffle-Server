package com.MakeUs.Waffle.domain.placeLikes;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.place.Place;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "place_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void setPlace(Place place) {
        if (Objects.nonNull(this.place)) {
            this.place.getPlaceLikes()
                    .remove(this);
        }
        this.place = place;
    }

}
