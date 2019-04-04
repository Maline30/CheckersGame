package com.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static com.checkers.CheckersApp.TILE_SIZE;

public class Pawn extends StackPane {

    private PawnType type;

    private double mouseX, mouseY;

    private double oldX, oldY;

    public PawnType getType(){
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Pawn(PawnType type, int x, int y) {
        this.type = type;

        move(x, y );

        Ellipse backGround = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.28 );
        backGround.setFill(Color.BLACK);

        backGround.setStroke(Color.BLACK);
        backGround.setStrokeWidth(TILE_SIZE * 0.02);

        backGround.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        backGround.setTranslateY((TILE_SIZE - TILE_SIZE * 0.28 * 2) / 2 + TILE_SIZE * 0.05);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.28 );
        ellipse.setFill(type == PawnType.RED ? Color.valueOf("#0146B3") : Color.valueOf("#BF0C0C"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.02);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);


        getChildren().addAll(backGround, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;

        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
