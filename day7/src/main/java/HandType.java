import java.util.Iterator;
import java.util.Map;

public enum HandType {

    FIVE_OF_A_KIND(7), FOUR_OF_A_KIND(6), FULL_HOUSE(5), THREE_OF_A_KIND(4), TWO_PAIRS(3), ONE_PAIRS(2), HIGH_CARD(1);

    private int winningPosition;

    private HandType(int winningPosition) {
        this.winningPosition = winningPosition;
    }

    public int getWinningPosition() {
        return winningPosition;
    }

    public static HandType calculate(Map<Card, Integer> handCache, Hand hand) {
        HandType handType = calculateInternal(handCache);
        if (RunnerDay7.jokerMode) {
            switch (hand.getJokerCount()) {
                case 0:
                    return handType;
                case 1: {
                    switch (handType) {
                        case FOUR_OF_A_KIND:
                            return FIVE_OF_A_KIND;
                        case THREE_OF_A_KIND:
                            return FOUR_OF_A_KIND;
                        case TWO_PAIRS:
                            return FULL_HOUSE;
                        case ONE_PAIRS:
                            return THREE_OF_A_KIND;
                        case HIGH_CARD: {
                            return ONE_PAIRS;
                        }
                    }
                }

                case 2: {
                    switch (handType) {
                        case THREE_OF_A_KIND:
                            return FIVE_OF_A_KIND;
                        case ONE_PAIRS:
                            return FOUR_OF_A_KIND;
                        case HIGH_CARD: {
                            return THREE_OF_A_KIND;
                        }
                    }
                }
                case 3: {
                    switch (handType) {
                        case ONE_PAIRS:
                            return FIVE_OF_A_KIND;
                        case HIGH_CARD: {
                            return FOUR_OF_A_KIND;
                        }
                    }
                }
                case 4: {
                    switch (handType) {
                        case HIGH_CARD: {
                            return FIVE_OF_A_KIND;
                        }
                    }
                    break;
                }
                case 5: {
                    return FIVE_OF_A_KIND;
                }
            }
            throw new IllegalStateException("Unknown combination:");


        } else {
            return handType;
        }

    }

    public static HandType calculateInternal(Map<Card, Integer> hand) {
        Iterator<Map.Entry<Card, Integer>> i = hand.entrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getValue() == 1) {
                i.remove();
            }
        }
        if (hand.size() == 0) {
            return HIGH_CARD;
        } else if (hand.size() == 1) {
            int number = hand.values().iterator().next();
            switch (number) {
                case 5:
                    return FIVE_OF_A_KIND;
                case 4:
                    return FOUR_OF_A_KIND;
                case 3:
                    return THREE_OF_A_KIND;
                case 2:
                    return ONE_PAIRS;
            }
        } else if (hand.size() == 2) {
            Iterator<Integer> i1 = hand.values().iterator();
            int pair1 = i1.next();
            int pair2 = i1.next();
            if (pair1 == 2 && pair2 == 2) {
                return TWO_PAIRS;
            } else if (pair1 == 3 && pair2 == 2 || pair1 == 2 && pair2 == 3) {
                return FULL_HOUSE;
            } else {
                throw new IllegalStateException("Unknown combination:");

            }

        } else {
            throw new IllegalStateException("Unknown combination:");
        }

        throw new IllegalStateException("Unknown combination:");
    }
}
