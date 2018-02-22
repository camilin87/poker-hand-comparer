public class Card {
    private char value;
    private char club;

    public static Card Parse(String str){
        return new Card(str.charAt(0), str.charAt(1));
    }

    private Card(char value, char club){
        if (IsInvalidClub(club)){
            throw new IllegalArgumentException();
        }

        this.value = value;
        this.club = club;
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
}
