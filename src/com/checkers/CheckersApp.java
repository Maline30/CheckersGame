package com.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersApp extends Application {

    public static final  int TILE_SIZE = 100;
    public static final  int WIDTH = 8;
    public static final  int HEIGHT = 8;

    private Tiles[][] board = new Tiles[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pawnGroup = new Group();

    private Parent createContent() {
        Pane window = new Pane();
        window.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        window.getChildren().addAll(tileGroup, pawnGroup);

        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                Tiles tiles = new Tiles((x + y) % 2 == 0, x, y);

                board[x][y] = tiles;

                tileGroup.getChildren().add(tiles);

                Pawn pawn = null;

                if(y <= 2 && ( x + y ) % 2 != 0) {
                    pawn = makePawn(PawnType.RED, x , y);
                }

                if(y >= 5 && ( x + y ) % 2 != 0) {
                    pawn = makePawn(PawnType.BLUE, x , y);
                }

                if (pawn != null) {
                    tiles.setPawn(pawn);
                    pawnGroup.getChildren().add(pawn);
                }
            }
        }

        return window;
    }

    private MoveResult tryMove (Pawn pawn, int newX, int newY){
        if(board[newX][newY].hasPawn() || (newX + newY) % 2 == 0) {
            return  new MoveResult(MoveType.NONE);
        }
            int x0 = toBoard(pawn.getOldX());
            int y0 = toBoard(pawn.getOldY());

            if (Math.abs(newX - x0) == 1 && newY - y0 == pawn.getType().moveDirection) {

                return new MoveResult(MoveType.NORMAL);

            } else if(Math.abs(newX - x0) == 2 && newY - y0 == pawn.getType().moveDirection * 2) {

                int x1 = x0 + (newX -x0) / 2;
                int y1 = y0 + (newX -y0) / 2;

                if (board[x1][y1].hasPawn() && board[x1][y1].getPawn().getType() != pawn.getType()) {
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPawn());
                }
            }
            return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return  (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checker Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pawn makePawn(PawnType type, int x, int y){
        Pawn pawn = new Pawn(type, x, y);

        pawn.setOnMouseReleased(e -> {
            int newX = toBoard(pawn.getLayoutX());
            int newY = toBoard(pawn.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {

                result = new MoveResult(MoveType.NONE);
            } else {

                 result = tryMove(pawn, newX, newY);
            }

            int x0 = toBoard(pawn.getOldX());
            int y0 = toBoard(pawn.getOldY());

            switch (result.getType()) {
                case NONE:
                    pawn.abortMove();
                    break;
                case NORMAL:
                    pawn.move(newX, newY);
                    board[x0][y0].setPawn(null);
                    board[newX][newY].setPawn(pawn);
                    break;
                case KILL:
                    pawn.move(newX, newY);
                    board[x0][y0].setPawn(null);
                    board[newX][newY].setPawn(pawn);

                    Pawn otherPawn = result.getPawn();
                    board[toBoard(otherPawn.getOldX())][toBoard(otherPawn.getOldY())].setPawn(null);
                    pawnGroup.getChildren().remove(otherPawn);
                    break;
            }
        });

        return pawn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
