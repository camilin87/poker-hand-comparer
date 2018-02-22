import org.junit.Test;

import java.lang.reflect.Array;
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

    @Test
    public void buildsHand(){
        String[] expected = {"2D", "3D", "4D", "5D", "6D"};
        assert Arrays.equals(expected, builder.build("2D 3D 4D 5D 6D"));
    }
}
