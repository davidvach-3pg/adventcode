import java.util.Objects;

public class Card {

    private int value;

    private String symbol;

    public Card(String mark) {
        this.symbol = mark;
        switch (mark) {
            case "A":
                value = 14;
                break;
            case "K":
                value = 13;
                break;
            case "Q":
                value = 12;
                break;
            case "J":
                if (RunnerDay7.jokerMode) {
                    value = -1;
                } else {
                    value = 11;
                }
                break;
            case "T":
                value = 10;
                break;
            default:
                value = Integer.parseInt(mark);
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", mark='" + symbol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
