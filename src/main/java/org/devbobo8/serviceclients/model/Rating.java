package org.devbobo8.serviceclients.model;

import lombok.Data;

@Data
public class Rating {
    private int stars;
    private String color;
    private String error;
}
