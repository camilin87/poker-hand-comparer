import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void canBeBuiltOutOfATwoDigitString(){
        assertEquals("2D", Card.parse("2D").toString());
    }

    @Test
    public void parsingFailsWhenInvalidClub(){
        assertParseFails("2I");
        assertParseFails("2U");
        assertParseFails("2F");
    }

    @Test
    public void parsingFailsWhenValueTooLow(){
        assertParseFails("0D");
        assertParseFails("1D");
    }

    private static void assertParseFails(String str){
        try{
            Card.parse(str);
            fail("Should have thrown");
        }
        catch (IllegalArgumentException e){
            assertNotNull(e);
        }
    }
}
