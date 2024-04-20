package com.stellarisapi.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RequestAdapterDTO {

    private Map<String,String> headers;

    private String body;

    public RequestAdapterDTO() {
    }

    public RequestAdapterDTO(Map<String, String> headers) {
        this.headers = headers;
    }

    public RequestAdapterDTO(String body) {
        this.body = body;
    }

    public RequestAdapterDTO(Map<String, String> headers, String body) {
        this.headers = headers;
        this.body = body;
    }
}
