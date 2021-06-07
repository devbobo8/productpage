package org.devbobo8.serviceclients.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class Ratings {
    private int id;
    private HashMap<String, Integer> ratings;
}
