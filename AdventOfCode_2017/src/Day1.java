import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Day1 {

    // find alliterations in a given circular list and return the sum of their digits
    public static int calcMatchingDigitsSum (String filePath) {

        int sum = 0;
        int number = -1;

        String line = "";

        // load the circular list from the given file into a string
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            line = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        char firstChar;
        char secondChar;
        int lineLen = line.length();

        // for every digit in the circular list: if the digit and its follower are equal, add it to the sum
        for (int charIndex = 0; charIndex < lineLen; charIndex++) {
            firstChar = line.charAt(charIndex);
            secondChar = line.charAt((charIndex + 1) % lineLen);

            if (firstChar == secondChar) {
                sum += Integer.parseInt(String.valueOf(firstChar));
            }
        }
        return sum;
    }

    // find matching digits with half the list-entries distance to each other and return their sum
    public static int calcHalfAroundMatchingDigitsSum (String filePath) {

        int sum = 0;
        int number = -1;

        String line = "";

        // try to read the given circular list out of a txt-file with the given filePath and save it in a String
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            line = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int lineLen = line.length();
        int halfway = lineLen / 2;

        char firstChar;
        char halfwayChar;

        // for every char in given circular list: if the char and its 'halfway-counterpart' are equal, add them to the sum
        for (int charIndex = 0; charIndex < lineLen; charIndex++) {
            firstChar = line.charAt(charIndex);
            halfwayChar = line.charAt((charIndex + halfway) % lineLen);

            if (firstChar == halfwayChar) {
                sum += Integer.parseInt(String.valueOf(firstChar));
            }
        }
        return sum;
    }
}
