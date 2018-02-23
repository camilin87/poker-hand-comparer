import java.util.*;
import java.util.stream.IntStream;

public class Card {
    private static final int CARD_LENGTH = 2;

    private static final Set<Character> suits = new HashSet<>(Arrays.asList('C', 'D', 'H', 'S'));

    private static final Map<Character, Integer> values = new HashMap<Character, Integer>(){{
        IntStream.rangeClosed('2', '9')
                .mapToObj(i -> (char)i)
                .forEach(c -> put(c, Character.getNumericValue(c)));

        put('T', 10);
        put('J', 11);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};

    private final char value;
    private final char club;

    private Card(char value, char club){
        char sanitizedValue = Character.toUpperCase(value);
        char sanitizedClub = Character.toUpperCase(club);

        if (IsInvalidValue(sanitizedValue)){
            throw new IllegalArgumentException("Invalid Card value");
        }

        if (IsInvalidSuit(sanitizedClub)){
            throw new IllegalArgumentException("Invalid Card suit");
        }

        this.value = sanitizedValue;
        this.club = sanitizedClub;
    }

    public static Card parse(final String str){
        if (str.length() != CARD_LENGTH){
            throw new IllegalArgumentException("Invalid Card length");
        }

        return new Card(str.charAt(0), str.charAt(1));
    }

    @Override
    public String toString() {
        return String.format("%s%s", value, club);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj instanceof Card && toString().equals(obj.toString());

    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    private static boolean IsInvalidSuit(char c){
        return !IsValidSuit(c);
    }

    private static boolean IsValidSuit(char c){
        return suits.contains(c);
    }

    private static boolean IsInvalidValue(char c){
        return !IsValidValue(c);
    }

    private static boolean IsValidValue(char c){
        return values.containsKey(c);
    }

    public int getNumericValue() {
        return values.get(value);
    }
}
