import org.junit.Test;
import java.util.Arrays;

public class HandTest {
    private final Hand builder = new Hand();

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenNull(){
        builder.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenEmpty(){
        builder.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenLessThanFiveCards(){
        builder.parse("2D 3D 4D 5D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenMoreThanFiveCards(){
        builder.parse("3D 3D 4D 5D 6D 7D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenCardTooShort(){
        builder.parse("D 2D 3D 4D 5D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenCardTooLong(){
        builder.parse("10D 2D 3D 4D 5D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenInvalidClub(){
        builder.parse("2C 3D 4H 5S 6I");
    }

    @Test
    public void buildsHand(){
        String[] expected = {"2D", "3D", "4D", "5D", "6D"};
        assert Arrays.equals(expected, builder.parse("2D 3D 4D 5D 6D"));
    }
}
