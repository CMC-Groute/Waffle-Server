package com.MakeUs.Waffle.domain.placeLikes.service;

import com.MakeUs.Waffle.domain.place.Place;
import com.MakeUs.Waffle.domain.place.repository.PlaceRepository;
import com.MakeUs.Waffle.domain.placeLikes.PlaceLike;
import com.MakeUs.Waffle.domain.placeLikes.repository.PlaceLikesRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceLikeService {

    private final UserRepository userRepository;
    private final PlaceLikesRepository placeLikesRepository;
    private final PlaceRepository placeRepository;

    public PlaceLikeService(UserRepository userRepository, PlaceLikesRepository placeLikesRepository, PlaceRepository placeRepository) {
        this.userRepository = userRepository;
        this.placeLikesRepository = placeLikesRepository;
        this.placeRepository = placeRepository;
    }


    @Transactional
    public Long savePlaceLike(Long userId, Long placeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));

        if (!isDuplicatePlaceLikes(
                userId,
                place
        )) {
            PlaceLike placeLike = PlaceLike.builder()
                    .userId(userId)
                    .place(place)
                    .build();
             placeLikesRepository.save(placeLike);
        }
        return placeId;
    }

    @Transactional
    public Long deletePlaceLike(Long userId, Long placeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));

        return placeLikesRepository.deleteByUserIdAndPlace(
                userId,
                place
        );
    }

    private boolean isDuplicatePlaceLikes(Long userId, Place place) {
        return placeLikesRepository.existsByUserIdAndPlace(
                userId,
                place
        );
    }
}
