package cz.itexpert.adventcode2023.day2;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class InputFileParserTest {

    @Test
    public void parseLineTest1() {

        String inputLine = "Game 1: 7 blue, 9 red, 1 green; 8 green; 10 green, 5 blue, 3 red; 11 blue, 5 red, 1 green";

        InputFileParser inputFileParser = new InputFileParser();
        GameData actualGameData = inputFileParser.parseLine(inputLine);


        GameData expectedGameData = prepareExpectedGameData();

        assertNotNull(actualGameData);

        assertEquals(expectedGameData.getGameNumber(), actualGameData.getGameNumber());
        assertEquals(expectedGameData.getRevealedCubeSet().size(), actualGameData.getRevealedCubeSet().size());

        assertThat(actualGameData.getRevealedCubeSet()).hasSameElementsAs(expectedGameData.getRevealedCubeSet());

    }

    @Test
    public void testBag() {
        // Game 1: 7 blue, 9 red, 1 green; 8 green; 10 green, 5 blue, 3 red; 11 blue, 5 red, 1 green
        GameData expectedGameData = prepareExpectedGameData();
        Bag bag = new Bag(List.of(new BagItem(CubeColor.blue, 1), new BagItem(CubeColor.green, 1), new BagItem(CubeColor.red, 1)));

        assertFalse(expectedGameData.isPartOfBag(bag));

        bag = new Bag(List.of(new BagItem(CubeColor.blue, 11), new BagItem(CubeColor.green, 10), new BagItem(CubeColor.red, 9)));
        assertTrue(expectedGameData.isPartOfBag(bag));
    }

    private static GameData prepareExpectedGameData() {
        Set<RevealedCube> revealedCubeSet1 = Set.of(new RevealedCube(CubeColor.blue, 7), new RevealedCube(CubeColor.red, 9), new RevealedCube(CubeColor.green, 1));
        Set<RevealedCube> revealedCubeSet2 = Set.of(new RevealedCube(CubeColor.green, 8));
        Set<RevealedCube> revealedCubeSet3 = Set.of(new RevealedCube(CubeColor.blue, 5), new RevealedCube(CubeColor.red, 3), new RevealedCube(CubeColor.green, 10));
        Set<RevealedCube> revealedCubeSet4 = Set.of(new RevealedCube(CubeColor.blue, 11), new RevealedCube(CubeColor.red, 5), new RevealedCube(CubeColor.green, 1));
        GameData expectedGameData = new GameData(1);
        expectedGameData.addRevealedCubeSet(revealedCubeSet1);
        expectedGameData.addRevealedCubeSet(revealedCubeSet2);
        expectedGameData.addRevealedCubeSet(revealedCubeSet3);
        expectedGameData.addRevealedCubeSet(revealedCubeSet4);
        return expectedGameData;
    }
}
