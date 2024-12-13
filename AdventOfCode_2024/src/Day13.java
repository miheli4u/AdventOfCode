import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    public static void main(String[] args) {

        // initiate the cost variables
        long totalCost = 0;
        List<Long> costs = new ArrayList<>();

        // true for part II
        boolean prizeCoordIncrease = true;
        long add = 0;
        if (prizeCoordIncrease) {
            add = 10000000000000L;
        }

        // initiate the claw machine variables
        int axIncrement = 0;
        int ayIncrement = 0;
        int bxIncrement = 0;
        int byIncrement = 0;
        long prizeX = 0;
        long prizeY = 0;

        // initiate the variables for reading the given puzzle input and save the numbers in the variables
        String filePath = "res/PuzzleInputDay13.txt";
        String line;
        int clawComponentIndex = 0;
        Pattern pattern = Pattern.compile("[0-9]{1,6}");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                // save the A-button variables
                if (clawComponentIndex == 0) {
                    Matcher matcher = pattern.matcher(line);
                    matcher.find();
                    axIncrement = Integer.parseInt(matcher.group());
                    matcher.find();
                    ayIncrement = Integer.parseInt(matcher.group());
                    clawComponentIndex++;

                }
                // save the B-button variables
                else if (clawComponentIndex == 1) {

                    Matcher matcher = pattern.matcher(line);
                    matcher.find();
                    bxIncrement = Integer.parseInt(matcher.group());
                    matcher.find();
                    byIncrement = Integer.parseInt(matcher.group());
                    clawComponentIndex++;

                }
                // save the prize variables
                else if (clawComponentIndex == 2) {

                    Matcher matcher = pattern.matcher(line);
                    matcher.find();
                    prizeX = Integer.parseInt(matcher.group()) + add;
                    matcher.find();
                    prizeY = Integer.parseInt(matcher.group()) + add;
                    clawComponentIndex++;

                    // calculate the token cost
                    costs.add(solve(axIncrement, ayIncrement, bxIncrement, byIncrement, prizeX, prizeY));

                }
                // reset the counter for the components of the claw
                else if (clawComponentIndex == 3) {
                    clawComponentIndex = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // sum up the costs and print the solution
        for (int i = 0; i < costs.size(); i++) {
            totalCost += costs.get(i);
        };
        System.out.println("Max prizes: " + costs.size());
        System.out.println("Min total cost: " + totalCost);
    }

    //  A method to calculate the fewest tokens need for the given claw machine
    public static long solve(int aX, int aY, int bX, int bY, long prizeX, long prizeY) {

        // calculate b, then fill the leftover distance with presses on the A-button
        long b = (prizeX * aY - prizeY * aX) / (bX * aY - bY * aX);

        long rest = prizeX - (b * bX);

        long numerator;
        long denominator;
        long a;

        if (aX == 0) {
            numerator = prizeY;
            denominator = aY;

        } else {
            numerator = rest;
            denominator = aX;
        }

        a = numerator / denominator;

        // return the tokens needed or zero if its not solvable
        if (a * aY + b * bY == prizeY && a * aX + b * bX == prizeX && numerator % denominator == 0) {
            return 3 * a + b;
        } else {
            return 0;
        }
    }
}
