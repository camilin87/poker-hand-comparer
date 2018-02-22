import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    private final Card[] cards;

    private Hand(Card[] cards){
        if (cards.length != 5){
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
        if (encodedHand == null || encodedHand == ""){
            throw new IllegalArgumentException();
        }

        String[] cards = encodedHand.split(" ");
        if (Arrays.stream(cards).anyMatch(c -> c.length() != 2)){
            throw new IllegalArgumentException();
        }

        Card[] handCards = Arrays.stream(cards)
                .map(Card::parse)
                .collect(Collectors.toList())
                .toArray(new Card[0]);
        return new Hand(handCards);
    }
}