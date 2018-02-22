public class Card {
    private char value;
    private char club;

    private Card(char value, char club){
        if (IsInvalidClub(club)){
            throw new IllegalArgumentException();
        }

        if (IsInvalidValue(value)){
            throw new IllegalArgumentException();
        }

        this.value = value;
        this.club = club;
    }

    public static Card parse(String str){
        return new Card(str.charAt(0), str.charAt(1));
    }

    @Override
    public String toString() {
        return String.format("%s%s", value, club);
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
        return Character.getNumericValue(c) > 1;
    }
}
