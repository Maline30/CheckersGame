package com.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tiles extends Rectangle {

    private Pawn pawn;

    public boolean hasPawn() {
        return pawn != null;
    }

    public Pawn getPawn(){
        return pawn;
    }

    public void setPawn(Pawn pawn){
        this.pawn = pawn;
    }

    public Tiles(boolean light, int x, int y){
        setWidth(CheckersApp.TILE_SIZE);
        setHeight(CheckersApp.TILE_SIZE);

        relocate(x * CheckersApp.TILE_SIZE, y * CheckersApp.TILE_SIZE);

        setFill(light ? Color.valueOf("#484D55") : Color.valueOf("637F8E"));

    }
}
