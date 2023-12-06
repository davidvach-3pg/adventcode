package cz.itexpert.adventcode2023.day3.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Cell {

    private final String value;
    protected int row;
    protected int col;

    protected Set<Cell> closeFriends = new HashSet<>();

    public void addCloseFriend(Cell cell) {
        if (cell != null) {
            closeFriends.add(cell);
        }
    }

    public boolean isAdjacentCell() {
        for (Cell cell : closeFriends) {
            if (cell instanceof AdjacentCell) {
                return true;
            }

        }
        return false;
    }

    protected Cell(String value, int row, int cell) {
        this.row = row;
        this.col = cell;
        this.value = value;
    }

    public static Cell resolveCell(Character character, int row, int cell) {

        if (Character.isDigit(character)) {
            return new NumberCell(character.toString(), row, cell);
        } else if (character.toString().equalsIgnoreCase(".")) {
            return new EmptyCell(row, cell);
        } else {
            return new AdjacentCell(character.toString(), row, cell);
        }
    }


    public String getValue() {
        return value;
    }

}