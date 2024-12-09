package cz.itexpert.adventcode2023.day3.model;

import java.util.*;
public class AdjacentCell extends Cell{

    protected AdjacentCell(String value, int x, int y) {
        super(value, x, y);
    }

    public Set<MultiNumberCell> getNumberNeighbours() {
        Set<MultiNumberCell> numberCells = new HashSet<>();
        for (Cell cell: closeFriends) {
            if (cell instanceof NumberCell) {
                numberCells.add(((NumberCell) cell).getMultiNumberCell());
            }
        }
        return numberCells;
    }

    public int getGearRatio() {
        Set<MultiNumberCell> numberCells = getNumberNeighbours();
        if (numberCells.size() == 2) {
            Iterator<MultiNumberCell> i = numberCells.iterator();
            return i.next().getValue() * i.next().getValue();
        } else {
            return 0;
        }
    }
}
