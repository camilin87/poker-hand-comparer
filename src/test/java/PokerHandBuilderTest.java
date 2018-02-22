import org.junit.Test;
import java.util.Arrays;

public class PokerHandBuilderTest {
    private final PokerHandBuilder builder = new PokerHandBuilder();

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenNull(){
        builder.build(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenEmpty(){
        builder.build("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenLessThanFiveCards(){
        builder.build("2D 3D 4D 5D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenMoreThanFiveCards(){
        builder.build("3D 3D 4D 5D 6D 7D");
    }

    @Test
    public void buildsHand(){
        String[] expected = {"2D", "3D", "4D", "5D", "6D"};
        assert Arrays.equals(expected, builder.build("2D 3D 4D 5D 6D"));
    }
}
