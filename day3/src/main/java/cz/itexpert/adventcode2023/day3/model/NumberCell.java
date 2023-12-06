package cz.itexpert.adventcode2023.day3.model;

public class NumberCell extends Cell {

    private MultiNumberCell multiNumberCell;

    public NumberCell(String value, int x, int y) {
        super(value,  x,y);
    }

    public MultiNumberCell getMultiNumberCell() {
        return multiNumberCell;
    }

    public void setMultiNumberCell(MultiNumberCell multiNumberCell) {
        this.multiNumberCell = multiNumberCell;
    }
}
