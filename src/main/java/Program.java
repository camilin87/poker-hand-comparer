import java.util.Arrays;

public class Program {
    public static void main(String[] args){
        System.out.println("Poker Hand Evaluator");

        System.out.println("Parsing:");
        Arrays.stream(args).forEach(System.out::println);

        String result = Arrays.stream(Hand.parseMultiple(args))
                .sorted()
                .skip(Math.max(args.length - 1, 0))
                .limit(1)
                .map(Hand::toString)
                .findFirst()
                .orElse("INVALID ARGS");

        System.out.println("--");
        System.out.println(result);
    }
}
