package com.MakeUs.Waffle.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private int status;
    private T data;

    private ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(200, data);
    }
}
