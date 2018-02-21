import org.junit.Test;

public class PokerHandBuilderTest {
    @Test(expected = IllegalArgumentException.class)
    public void failsWhenInputIsEmpty(){
        new PokerHandBuilder().build("");
    }
}
