package org.hoang.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.hoang.model.City;
import org.hoang.model.ForecastItem;
import org.hoang.model.ForecastResponse;
import org.hoang.model.WeatherAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainViewController {
    @FXML
    private ImageView imageBG;

    @FXML
    private Pane pane, pane1, pane2, pane3, pane4;

    @FXML
    private Text precipitation;

    @FXML
    private Text humidity;

    @FXML
    private Text wind;

    @FXML
    private TextField cityTextField;

    @FXML
    private Text cityText;

    @FXML
    private ContextMenu suggestSearchMenu;

    @FXML
    private Text tempOnpane;

    @FXML
    private ImageView iconPane1;

    //ky thuat Debounce giam tan suat goi API, giam giat lag -> dung trong suggestSearch cho do goi nhieu
    private final PauseTransition pause = new PauseTransition(Duration.millis(600));

    private List<ForecastItem> weathers;

    @FXML   // thêm annotation này để rõ ràng hơn
    public void initialize() throws IOException, InterruptedException {
        Rectangle clip = new Rectangle(375, 450);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        imageBG.setClip(clip);

        HoverEffect.add(pane);
        HoverEffect.add(pane1);
        HoverEffect.add(pane2);
        HoverEffect.add(pane3);
        HoverEffect.add(pane4);
        //HoverEffect.add(locationButton);

        cityTextField.textProperty().addListener((obs, oldText, newText) -> {
            pause.setOnFinished(e -> {
                try {
                    nameSuggest(); // gọi method suggest
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            pause.playFromStart(); // reset timer mỗi lần gõ
        });

        weatherSceneController();
    }

    @FXML
    public void weatherSceneController() throws IOException, InterruptedException {
        pane.requestFocus();
        WeatherAPI api = new WeatherAPI();
        String nameOfCity = (cityTextField.getText() != "") ? cityTextField.getText() : "Hanoi, VN";
        String weatherJson = api.callAPI(nameOfCity);
        ObjectMapper mapper = new ObjectMapper();
        ForecastResponse response = mapper.readValue(weatherJson, ForecastResponse.class);
        weathers = response.list;
        City city = response.city;


        List<Pane> panes = new ArrayList<>();
        panes.add(pane1);
        panes.add(pane2);
        panes.add(pane3);
        panes.add(pane4);


        for(int i = 0; i <= 24; i += 8)
        {
            ImageView iconView = (ImageView) panes.get(i / 8).getChildren().get(0);
            Text tempText = (Text) panes.get(i / 8).getChildren().get(1);

            iconView.setImage(
                    new Image("http://openweathermap.org/img/wn/"
                            + weathers.get(i).weather.get(0).icon
                            + "@2x.png")
                    );

            tempText.setText(weathers.get(i).main.temp + " ºC");
        }

        cityText.setText(city.name + ", " + city.country);
        tempOnpane.setText((int)weathers.get(0).main.temp + " ºC");
    }

    @FXML
    public void forecastPaneController(MouseEvent event)
    {
        Pane clicked = (Pane) event.getSource();
        int index = 0;
        if(clicked == pane2) index = 8;
        else if(clicked == pane3) index = 16;
        else if(clicked == pane4) index = 24;

        ForecastItem thisWeather = weathers.get(index);

        Double rainText = 0.0; //default neu troi khong mua

        if(thisWeather.rain != null)
        {
            rainText = thisWeather.rain.threeHourVolume;
        }

        tempOnpane.setText((int)thisWeather.main.temp + " ºC");

        precipitation.setText(rainText + " mm");
        humidity.setText(thisWeather.main.humidity + " %");
        wind.setText(thisWeather.wind.speed + " m/s");
        tempOnpane.setText((int)thisWeather.main.temp + " ºC");
    }


    @FXML
    public String getCityTextNow()
    {
        return cityTextField.getText().toString();
    }
    @FXML
    public void nameSuggest() throws IOException, InterruptedException {
        String input = getCityTextNow().trim();

        if (input.isEmpty()) {
            suggestSearchMenu.hide();
            return;
        }

        WeatherAPI api = new WeatherAPI();
        String json = api.suggestName(input);

        ObjectMapper mapper = new ObjectMapper();
        List<City> cities = mapper.readValue(json, new TypeReference<List<City>>() {});

        // Lọc ra list các name
        List<String> names = new ArrayList<>();
        for(City x : cities)
        {
            names.add(x.name + ", " + x.country);
        }

        if (!names.isEmpty()) {
            suggestSearchMenu.getItems().clear();
            for (String name : names) {
                Label lbl = new Label(name);
                lbl.setStyle("-fx-padding: 5;");
                CustomMenuItem item = new CustomMenuItem(lbl, true);
                item.setOnAction(e -> cityTextField.setText(name));
                suggestSearchMenu.getItems().add(item);
            }
            suggestSearchMenu.show(cityTextField, Side.BOTTOM, 0, 0);
        } else {
            suggestSearchMenu.hide();
        }
    }

}
