package cz.itexpert.adventcode2023.day1;

import java.util.HashSet;
import java.util.Set;

public class Dictionary {

    private Set<DictionaryItem> data;

    public Dictionary() {
        build();
    }

    private void build() {
        data = new HashSet<>();
        data.add( DictionaryItem.valueOf("1", 1, true));
        data.add( DictionaryItem.valueOf("2", 2, true));
        data.add( DictionaryItem.valueOf("3", 3, true));
        data.add( DictionaryItem.valueOf("4", 4, true));
        data.add( DictionaryItem.valueOf("5", 5, true));
        data.add( DictionaryItem.valueOf("6", 6,  true));
        data.add( DictionaryItem.valueOf("7", 7, true));
        data.add( DictionaryItem.valueOf("8", 8, true));
        data.add( DictionaryItem.valueOf("9", 9, true));

        data.add( DictionaryItem.valueOf("one", 1, false));
        data.add( DictionaryItem.valueOf("two", 2, false));
        data.add( DictionaryItem.valueOf("three", 3, false));
        data.add( DictionaryItem.valueOf("four", 4, false));
        data.add( DictionaryItem.valueOf("five", 5, false));
        data.add( DictionaryItem.valueOf("six", 6,  false));
        data.add( DictionaryItem.valueOf("seven", 7, false));
        data.add( DictionaryItem.valueOf("eight", 8, false));
        data.add( DictionaryItem.valueOf("nine", 9, false));

    }

    public DictionaryItem hasTextElement(String input) {
        for (DictionaryItem dictionaryItem: data) {
            if (dictionaryItem.getTextValue().equalsIgnoreCase(input)) {
                return dictionaryItem;
            }
        }
        return null;
    }
}
