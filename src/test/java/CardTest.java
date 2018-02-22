import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void canBeBuiltOutOfATwoDigitString(){
        assertEquals("2D", Card.parse("2D").toString());
        assertEquals("3D", Card.parse("3D").toString());
        assertEquals("4D", Card.parse("4D").toString());
        assertEquals("5D", Card.parse("5D").toString());
        assertEquals("6D", Card.parse("6D").toString());
        assertEquals("7D", Card.parse("7D").toString());
        assertEquals("8D", Card.parse("8D").toString());
        assertEquals("9D", Card.parse("9D").toString());
        assertEquals("TD", Card.parse("TD").toString());
        assertEquals("JD", Card.parse("JD").toString());
        assertEquals("QD", Card.parse("QD").toString());
        assertEquals("KD", Card.parse("KD").toString());
        assertEquals("AD", Card.parse("AD").toString());
    }

    @Test
    public void canBeBuiltOutOfATwoDigitLowercasedString(){
        assertEquals("2D", Card.parse("2d").toString());
        assertEquals("3D", Card.parse("3d").toString());
        assertEquals("4D", Card.parse("4d").toString());
        assertEquals("5D", Card.parse("5d").toString());
        assertEquals("6D", Card.parse("6d").toString());
        assertEquals("7D", Card.parse("7d").toString());
        assertEquals("8D", Card.parse("8d").toString());
        assertEquals("9D", Card.parse("9d").toString());
        assertEquals("TD", Card.parse("td").toString());
        assertEquals("JD", Card.parse("jd").toString());
        assertEquals("QD", Card.parse("qd").toString());
        assertEquals("KD", Card.parse("kd").toString());
        assertEquals("AD", Card.parse("ad").toString());
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

    @Test
    public void parsingFailsWhenValueIsInvalid(){
        assertParseFails("YD");
        assertParseFails("BD");
        assertParseFails("UD");
    }

    @Test
    public void equalCardsAreEqual(){
        assertEquals(Card.parse("2D"), Card.parse("2D"));
        assertEquals(Card.parse("KS"), Card.parse("KS"));
        assertEquals(Card.parse("2D"), Card.parse("2d"));
        assertEquals(Card.parse("KS"), Card.parse("ks"));
    }

    @Test
    public void differentCardsAreNotEqual(){
        assertNotEquals(Card.parse("3D"), null);
        assertNotEquals(null, Card.parse("3D"));
        assertNotEquals(Card.parse("3D"), "3D");
        assertNotEquals(Card.parse("3D"), Card.parse("3S"));
        assertNotEquals(Card.parse("3D"), Card.parse("4D"));
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
