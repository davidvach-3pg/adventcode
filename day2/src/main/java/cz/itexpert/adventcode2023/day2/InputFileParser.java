package cz.itexpert.adventcode2023.day2;

import java.io.InputStream;
import java.util.*;


public class InputFileParser {

    public Collection<GameData> parseFile(InputStream inputStream) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(inputStream));
        Collection<GameData> result = new HashSet<>();
        while (scanner.hasNextLine()) {
            result.add(parseLine(scanner.nextLine()));
        }
        return result;
     }

    public GameData parseLine(String line) {
        // Game 1: 7 blue, 9 red, 1 green; 8 green; 10 green, 5 blue, 3 red; 11 blue, 5 red, 1 green
        String[] text = line.split(":");
        String[] gameDataText = text[0].split(" ");
        GameData gameData = new GameData(Integer.parseInt(gameDataText[1]));

        String[] revealedCubeTexts = text[1].split(";");

        String[] cubeTexts;
        String[] item;
        Set<RevealedCube> revealedCubeSet;

        for (String revealedCubeText: revealedCubeTexts) {
            cubeTexts = revealedCubeText.trim().split(",");
            revealedCubeSet = new HashSet<>();
            for (String cubeText: cubeTexts) {
                item = cubeText.trim().split(" ");
                revealedCubeSet.add(new RevealedCube(CubeColor.valueOf(item[1]), Integer.parseInt(item[0]) ));
            }
            gameData.addRevealedCubeSet(revealedCubeSet);
        }

        return gameData;
    }
}
