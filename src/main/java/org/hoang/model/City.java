package org.hoang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    public String name;
    public String country;
    public String state;
}

