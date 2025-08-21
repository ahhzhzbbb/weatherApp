package org.hoang.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class mainViewController {
    @FXML
    private Text precipitation;

    @FXML
    private ImageView imageBG;

    @FXML
    private Pane pane, pane1, pane2, pane3, pane4;

    @FXML
    private Button locationButton;

    @FXML   // thêm annotation này để rõ ràng hơn
    public void initialize() {
        Rectangle clip = new Rectangle(375, 450);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        imageBG.setClip(clip);

        HoverEffect.add(pane);
        HoverEffect.add(pane1);
        HoverEffect.add(pane2);
        HoverEffect.add(pane3);
        HoverEffect.add(pane4);
        HoverEffect.add(locationButton);
    }

    @FXML
    public void setText(String _text) {
        this.precipitation.setText(_text);
    }
}
