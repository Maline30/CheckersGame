package com.checkers;

public class MoveResult {

    private MoveType type;
    private Pawn pawn;

    public MoveType getType() {
        return type;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public MoveResult(MoveType type, Pawn pawn){
        this.type = type;
        this.pawn = pawn;
    }

    public MoveResult(MoveType type){
        this(type, null);
    }


}
