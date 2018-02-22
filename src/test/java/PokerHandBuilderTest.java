import org.junit.Test;

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
}
