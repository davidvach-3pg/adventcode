package cz.itexpert.adventcode2023.day4;

import java.io.InputStream;
import java.util.*;

public class InputFileParser {

    public Collection<Card> parseFile(InputStream inputStream) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(inputStream));
        Collection<Card> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            result.add(parseLine(scanner.nextLine()));
        }
        return result;
    }

    public Card parseLine(String line) {
        line = line.replaceAll("\\s{1,}", " ");
        // Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        String[] text = line.split(":");
        Card card = new Card(Integer.parseInt(text[0].split(" ")[1]));
        String[] numberRow = text[1].split("\\|");
        for (String number: numberRow[0].split(" ")) {
            if (number.trim().length() > 0) {
                card.addWinningNumber(Integer.valueOf(number));
            }
        }
        for (String number: numberRow[1].split(" ")) {
            if (number.trim().length() > 0) {
                card.addMyNumber(Integer.valueOf(number));
            }
        }
        return card;
    }
}
