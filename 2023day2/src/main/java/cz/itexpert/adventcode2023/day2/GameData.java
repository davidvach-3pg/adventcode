package cz.itexpert.adventcode2023.day2;

import java.util.*;

public class GameData {

    private final Integer gameNumber;
    private final Collection<RevealedCubeSet> revealedCubeSet = new LinkedList<>();

    public int buildFewestRevealedCubesAndReturnPower() {
        int power=1, maxColorNumber;
        for (CubeColor cubeColor: CubeColor.values()) {
            maxColorNumber =1 ;
            for (RevealedCubeSet revealedCubeSetItem: revealedCubeSet) {
                for (RevealedCube revealedCube: revealedCubeSetItem.revealedCubeList()) {
                    if (revealedCube.cubeColor().equals(cubeColor)) {
                        maxColorNumber = Math.max(maxColorNumber, revealedCube.number());
                    }
                }
            }
            power *= maxColorNumber;
        }
        return power;
    }

    public GameData(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }

    public boolean isPartOfBag(Bag bag) {
        int maxGuess;
        for (BagItem bagItem: bag.bagItems()) {
            maxGuess = 0;
            for (RevealedCubeSet revealedCubeSet: revealedCubeSet) {
                for (RevealedCube revealedCube : revealedCubeSet.revealedCubeList()) {
                    if (revealedCube.cubeColor().equals(bagItem.cubeColor())) {
                        maxGuess = Math.max(revealedCube.number(), maxGuess);
                    }
                }
            }
            if (bagItem.numberOfElements() < maxGuess ) {
                return false;
            }
        }

    return true;

    }


    public void addRevealedCubeSet(Collection<RevealedCube> revealedCubes) {
        revealedCubeSet.add(new RevealedCubeSet(revealedCubes));
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public Collection<RevealedCubeSet> getRevealedCubeSet() {
        return revealedCubeSet;
    }
}
