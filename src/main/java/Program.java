import java.util.Arrays;

public class Program {
    public static void main(String[] args){
        String result = Arrays.stream(Hand.parseMultiple(args))
                .sorted()
                .skip(Math.max(args.length - 1, 0))
                .limit(1)
                .map(Hand::toString)
                .findFirst()
                .orElse("INVALID ARGS");

        System.out.println(result);
    }
}
