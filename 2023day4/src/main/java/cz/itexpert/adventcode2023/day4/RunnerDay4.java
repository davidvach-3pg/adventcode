package cz.itexpert.adventcode2023.day4;

import java.util.*;

public class RunnerDay4 {

    public static void main(String[] args) {

        InputFileParser inputFileParser = new InputFileParser();
        Collection<Card> gameDataSet = inputFileParser.parseFile(RunnerDay4.class.getResourceAsStream("/day4.txt"));

        int totalSum = 0;
        for (Card card: gameDataSet) {
            totalSum += card.getWinningPoints();
        }

        System.out.println("Result a:" +  totalSum);


        int[] cardPool = new int[gameDataSet.size()];
        for (int i= 0; i <= gameDataSet.size()-1; i++) {
            cardPool[i] = 1;
        }

        totalSum = 0;
        for (Card card: gameDataSet) {
            addCardsToPool(cardPool, card);

        }


        for (int i= 0; i < cardPool.length; i++) {
            totalSum += cardPool[i];
        }
        System.out.println("Result b:"+ totalSum);
    }

    public static void addCardsToPool( int[] cardPool, Card card) {
        int originalPool = cardPool[card.getCardNumber()-1];

        for (int cardNumberToAdd = card.getCardNumber() + 1; cardNumberToAdd <= (card.getCardNumber() + card.getMatchNumbers().size()); cardNumberToAdd++) {
            if (cardPool.length >= cardNumberToAdd) {
                cardPool[cardNumberToAdd-1] += originalPool;
            }
        }
    }
}
