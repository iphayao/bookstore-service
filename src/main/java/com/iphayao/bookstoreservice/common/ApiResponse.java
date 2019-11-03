package com.iphayao.bookstoreservice.common;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String status;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> error(T data) {
        DataBuilder builder = error();
        return builder.data(data);
    }

    private static DataBuilder error() {
        return new SimpleDataBuilder("Error");
    }

    public interface DataBuilder {
        <T> ApiResponse<T> data(T data);
    }

    private static class SimpleDataBuilder implements DataBuilder {
        String status;

        SimpleDataBuilder(String status) {
            this.status = status;
        }

        @Override
        public <T> ApiResponse<T> data(T data) {
            return new ApiResponse<>(status, data);
        }
    }

}
