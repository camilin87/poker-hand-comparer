import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
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
}