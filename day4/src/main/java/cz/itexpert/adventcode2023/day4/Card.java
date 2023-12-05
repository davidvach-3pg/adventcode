package cz.itexpert.adventcode2023.day4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Card {

    private final int cardNumber;

    private final List<Integer> winningNumbers = new ArrayList<>();

    private final Set<Integer> matchNumbers = new HashSet<>();


    public Set<Integer> getMatchNumbers() {
        return matchNumbers;
    }

    public void addWinningNumber(Integer number) {
        winningNumbers.add(number);
    }

    public void addMyNumber(Integer number) {
        if (winningNumbers.contains(number)) {
            matchNumbers.add(number);
        }
    }

    public Card(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getWinningPoints() {
        if (matchNumbers.size() > 0) {
            return calculatePoints(matchNumbers.size());
        } else {
            return 0;
        }
    }

    public int getCardNumber() {
        return cardNumber;
    }

    private static int calculatePoints(int number) {
        int result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= 2;
        }

        return result;
    }
}
