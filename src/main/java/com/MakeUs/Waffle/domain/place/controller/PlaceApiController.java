package com.MakeUs.Waffle.domain.place.controller;

import com.MakeUs.Waffle.domain.common.KakaoRestApiHelper;
import com.MakeUs.Waffle.domain.place.dto.DecidedPlaceDetailResponse;
import com.MakeUs.Waffle.domain.place.dto.SearchPlaceRequest;
import com.MakeUs.Waffle.domain.place.dto.UpdateDecidePlaceRequest;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PlaceApiController {

    @Resource
    private KakaoRestApiHelper kakaoRestApiHelper;

    @GetMapping(value="/place/search")
    public ResponseEntity searchPlaceByKeyword(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody SearchPlaceRequest searchPlaceRequest) throws Exception {
        ResponseEntity re = kakaoRestApiHelper.getSearchPlaceByKeyword(searchPlaceRequest);
        return re;
    }
}
