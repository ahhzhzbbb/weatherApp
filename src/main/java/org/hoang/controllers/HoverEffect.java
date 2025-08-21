package org.hoang.controllers;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class HoverEffect {
    public static void add(Node node) {
        node.setOnMouseEntered(e -> apply(node, 1.05));
        node.setOnMouseExited(e -> apply(node, 1.0));
    }

    private static void apply(Node node, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(250), node);
        st.setToX(scale);
        st.setToY(scale);
        st.setInterpolator(Interpolator.EASE_BOTH);
        st.stop();
        st.play();
    }
}
