package cz.itexpert.adventcode2023.day3.model;

import java.util.List;

public class MultiNumberCell  {
    private final Integer value;
    private final List<NumberCell> numberCellList;

    protected MultiNumberCell(List<NumberCell> numberCellList) {
        StringBuilder textValue = new StringBuilder();
        for (NumberCell cell: numberCellList) {
            textValue.append(cell.getValue());
        }
        this.value = Integer.parseInt(textValue.toString());
        this.numberCellList = numberCellList;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isAdjacent() {
        for (NumberCell cell: numberCellList) {
            if (cell.isAdjacentCell()) {
                return true;
            }
        }
        return false;
    }
}
