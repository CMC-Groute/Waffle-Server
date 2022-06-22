package com.MakeUs.Waffle.domain.place.repository;

import com.MakeUs.Waffle.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
