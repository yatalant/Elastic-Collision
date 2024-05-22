package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Button button1;
    private Button button2;

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1000, 500);

        button1 = new Button("Button 1");
        button1.setMinSize(100, 100);
        button1.setLayoutX(100);
        button1.setLayoutY(100);

        button2 = new Button("Button 2");
        button2.setMinSize(100, 100);
        button2.setLayoutX(100);
        button2.setLayoutY(300);

        pane.getChildren().addAll(button1, button2);

        ButtonCoords buttonCoords = new ButtonCoords();

        button1.setOnMousePressed(mouseEvent -> {
            buttonCoords.setX(mouseEvent.getSceneX() - button1.getLayoutX());
            buttonCoords.setY(mouseEvent.getSceneY() - button1.getLayoutY());
        });

        button1.setOnMouseDragged(mouseEvent -> {
            button1.setLayoutX(mouseEvent.getSceneX() - buttonCoords.getX());
            button1.setLayoutY(mouseEvent.getSceneY() - buttonCoords.getY());
            collisionCheck(button1, button2);
        });

        button2.setOnMousePressed(mouseEvent -> {
            buttonCoords.setX(mouseEvent.getSceneX() - button2.getLayoutX());
            buttonCoords.setY(mouseEvent.getSceneY() - button2.getLayoutY());
        });

        button2.setOnMouseDragged(mouseEvent -> {
            button2.setLayoutX(mouseEvent.getSceneX() - buttonCoords.getX());
            button2.setLayoutY(mouseEvent.getSceneY() - buttonCoords.getY());
            collisionCheck(button2, button1);
        });

        stage.setTitle("Buttons");
        stage.setScene(scene);
        stage.show();
    }

    private void collisionCheck(Button button1, Button button2) {
        if (button1.getBoundsInParent().intersects(button2.getBoundsInParent())) {
            // Вычисляем вектор отталкивания
            double dx = button2.getLayoutX() - button1.getLayoutX();
            double dy = button2.getLayoutY() - button1.getLayoutY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double overlap = 100 - distance;

            dx /= distance;
            dy /= distance;
            dx *= overlap;
            dy *= overlap;

            button1.setLayoutX(button1.getLayoutX() - dx);
            button1.setLayoutY(button1.getLayoutY() - dy);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class ButtonCoords {
        private double x;
        private double y;

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}