package org.hoang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastItem {
    @JsonProperty("dt_txt")
    public String dateTime;

    public Main main;
    public Wind wind;
    public List<WeatherDescription> weather;
    public Rain rain;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        public double temp;
        public int humidity;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        public double speed;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDescription {
        public String main;
        public String description;
        public String icon;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rain {
        @JsonProperty("3h")
        public double threeHourVolume;
    }
}
