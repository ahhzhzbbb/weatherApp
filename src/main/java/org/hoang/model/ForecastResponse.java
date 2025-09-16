package org.hoang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//class nay chi co tac dung la boc tach lop vo cua json de lay List(xem API cu the de biet)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    public City city;
    public List<ForecastItem> list;
}
