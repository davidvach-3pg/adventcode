package cz.itexpert.adventcode2023.day1;

public class DictionaryItem {

    private final String textValue;
    private final Integer value;

    private final boolean number;

    public DictionaryItem(String textValue, int value, boolean number) {
        this.textValue = textValue;
        this.value = value;
        this.number = number;
    }

    public static DictionaryItem valueOf(String textValue, int value, boolean number) {
        return new DictionaryItem(textValue, value, number);
    }

    public String getTextValue() {
        return textValue;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isNumber() {
        return number;
    }

    @Override
    public String toString() {
        return textValue;
    }
}
