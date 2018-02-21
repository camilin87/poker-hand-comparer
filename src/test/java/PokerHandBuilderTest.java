import org.junit.Test;

public class PokerHandBuilderTest {
    @Test
    public void returnsEmpty(){
        assert 0 == new PokerHandBuilder().build("aaaa").length;
    }
}
