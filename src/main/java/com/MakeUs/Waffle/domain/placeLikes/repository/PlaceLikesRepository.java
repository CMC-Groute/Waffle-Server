package com.MakeUs.Waffle.domain.placeLikes.repository;

import com.MakeUs.Waffle.domain.place.Place;
import com.MakeUs.Waffle.domain.placeLikes.PlaceLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceLikesRepository extends JpaRepository<PlaceLike, Long> {
    boolean existsByUserIdAndPlace(Long userId, Place place);

    Long deleteByUserIdAndPlace(Long userId, Place place);
}

