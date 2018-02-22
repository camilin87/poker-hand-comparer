import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Card {
    private static final Set<Character> suits = new HashSet<>(Arrays.asList(new Character[]{
            'C', 'D', 'H', 'S'
    }));

    private static final Set<Character> specialValues = new HashSet<>(Arrays.asList(new Character[]{
            'T', 'J', 'Q', 'K', 'A'
    }));

    private char value;
    private char club;

    private Card(char value, char club){
        char sanitizedValue = Character.toUpperCase(value);
        char sanitizedClub = Character.toUpperCase(club);

        if (IsInvalidValue(sanitizedValue)){
            throw new IllegalArgumentException();
        }

        if (IsInvalidSuit(sanitizedClub)){
            throw new IllegalArgumentException();
        }

        this.value = sanitizedValue;
        this.club = sanitizedClub;
    }

    public static Card parse(String str){
        return new Card(str.charAt(0), str.charAt(1));
    }

    @Override
    public String toString() {
        return String.format("%s%s", value, club);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }

        if (!(obj instanceof Card)){
            return false;
        }

        return toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public static boolean IsInvalidSuit(char c){
        return !IsValidSuit(c);
    }

    public static boolean IsValidSuit(char c){
        return suits.contains(c);
    }

    public static boolean IsInvalidValue(char c){
        return !IsValidValue(c);
    }

    public static boolean IsValidValue(char c){
        if (specialValues.contains(c)){
            return true;
        }

        return Character.isDigit(c) && Character.getNumericValue(c) > 1;
    }
}
