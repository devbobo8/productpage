package org.devbobo8.serviceclients.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RequestResult<T> {
    private T data;
    private String error = "";
    private int statusCode = HttpStatus.OK.value();
}
