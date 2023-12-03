package cz.itexpert.adventcode2023.day3;

import cz.itexpert.adventcode2023.day3.model.Matrix;

public class RunnerDay3 {

    public static void main(String[] args) {
        InputFileParser inputFileParser = new InputFileParser();
        Matrix matrix = inputFileParser.parseFile(RunnerDay3.class.getResourceAsStream("/day3.txt"));
        matrix.buildCloseFriendsAndIdentifyNumbers();


        System.out.println("\nadjacent sum (525911): "  +  matrix.getSumOfAdjacentNumbers());
        System.out.println("gear sum (75805607): "  + matrix.getGearsSum());
    }

}
