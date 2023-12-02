package cz.itexpert.adventcode2023.day2;

import java.util.Collection;
import java.util.Set;

public class RunnerDay2 {

    public static void main(String[] args) {

        InputFileParser inputFileParser = new InputFileParser();
        Collection<GameData> gameDataSet = inputFileParser.parseFile(RunnerDay2.class.getResourceAsStream("/day2.txt"));

        Bag bag = new Bag(Set.of(new BagItem(CubeColor.red, 12), new BagItem(CubeColor.green, 13), new BagItem(CubeColor.blue, 14)));

        int result = 0;
        for (GameData gameData: gameDataSet) {
            if (gameData.isPartOfBag(bag)) {
                result += gameData.getGameNumber();
            }
        }
        System.out.println(result);
        result = 0;
        for (GameData gameData: gameDataSet) {
                result += gameData.buildFewestRevealedCubesAndReturnPower();
        }
        System.out.println(result);


    }
}
