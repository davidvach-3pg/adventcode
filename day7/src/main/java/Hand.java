import java.util.*;

public class Hand implements Comparable<Hand> {

    private HandType handType;

    private List<Card> cardList = new ArrayList<>();

    private int jokerCount = 0;

    private String cardAsText;

    private long bet;

    public long getBet() {
        return bet;
    }

    public int getJokerCount() {
        return jokerCount;
    }

    public Hand(String cardAsText, long bet) {
        this.bet = bet;
        this.cardAsText = cardAsText;
        Card card;
        Map<Card, Integer> handTypeCache = new HashMap();
        for (String cardSymbol: cardAsText.split("")) {
            card = new Card(cardSymbol);
            cardList.add(card);
            if (cardSymbol.equals("J")) {
                jokerCount++;
            } else {
                if (handTypeCache.containsKey(card)) {
                    handTypeCache.put(card, handTypeCache.get(card) + 1);
                } else {
                    handTypeCache.put(card, 1);
                }
            }
        }
        handType = HandType.calculate(handTypeCache, this);
    }


    @Override
    public int compareTo(Hand o2) {
        if (this.handType.getWinningPosition() != o2.handType.getWinningPosition()) {
            return (this.handType.getWinningPosition() - o2.handType.getWinningPosition());
        } else {
            for (int no = 0; no < this.cardList.size(); no++) {
                if (this.cardList.get(no).getValue() != o2.cardList.get(no).getValue()) {
                    return this.cardList.get(no).getValue() - o2.cardList.get(no).getValue();
                }
            }
        }
        return 0;
    }
}
