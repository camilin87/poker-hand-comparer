public class Card {
    private char value;
    private char club;

    public static Card Parse(String str){
        return new Card(str.charAt(0), str.charAt(1));
    }

    private Card(char value, char club){
        this.value = value;
        this.club = club;
    }

    @Override
    public String toString() {
        return String.format("%s%s", value, club);
    }
}
