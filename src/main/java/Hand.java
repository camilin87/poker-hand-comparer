import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hand implements Comparable<Hand> {
    private static final int NOT_FOUND = -1;
    private static final int EQUAL_COMPARISON = 0;
    private static final int CARDS_PER_HAND = 5;

    private final Card[] cards;

    private Hand(Card[] cards){
        if (cards.length != CARDS_PER_HAND){
            throw new IllegalArgumentException();
        }

        if (Arrays.stream(cards).distinct().count() != CARDS_PER_HAND){
            throw new IllegalArgumentException();
        }

        this.cards = cards;
    }

    @Override
    public String toString() {
        List<String> result = Arrays.stream(cards)
                .map(Card::toString)
                .collect(Collectors.toList());
        return String.join(" ", result);
    }

    public static Hand parse(String encodedHand){
        Card[] cards = Arrays.stream(encodedHand.split(" "))
                .map(Card::parse)
                .collect(Collectors.toList())
                .toArray(new Card[0]);
        return new Hand(cards);
    }

    public static Hand[] parseMultiple(String[] encodedHands){
        Hand[] hands = Arrays.stream(encodedHands)
                .map(Hand::parse)
                .collect(Collectors.toList())
                .toArray(new Hand[0]);

        long uniqueCards = Arrays.stream(hands)
                .map(h -> h.cards)
                .flatMap(c -> Arrays.stream(c))
                .distinct()
                .count();

        if (uniqueCards != hands.length * CARDS_PER_HAND){
            throw new IllegalArgumentException();
        }

        return hands;
    }

    public static int compare(Hand h1, Hand h2){
        return h1.compareTo(h2);
    }

    @Override
    public int compareTo(Hand that) {
        int pairComparison = compareForPairs(this, that);
        if (pairComparison != EQUAL_COMPARISON){
            return pairComparison;
        }

        return compareForHighestValue(this, that);
    }

    private int pairValue(){
        int[] values = Arrays.stream(cards)
                .mapToInt(Card::getNumericValue)
                .toArray();

        return Arrays.stream(values)
                .filter(i -> Arrays.stream(values).filter(j -> j == i).count() == 2)
                .findFirst()
                .orElse(NOT_FOUND);
    }

    private static int compareForPairs(Hand h1, Hand h2){
        int v1 = h1.pairValue();
        int v2 = h2.pairValue();

        if (v1 == v2){
            return EQUAL_COMPARISON;
        }

        return Integer.compare(v1, v2);
    }

    private static int compareForHighestValue(Hand h1, Hand h2){
        int[] h1Values = Arrays.stream(h1.cards)
                .map(Card::getNumericValue)
                .sorted((i, j) -> Integer.compare(j, i))
                .mapToInt(i -> i)
                .toArray();

        int[] h2Values = Arrays.stream(h2.cards)
                .map(Card::getNumericValue)
                .sorted((i, j) -> Integer.compare(j, i))
                .mapToInt(i -> i)
                .toArray();

        return IntStream.range(0, CARDS_PER_HAND)
                .map(i -> Integer.compare(h1Values[i], h2Values[i]))
                .filter(i -> i != EQUAL_COMPARISON)
                .findFirst()
                .orElse(EQUAL_COMPARISON);
    }
}