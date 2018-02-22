import java.util.Arrays;
import java.util.stream.Collectors;

public class PokerHandBuilder {
    public String[] build(String encodedHand){
        if (encodedHand == null || encodedHand == ""){
            throw new IllegalArgumentException();
        }

        String[] cards = encodedHand.split(" ");
        if (cards.length != 5){
            throw new IllegalArgumentException();
        }

        if (Arrays.stream(cards).anyMatch(c -> c.length() != 2)){
            throw new IllegalArgumentException();
        }

        return Arrays.stream(cards)
                .map(Card::parse)
                .map(Card::toString)
                .collect(Collectors.toList())
                .toArray(new String[0]);

    }
}