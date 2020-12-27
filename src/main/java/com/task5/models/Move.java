package com.task5.models;

public class Move {

    public String roomId;
    public short x;
    public short y;
    public short move;

    public Move(String roomId, short x, short y, short move) {
        this.roomId = roomId;
        this.x = x;
        this.y = y;
        this.move = move;
    }
}
