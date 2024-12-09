package cz.itexpert.adventcode2023.day1;

import java.util.*;

public class StringText {

    public static LinkedList<DictionaryItem> parseNumbers(String inputString) {

        LinkedList<DictionaryItem> result = new LinkedList<>();

        Dictionary dictionary = new Dictionary();

        String input;
        DictionaryItem recognizedItem;
        for (int i = 0; i < inputString.length(); i++) {
            for (int j = i+1; j <= inputString.length(); j++) {
                input = inputString.substring(i, j);
                recognizedItem = dictionary.hasTextElement(input);
                if (recognizedItem != null) {
                    result.add(recognizedItem);
                    if (!recognizedItem.isNumber()) {
                        i = j-1;  // twone = two and one
                    }
                }
            }
        }

        return result;
    }
}
