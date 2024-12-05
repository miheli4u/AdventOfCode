import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Day5 {

    // finds the sum of the middle numbers of correctly-ordered update pages in a given file
    public static int findSumOfMiddleNumbersOfCorrectlyOrderedUpdates (String filePath) {

        int sum = 0;
        int sumOfFalse = 0;

        // read the file given and construct a list with rules and another one with the updates
        String line;

        boolean isFirstPartOver = false;
        String[] stringArr = new String[2];
        ArrayList<String[]> ruleList = new ArrayList<>();

        ArrayList<String[]> updateList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                if (line.equals("")) {
                    isFirstPartOver = true;
                    continue;
                }

                // if the reader is the first part, add the numbers to the rulelist, to the updatelist otherwise
                if (!isFirstPartOver) {
                    ruleList.add(line.split("\\|"));
                } else {
                    updateList.add(line.split(","));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] update;
        String[] rule;
        boolean isUpdateCorrect = true;

        // for every update and rule: if a rule is broken: let 'isRuleBroken()' find and fix it
        for (int updateIndex = 0; updateIndex < updateList.size(); updateIndex++) {

            isUpdateCorrect = true;
            update = updateList.get(updateIndex);

            for (int ruleIndex = 0; ruleIndex < ruleList.size(); ruleIndex++) {

                rule = ruleList.get(ruleIndex);

                // 'isRuleBroken()' returns true if a rule is broken and fixes it
                if (isRuleBroken(update, rule)) {
                    isUpdateCorrect = false;
                    // restart checking the rules in case earlier rules could've been broken by the 'fix'
                    ruleIndex = 0;
                }
            }

            // find the middle number and add it either to the sum of the correct or the sum of the rule-breaking updates
            int toAdd = Integer.parseInt(update[(update.length / 2)]);
            if (isUpdateCorrect) {
                sum += toAdd;
            } else {
                sumOfFalse += toAdd;
            }
        }

        System.out.println("The sum of the middle numbers of the repaired updates is: " + sumOfFalse);
        return sum;
    }

    // checks if the given update breaks the given rule and repairs the update order if so
    public static boolean isRuleBroken (String[] update, String[] rule) {

        String ruleNum1 = rule[0];
        String ruleNum2 = rule[1];

        // check if the second number from the rule appears in the update pages
        for (int pageIndex = 0; pageIndex < update.length - 1; pageIndex++) {

            if (update[pageIndex].equals(ruleNum2)) {
                
                // check if the first number of the rule appears after the second and is therefore a rulebreak
                for (int pageIndex2 = pageIndex + 1; pageIndex2 < update.length; pageIndex2++) {
                    
                    // if the rule is broken, swap both numbers to repair the update order
                    if (update[pageIndex2].equals(ruleNum1)) {

                        String temp = update[pageIndex];
                        update[pageIndex] = update[pageIndex2];
                        update[pageIndex2] = temp;

                        return true;
                    }
                }
            }
        }
        return false;
    }
}
