import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hand implements Comparable<Hand> {
    private static final String CARD_DELIMITER = " ";
    private static final int EQUAL_COMPARISON = 0;
    private static final int CARDS_PER_HAND = 5;
    private static final int[] EXPECTED_FULL_GROUP_SIZES = {2, 3};


    private final Card[] cards;

    private Hand(Card[] cards){
        if (cards.length != CARDS_PER_HAND){
            throw new IllegalArgumentException("Invalid number or cards in Hand");
        }

        if (Arrays.stream(cards).distinct().count() != CARDS_PER_HAND){
            throw new IllegalArgumentException("Found duplicate cards in Hand");
        }

        this.cards = cards;
    }

    @Override
    public String toString() {
        return Arrays.stream(cards)
                .map(Card::toString)
                .collect(Collectors.joining(CARD_DELIMITER));
    }

    public static Hand parse(final String encodedHand){
        Card[] cards = Arrays.stream(encodedHand.split(CARD_DELIMITER))
                .map(Card::parse)
                .toArray(Card[]::new);
        return new Hand(cards);
    }

    public static Hand[] parseMultiple(final String[] encodedHands){
        Hand[] hands = Arrays.stream(encodedHands)
                .map(Hand::parse)
                .toArray(Hand[]::new);

        long uniqueCards = Arrays.stream(hands)
                .map(h -> h.cards)
                .flatMap(Arrays::stream)
                .distinct()
                .count();

        if (uniqueCards != hands.length * CARDS_PER_HAND){
            throw new IllegalArgumentException("Found duplicate cards in set");
        }

        return hands;
    }

    public static int compare(Hand h1, Hand h2){
        return h1.compareTo(h2);
    }

    private interface HandComparisonRule {
        int compare(Hand h1, Hand h2);
    }

    @Override
    public int compareTo(Hand that) {
        HandComparisonRule[] rules = new HandComparisonRule[]{
                Hand::compareForStraightFlush,
                Hand::compareForFourOfAKind,
                Hand::compareForFullHouse,
                Hand::compareForFlush,
                Hand::compareForStraight,
                Hand::compareForThreeOfAKind,
                Hand::compareForPairs,
                Hand::compareForHighestValue
        };

        return Arrays.stream(rules)
                .map(r -> r.compare(this, that))
                .filter(c -> c != EQUAL_COMPARISON)
                .findFirst()
                .orElse(EQUAL_COMPARISON);
    }

    private static int compareForFullHouse(Hand h1, Hand h2) {
        int s1 = h1.isFullHouse() ? 1 : 0;
        int s2 = h2.isFullHouse() ? 1 : 0;

        return Integer.compare(s1, s2);
    }

    private static int compareForStraightFlush(Hand h1, Hand h2) {
        int s1 = h1.isStraightFlush() ? 1 : 0;
        int s2 = h2.isStraightFlush() ? 1 : 0;

        return Integer.compare(s1, s2);
    }

    private static int compareForFlush(Hand h1, Hand h2){
        int s1 = h1.isFlush() ? 1 : 0;
        int s2 = h2.isFlush() ? 1 : 0;

        return Integer.compare(s1, s2);
    }

    private static int compareForStraight(Hand h1, Hand h2){
        int s1 = h1.isStraight() ? 1 : 0;
        int s2 = h2.isStraight() ? 1 : 0;

        return Integer.compare(s1, s2);
    }

    private static int compareForFourOfAKind(Hand h1, Hand h2){
        return compareForEquivalentGroups(h1, h2, 4);
    }

    private static int compareForThreeOfAKind(Hand h1, Hand h2){
        return compareForEquivalentGroups(h1, h2, 3);
    }

    private static int compareForPairs(Hand h1, Hand h2){
        return compareForEquivalentGroups(h1, h2, 2);
    }

    private static int compareForEquivalentGroups(Hand h1, Hand h2, int groupSize){
        int[] groups1 = h1.equivalentValuesSortedDesc(groupSize);
        int[] groups2 = h2.equivalentValuesSortedDesc(groupSize);

        int countComparison = Integer.compare(groups1.length, groups2.length);
        if (countComparison != EQUAL_COMPARISON){
            return countComparison;
        }

        return IntStream.range(0, groups1.length)
                .map(i -> Integer.compare(groups1[i], groups2[i]))
                .filter(i -> i != EQUAL_COMPARISON)
                .findFirst()
                .orElse(EQUAL_COMPARISON);
    }

    private static int compareForHighestValue(Hand h1, Hand h2){
        int[] h1Values = h1.getValuesSortedDesc();
        int[] h2Values = h2.getValuesSortedDesc();

        return IntStream.range(0, CARDS_PER_HAND)
                .map(i -> Integer.compare(h1Values[i], h2Values[i]))
                .filter(i -> i != EQUAL_COMPARISON)
                .findFirst()
                .orElse(EQUAL_COMPARISON);
    }

    private int[] equivalentValuesSortedDesc(int groupSize){
        Map<Integer, List<Card>> equivalentCards = getEquivalentCards();

        return equivalentCards
                .keySet()
                .stream()
                .filter(k -> equivalentCards.get(k).size() == groupSize)
                .sorted((i, j) -> Integer.compare(j, i))
                .mapToInt(i -> i)
                .toArray();
    }

    private boolean isFullHouse(){
        int[] groupSizes = getEquivalentCards()
                .values()
                .stream()
                .map(l -> l.size())
                .sorted()
                .mapToInt(i -> i)
                .toArray();
        return Arrays.equals(EXPECTED_FULL_GROUP_SIZES, groupSizes);
    }

    private Map<Integer, List<Card>> getEquivalentCards() {
        return Arrays.stream(cards)
                .collect(Collectors.groupingBy(Card::getNumericValue));
    }

    private boolean isStraightFlush(){
        return isStraight() && isFlush();
    }

    private boolean isFlush(){
        long uniqueClubs = Arrays.stream(cards)
                .map(Card::getClub)
                .distinct()
                .count();
        return uniqueClubs == 1;
    }

    private boolean isStraight(){
        int[] handValues = getValuesSortedDesc();
        return IntStream.range(0, handValues.length - 1)
                .map(i -> handValues[i] - handValues[i + 1])
                .allMatch(i -> i == 1);
    }

    private int[] getValuesSortedDesc(){
        return Arrays.stream(cards)
                .map(Card::getNumericValue)
                .sorted((i, j) -> Integer.compare(j, i))
                .mapToInt(i -> i)
                .toArray();
    }
}