package cz.itexpert.adventcode2023.day3;

import cz.itexpert.adventcode2023.day3.model.Cell;
import cz.itexpert.adventcode2023.day3.model.Matrix;

import java.io.InputStream;
import java.util.*;

public class InputFileParser {


    public Matrix parseFile(InputStream inputStream) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(inputStream));
        Matrix matrix = new Matrix();
        int rowNo = 0;
        while (scanner.hasNextLine()) {
            matrix.addRow(parseLine(scanner.nextLine(), ++rowNo));
        }
        return matrix;
    }

    public Vector<Cell> parseLine(String line, int rowNo) {

        Vector<Cell> rowOfCells = new Vector<>();
        int columnNo = 0;
        for (Character ch: line.toCharArray()) {
            rowOfCells.add(Cell.resolveCell(ch, rowNo, ++columnNo));
        }
        return rowOfCells;

    }
}
