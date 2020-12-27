package com.task5.models;

import java.util.ArrayList;
import java.util.Arrays;

public class GameRoom {

    public static final short NOUGHT = -1;
    public static final short CROSS = 1;
    public static final short TIE = 0;
    public static final short NOT_ENDED = -2;

    public String title;
    public String color;
    public ArrayList<String> tags;
    public ArrayList<ArrayList<Short>> field;
    public short size;
    public short nextMove;
    public short winner;
    private short moves_counter;
    private short players_counter;

    public GameRoom(String title, String[] tags, String color, short size) {
        this.title = title;
        this.color = color;
        this.tags = new ArrayList<>(Arrays.asList(tags));
        this.field = new ArrayList<>();
        this.size = size;
        // fill field matrix with zeros
        for(short i = 0; i < size; i++) {
            this.field.add(new ArrayList<>());
            for (short j = 0; j < size; j++) {
                this.field.get(i).add((short)0);
            }
        }
        this.nextMove = CROSS;
        this.moves_counter = 0;
        this.players_counter = 0;
        this.winner = NOT_ENDED;
    }

    public boolean saveMove(Move m) {
        if(field.get(m.y).get(m.x) == 0 && nextMove == m.move) {
            field.get(m.y).set(m.x, m.move);
            nextMove *= -1;
            //moves_counter ++;
            winner = checkWin();
            return true;
        }
        return false;
    }

    public short getCurrentRole() {
        if(players_counter == 0) {
            players_counter ++;
            return CROSS;
        } else if(players_counter == 1){
            players_counter ++;
            return NOUGHT;
        }
        return TIE;
    }

    /**
     * Функция определения победителя
     * @return возвращает -1 при победе NOUGHT, 1 при победе CROSS, 0 -- ничья, -2 -- игра не завершена
     */
    public short checkWin() {
        for(ArrayList<Short> row: field) {
            if(Math.abs(sum(row)) == field.size()) {
                return row.get(0);
            }
        }
        for(ArrayList<Short> col: transpose(field)) {
            if(Math.abs(sum(col)) == field.size()) {
                return col.get(0);
            }
        }
        short diagonal_sum = checkDiagonal(field);
        if(diagonal_sum != 0) {
            return diagonal_sum;
        }
        if(moves_counter == field.size()*field.size()) {
            return TIE;
        }
        return NOT_ENDED;
    }

    public boolean isAvailable(){
        return players_counter < 2 ? true : false;
    }

    private short sum(ArrayList<Short> row) {
        short result = 0;
        for(short el : row) {
            result += el;
        }
        return result;
    }

    /**
     * Функция проверяющая победителя на диагонали
     * @return возвращает -1 при победе NOUGHT, 1 при победе CROSS, 0 -- нет победителя на диагонали
     */
    private short checkDiagonal(ArrayList<ArrayList<Short>> matrix) {
        short left_diagonal_sum = 0;
        short right_diagonal_sum = 0;
        for (int i = 0; i < matrix.size(); i++) {
            left_diagonal_sum += matrix.get(i).get(i);
            right_diagonal_sum += matrix.get(i).get(matrix.size() - 1 - i);
        }
        if(Math.abs(left_diagonal_sum) == matrix.size()) {
            return (short) (left_diagonal_sum/matrix.size());
        }
        if(Math.abs(right_diagonal_sum) == matrix.size()) {
            return (short) (right_diagonal_sum/matrix.size());
        }
        return 0;
    }


    private ArrayList<ArrayList<Short>> transpose(ArrayList<ArrayList<Short>> matrix) {
        ArrayList<ArrayList<Short>> transpose = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            transpose.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                transpose.get(i).add(matrix.get(j).get(i));
            }
        }
        return transpose;
    }
}
