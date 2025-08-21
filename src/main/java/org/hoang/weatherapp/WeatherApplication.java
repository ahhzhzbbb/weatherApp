package org.hoang.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WeatherApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(WeatherApplication.class.getResource("/org/hoang/weatherapp/view/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 900, 500);
        scene.getStylesheets().add(getClass().getResource("/org/hoang/weatherapp/styles/style1.css").toExternalForm());
        stage.setTitle("WeatherApp");
        stage.setScene(scene);
        stage.show();
    }
}
