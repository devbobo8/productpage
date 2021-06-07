package org.devbobo8.serviceclients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookDetail {
    private int id;
    private String author;
    private int year;
    private String type;
    private int pages;
    private String publisher;
    private String language;
    @JsonProperty("ISBN-10")
    private String isbn10;
    @JsonProperty("ISBN-13")
    private String isbn13;
}
