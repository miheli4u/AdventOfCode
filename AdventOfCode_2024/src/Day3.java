import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day3 {

    // finds appearances of the form "mul(num1,num2)" in a textFile and returns the sum of all products
    public static int calcProductSumFromCorruptedMemory(String filePath) {

        int productSum = 0;

        // regular expression to find mul(x,y)
        Pattern regex = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)");

        // regular expression to isolate the numbers from the mul()-function call
        Pattern regex2 = Pattern.compile("[0-9]{1,3},[0-9]{1,3}");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            
            while ((line = br.readLine()) != null) {

                Matcher matcher = regex.matcher(line);

                ArrayList<String> lineMatches = new ArrayList<String>();

                // add all found mul()-calls to a list
                while (matcher.find()) {
                    lineMatches.add(matcher.group());
                }

                Matcher matcher2;
                String[] numbers = new String[2];

                // isolate both numbers from all mul()-calls and add their product to the sum
                for (String s : lineMatches) {

                    matcher2 = regex2.matcher(s);

                    while (matcher2.find()) {

                        numbers = matcher2.group().split(",");
                    }
                    productSum += (Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productSum;
    }

    // finds appearances of "do()" and "don't()" and adds up only the product of the chosen multiplications
    public static int calcProductSumFromCorruptedMemoryWithSwitches(String filePath) {

        int productSum = 0;
        boolean isEnabled = true;

        // regular expression to find mul(x,y), do() and don't()
        Pattern regex = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)");

        // regular expression to isolate the numbers from the mul()-function call
        Pattern regex2 = Pattern.compile("[0-9]{1,3},[0-9]{1,3}");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            // search for the regular expressions in every line of the textfile
            while ((line = br.readLine()) != null) {

                Matcher matcher = regex.matcher(line);
                Matcher matcher2;
                String found;
                String[] numbers = new String[2];

                // handle every found expression
                ArrayList<String> lineMatches = new ArrayList<String>();
                while (matcher.find()) {
                    found = matcher.group();

                    // if the found expression is a do() -> enable the following multiplications
                    if (found.equals("do()") ) {
                        isEnabled = true;
                        System.out.println("isEnabled = true");

                    }
                    // if the found expression is a don't() -> disable the following multiplications
                    else if (found.equals("don't()")) {
                        isEnabled = false;
                        System.out.println("isEnabled = false");
                    }
                    // isolate both numbers from the mul()-call and add their product to the sum if isEnabled
                    else {
                        matcher2 = regex2.matcher(found);
                        matcher2.find();
                        numbers = matcher2.group().split(",");
                        if (isEnabled) {
                            productSum += (Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]));
                        }
                    }

                }
            }
        } catch(IOException e) {
                    e.printStackTrace();
                }
        return productSum;
    }
}