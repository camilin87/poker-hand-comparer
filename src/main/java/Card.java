public class Card {
    private char value;
    private char club;

    private Card(char value, char club){
        char sanitizedValue = Character.toUpperCase(value);
        char sanitizedClub = Character.toUpperCase(club);

        if (IsInvalidValue(sanitizedValue)){
            throw new IllegalArgumentException();
        }

        if (IsInvalidClub(sanitizedClub)){
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

    public static boolean IsInvalidClub(char c){
        return !IsValidClub(c);
    }

    public static boolean IsValidClub(char c){
        return c == 'C' ||
                c == 'D' ||
                c == 'H' ||
                c == 'S';
    }

    public static boolean IsInvalidValue(char c){
        return !IsValidValue(c);
    }

    public static boolean IsValidValue(char c){
        if (c == 'T' ||
                c == 'J' ||
                c == 'Q' ||
                c == 'K' ||
                c == 'A'){
            return true;
        }

        return Character.isDigit(c) && Character.getNumericValue(c) > 1;
    }
}
