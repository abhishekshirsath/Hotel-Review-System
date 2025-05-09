package com.example.user.payload;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    private String message;
    private boolean success;
    private int status;

    private ApiResponse(ApiResponseBuilder builder){
        this.message = builder.message;
        this.success = builder.success;
        this.status = builder.status;
    }

    // Getter methods to ensure proper serialization
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public static class ApiResponseBuilder{
        private String message;
        private boolean success;
        private int status;

        public ApiResponseBuilder message(String message){
            this.message = message;
            return this;
        }

        public ApiResponseBuilder success(boolean success){
            this.success = success;
            return this;
        }

        public ApiResponseBuilder status(HttpStatus status){
            this.status = status.value();
            return this;
        }

        public ApiResponse build(){
            return new ApiResponse(this);
        }
    }

}
