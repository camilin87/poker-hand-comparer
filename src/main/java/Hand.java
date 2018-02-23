import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hand implements Comparable<Hand> {
    private static final int EQUAL_COMPARISON = 0;
    private static final int CARDS_PER_HAND = 5;
    private static final String CARD_DELIMITER = " ";

    private final Card[] cards;

    private Hand(Card[] cards){
        if (cards.length != CARDS_PER_HAND){
            throw new IllegalArgumentException("Invalid number or cards in Hand");
        }

        if (Arrays.stream(cards).distinct().count() != CARDS_PER_HAND){
            throw new IllegalArgumentException("Found duplicate cards in Hand");
        }

        this.cards = cards;
    }

    @Override
    public String toString() {
        return Arrays.stream(cards)
                .map(Card::toString)
                .collect(Collectors.joining(CARD_DELIMITER));
    }

    public static Hand parse(final String encodedHand){
        Card[] cards = Arrays.stream(encodedHand.split(CARD_DELIMITER))
                .map(Card::parse)
                .toArray(Card[]::new);
        return new Hand(cards);
    }

    public static Hand[] parseMultiple(final String[] encodedHands){
        Hand[] hands = Arrays.stream(encodedHands)
                .map(Hand::parse)
                .toArray(Hand[]::new);

        long uniqueCards = Arrays.stream(hands)
                .map(h -> h.cards)
                .flatMap(Arrays::stream)
                .distinct()
                .count();

        if (uniqueCards != hands.length * CARDS_PER_HAND){
            throw new IllegalArgumentException("Found duplicate cards in set");
        }

        return hands;
    }

    public static int compare(Hand h1, Hand h2){
        return h1.compareTo(h2);
    }

    @Override
    public int compareTo(Hand that) {
        int pairsComparison = compareForPairs(this, that);
        if (pairsComparison != EQUAL_COMPARISON){
            return pairsComparison;
        }

        return compareForHighestValue(this, that);
    }

    private static int compareForPairs(Hand h1, Hand h2){
        int[] p1 = h1.pairValues();
        int[] p2 = h2.pairValues();

        int pairLengthComparison = Integer.compare(p1.length, p2.length);
        if (pairLengthComparison != EQUAL_COMPARISON){
            return pairLengthComparison;
        }

        return Integer.compare(Arrays.stream(p1).sum(), Arrays.stream(p2).sum());
    }

    private static int compareForHighestValue(Hand h1, Hand h2){
        int[] h1Values = h1.getValuesSortedDesc();
        int[] h2Values = h2.getValuesSortedDesc();

        return IntStream.range(0, CARDS_PER_HAND)
                .map(i -> Integer.compare(h1Values[i], h2Values[i]))
                .filter(i -> i != EQUAL_COMPARISON)
                .findFirst()
                .orElse(EQUAL_COMPARISON);
    }

    private int[] pairValues(){
        Map<Integer, List<Card>> equivalentCards = Arrays.stream(cards)
                .collect(Collectors.groupingBy(Card::getNumericValue));

        return equivalentCards
                .keySet()
                .stream()
                .filter(k -> equivalentCards.get(k).size() == 2)
                .mapToInt(i -> i)
                .toArray();
    }

    private int[] getValuesSortedDesc(){
        return Arrays.stream(cards)
                .map(Card::getNumericValue)
                .sorted((i, j) -> Integer.compare(j, i))
                .mapToInt(i -> i)
                .toArray();
    }
}