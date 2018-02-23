import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hand implements Comparable<Hand> {
    private static final int cardsPerHand = 5;

    private final Card[] cards;

    private Hand(Card[] cards){
        if (cards.length != cardsPerHand){
            throw new IllegalArgumentException();
        }

        if (Arrays.stream(cards).distinct().count() != cardsPerHand){
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

        if (uniqueCards != hands.length * cardsPerHand){
            throw new IllegalArgumentException();
        }

        return hands;
    }

    public static int compare(Hand h1, Hand h2){
        return h1.compareTo(h2);
    }

    @Override
    public int compareTo(Hand that) {
        if (this.isPair() && !that.isPair()){
            return 1;
        }

        if (that.isPair() && !this.isPair()){
            return -1;
        }

        return compareForHighestValue(this, that);
    }

    private boolean isPair(){
        long uniqueValues = Arrays.stream(cards)
                .map(Card::getNumericValue)
                .distinct()
                .count();
        return uniqueValues == 4;
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

        return IntStream.range(0, cardsPerHand)
                .map(i -> Integer.compare(h1Values[i], h2Values[i]))
                .filter(i -> i != 0)
                .findFirst()
                .orElse(0);
    }
}