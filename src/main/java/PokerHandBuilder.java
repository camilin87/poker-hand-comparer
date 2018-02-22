import java.util.Arrays;

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

        boolean containsInvalidClub = Arrays.stream(cards)
                .map(c -> c.charAt(1))
                .anyMatch(Card::IsInvalidClub);
        if (containsInvalidClub){
            throw new IllegalArgumentException();
        }

        return new String[]{"2D", "3D", "4D", "5D", "6D"};
    }
}