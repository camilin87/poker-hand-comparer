import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void canBeBuiltOutOfATwoDigitString(){
        assertEquals("2D", Card.Parse("2D").toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenInvalidClub(){
        Card.Parse("2I");
    }
}
