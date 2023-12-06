package cz.itexpert.adventcode2023.day3.model;

import java.util.*;

public class Matrix {

    Vector<Vector<Cell>> matrix = new Vector<>();

    public Cell getCell(int row, int col) {
        try {
            return matrix.get(row - 1).get(col - 1);
        } catch (Exception e) {
            return null;
        }
    }


    public void addRow(Vector<Cell> rowOfCells) {
        matrix.add(rowOfCells);
    }

    public int getSumOfAdjacentNumbers() {
        int totalSum = 0;
        Set<MultiNumberCell> validNumbers = new HashSet<>();
        for (Vector<Cell> rowOfCells : matrix) {
           for (Cell cell: rowOfCells) {
               if (cell instanceof NumberCell) {
                   if (((NumberCell) cell).getMultiNumberCell().isAdjacent()) {
                       validNumbers.add(((NumberCell) cell).getMultiNumberCell());
                   }
               }
           }
        }
        Iterator<MultiNumberCell> i = validNumbers.iterator();
        System.out.println("Valid numbers: ");
        MultiNumberCell cell;
        while (i.hasNext()) {
            cell = i.next();
            totalSum += cell.getValue();
            System.out.print(cell.getValue());
            if (i.hasNext()) {
                System.out.print(", ");
            }
        }
        return totalSum;
    }

    public void buildCloseFriendsAndIdentifyNumbers() {
        for (Vector<Cell> rowOfCells : matrix) {
            for (Cell cell : rowOfCells) {
                cell.addCloseFriend(getCell(cell.row - 1, cell.col - 1));
                cell.addCloseFriend(getCell(cell.row - 1, cell.col));
                cell.addCloseFriend(getCell(cell.row - 1, cell.col + 1));

                cell.addCloseFriend(getCell(cell.row, cell.col - 1));
                cell.addCloseFriend(getCell(cell.row, cell.col + 1));

                cell.addCloseFriend(getCell(cell.row + 1, cell.col - 1));
                cell.addCloseFriend(getCell(cell.row + 1, cell.col));
                cell.addCloseFriend(getCell(cell.row + 1, cell.col + 1));
            }
        }
        List<NumberCell> wholeNumbers = new ArrayList<>();
        for (Vector<Cell> rowOfCells : matrix) {
            wholeNumbers.clear();
            for (Cell cell : rowOfCells) {
                if (cell instanceof NumberCell) {
                    wholeNumbers.add((NumberCell) cell);
                } else {
                    voidRegisterWholeNumber(wholeNumbers);
                    wholeNumbers.clear();
                }
            }
            if (!wholeNumbers.isEmpty()) {
                voidRegisterWholeNumber(wholeNumbers);
            }
        }

    }

    private void voidRegisterWholeNumber(List<NumberCell> numberCells) {
        if (!numberCells.isEmpty()) {
            MultiNumberCell multiNumberCell = new MultiNumberCell(new ArrayList<>(numberCells));
            numberCells.forEach(cell -> cell.setMultiNumberCell(multiNumberCell));
        }
    }

    public int getGearsSum() {
        int total = 0;
        for (Vector<Cell> rowOfCells : matrix) {
            for (Cell cell : rowOfCells) {
                if (cell instanceof AdjacentCell) {
                    total += ((AdjacentCell) cell).getGearRatio();
                }
            }
        }
        return total;
    }
}
