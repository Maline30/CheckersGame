package com.checkers;

public enum PawnType  {

    RED(1 ), BLUE(-1);

    final int moveDirection;

    PawnType(int moveDirection) {
        this.moveDirection = moveDirection;
    }

}
