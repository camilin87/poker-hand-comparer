import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

public class HandTest {
    @Test(expected = NullPointerException.class)
    public void failsWhenNull(){
        Hand.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenEmpty(){
        Hand.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenLessThanFiveCards(){
        Hand.parse("2D 3D 4D 5D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenMoreThanFiveCards(){
        Hand.parse("3D 3D 4D 5D 6D 7D");
    }

    @Test
    public void buildsHand(){
        assertEquals("2D 3D 4D 5D 6D", Hand.parse("2D 3D 4D 5D 6D").toString());
        assertEquals("2D 3D 4S 5H 6D", Hand.parse("2d 3D 4S 5h 6D").toString());
        assertEquals("2C 2D 2H 2S 6D", Hand.parse("2c 2d 2h 2s 6D").toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenDuplicatedCard(){
        Hand.parse("2D 2D 4D 5D 6D");
    }

    @Test
    public void buildsMultipleHandsAtOnce(){
        String[] expected = new String[]{
                "7S 3S 4S 5S 6S",
                "7D 8D 9H 5H 6D",
                "2C 2D 2H 2S 6C"
        };

        String[] input = new String[]{
                "7S 3S 4S 5S 6S",
                "7d 8D 9H 5h 6D",
                "2c 2d 2h 2s 6C"
        };
        String[] actual = Arrays.stream(Hand.parseMultiple(input))
                .map(Hand::toString)
                .toArray(String[]::new);

        assertEquals(String.join("|", expected), String.join("|", actual));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenDuplicatedCardIsFoundOnDifferentHands(){
        String[] input = new String[]{
                "2D 3D 4D 5D 6D",
                "7D 8D 9D AD 2D"
        };
        Hand.parseMultiple(input);
    }

    @Test
    public void handWithHighestCardWins(){
        assertIsStrongest("3D 9C 8D 4H 2H", new String[]{
                "3D 9C 8D 4H 2H",
                "3H 7D 8H 4D 2D"
        });
    }

    @Test
    public void handWithHighestCardWinsEvenWhenPartiallyTied(){
        assertIsStrongest("3D 9C 8D 5H 2H", new String[]{
                "3D 9C 8D 5H 2H",
                "3H 9D 8H 4D 2D"
        });
    }

    @Test
    public void handWithEquivalentCardsAreTied(){
        assertEquals(0, Hand.compare(
                Hand.parse("3D 9C 8D 4H 2H"),
                Hand.parse("3H 9D 8H 4D 2D")
        ));
    }

    @Test
    public void onePairBeatsCommonCards(){
        assertIsStrongest("2H 2S 7D 5H 9H", new String[]{
                "2H 2S 7D 5H 9H",
                "AH 9D 8H 4D 2D"
        });
    }

    @Test
    public void onePairBeatsAWeakerPair(){
        assertIsStrongest("AH 8D 8H 4D 2D", new String[]{
                "2H 2S 7D 5H 9H",
                "AH 8D 8H 4D 2D"
        });
    }

    @Test
    public void equalPairsAreUntiedByHighestValueCard(){
        assertIsStrongest("AH 8D 8H 2C 2D", new String[]{
                "2H 2S 7D 5H 9H",
                "AH 8D 8H 2C 2D"
        });
    }

    @Test
    public void twoPairBeatsPair(){
        assertIsStrongest("2H 2S 4D 4H 9H", new String[]{
                "2H 2S 4D 4H 9H",
                "AH AD 8H 4S 2D"
        });
    }

    @Test
    public void twoPairBeatsWeakerTwoPairWhenTheStrongestPairIsDifferent(){
        assertIsStrongest("AH AD 3H 3S 2D", new String[]{
                "2H 2S 4D 4H 9H",
                "AH AD 3H 3S 2D"
        });

        assertIsStrongest("2H 2S 5D 5H 9H", new String[]{
                "2H 2S 5D 5H 9H",
                "4H AD 3H 3S 4D"
        });
    }

    @Test
    public void twoPairBeatsWeakerTwoPairWhenTheStrongestPairIsEquivalent(){
        assertIsStrongest("4C 4S 3H 3S 2D", new String[]{
                "2H 2S 4D 4H 9H",
                "4C 4S 3H 3S 2D"
        });
    }

    @Test
    public void equalTwoPairsAreUntiedByHighestCard(){
        assertIsStrongest("2H 2S 4D 4H 9H", new String[]{
                "2H 2S 4D 4H 9H",
                "4C 4S 2C 2D 7D"
        });
    }

    @Test
    public void threeOfAKindBeatsTwoPair(){
        assertIsStrongest("2H 2S 2D 4H 9H", new String[]{
                "2H 2S 2D 4H 9H",
                "9C 9S 2C 7H 7D"
        });
    }

    @Test
    public void threeOfAKindBeatsWeakerThreeOfAKind(){
        assertIsStrongest("9C 9S 9D 3H 7D", new String[]{
                "2H 2S 2D AH 9H",
                "9C 9S 9D 3H 7D"
        });
    }

    @Test
    public void straightBeatsThreeOfAKind(){
        assertIsStrongest("2C 3D 4H 5S 6C", new String[]{
                "2C 3D 4H 5S 6C",
                "AC AS AD 3H 7D"
        });
    }

    @Test
    public void straightsAreUntiedByTheHighestCard(){
        assertIsStrongest("3H 4S 5C 6H 7D", new String[]{
                "2C 3D 4H 5S 6C",
                "3H 4S 5C 6H 7D"
        });
    }

    @Test
    public void flushBeatsStraight(){
        assertIsStrongest("2C 7C 4C 5C 8C", new String[]{
                "2C 7C 4C 5C 8C",
                "3H 4S 5D 6H 7D"
        });
    }

    @Test
    public void flushesAreUntiedByTheHighestCard(){
        assertIsStrongest("3D 9D 5D 2D 7D", new String[]{
                "2C 7C 4C 5C 8C",
                "3D 9D 5D 2D 7D"
        });
    }

    private void assertIsStrongest(final String expected, final String[] hands) {
        assertIsStrongestInternal(expected, hands);

        List<String> handsList = Arrays.asList(hands);
        Collections.reverse(handsList);
        String[] reversedHands = handsList.toArray(new String[0]);
        assertIsStrongestInternal(expected, reversedHands);
    }

    private void assertIsStrongestInternal(final String expected, final String[] hands){
        String strongest = Arrays.stream(Hand.parseMultiple(hands))
                .sorted()
                .skip(hands.length - 1)
                .limit(1)
                .findFirst()
                .get()
                .toString();
        assertEquals(expected, strongest);
    }
}
