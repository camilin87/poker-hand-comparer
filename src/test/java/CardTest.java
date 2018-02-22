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

    @Test(expected = NullPointerException.class)
    public void parsingFailsForNull(){
        Card.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parsingFailsWhenEmpty(){
        Card.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parsingFailsWhenCardTooShort(){
        Card.parse("D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parsingFailsWhenCardTooLong(){
        Card.parse("10D");
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

        assertEquals(Card.parse("2D").hashCode(), Card.parse("2D").hashCode());
        assertEquals(Card.parse("KS").hashCode(), Card.parse("KS").hashCode());
        assertEquals(Card.parse("2D").hashCode(), Card.parse("2d").hashCode());
        assertEquals(Card.parse("KS").hashCode(), Card.parse("ks").hashCode());
    }

    @Test
    public void differentCardsAreNotEqual(){
        assertNotEquals(Card.parse("3D"), null);
        assertNotEquals(null, Card.parse("3D"));
        assertNotEquals(Card.parse("3D"), "3D");
        assertNotEquals(Card.parse("3D"), Card.parse("3S"));
        assertNotEquals(Card.parse("3D"), Card.parse("4D"));

        assertNotEquals(Card.parse("3D").hashCode(), Card.parse("3S").hashCode());
        assertNotEquals(Card.parse("3D").hashCode(), Card.parse("4D").hashCode());
    }

    @Test
    public void calculatesTheNumericValue(){
        assertEquals(2, Card.parse("2D").getNumericValue());
        assertEquals(3, Card.parse("3D").getNumericValue());
        assertEquals(4, Card.parse("4D").getNumericValue());
        assertEquals(5, Card.parse("5D").getNumericValue());
        assertEquals(6, Card.parse("6D").getNumericValue());
        assertEquals(7, Card.parse("7D").getNumericValue());
        assertEquals(8, Card.parse("8D").getNumericValue());
        assertEquals(9, Card.parse("9D").getNumericValue());
        assertEquals(10, Card.parse("TD").getNumericValue());
        assertEquals(11, Card.parse("JD").getNumericValue());
        assertEquals(12, Card.parse("QD").getNumericValue());
        assertEquals(13, Card.parse("KD").getNumericValue());
        assertEquals(14, Card.parse("AD").getNumericValue());
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
