package org.devbobo8.serviceclients.model;

import lombok.Data;

@Data
public class Review {
    private String reviewer;
    private String text;
    private Rating rating;
}
