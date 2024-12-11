import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String testString = "100010";

        System.out.println(Integer.parseInt(testString.substring(0, testString.length() / 2)));

        System.out.println(Integer.parseInt(testString.substring(testString.length() / 2 + 1)));
    }
}
