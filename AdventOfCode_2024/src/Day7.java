import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day7 {

    /**
     * A method to calculate which calibrations are solvable with different operators like + and * (and concatenation)
     * @param filePath filepath of the textfile which contains the calibrations from the AoC Puzzleinput
     * @param includeConcat true if the method should also check if calibrations are solvable with concatenation
     * @return the sum of the evaluation values of the solvable calibrations
     */
    public static long calcTotalCalibrations (String filePath, boolean includeConcat) {

        long totalCalibrations = 0;

        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // read every line from the given textfile
            while ((line = br.readLine()) != null) {

                // parse the linestring into numbers
                String[] parts = line.split(": ");
                long target = Long.parseLong(parts[0]);
                long[] nums = Arrays.stream(parts[1].split(" ")).mapToLong(Long::parseLong).toArray();

                // if the calibration is solvable, add the value to the sum
                if (canProduceTarget(target, nums, includeConcat)) {
                    totalCalibrations += target;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalCalibrations;
    }

    /**
     * A method to set the starting values for the recursive function evaluate to check if the calibration is solvable
     * @param target the left hand side from the calibration (equation)
     * @param nums the array with the given numbers (the right hand side of the calibration (equation))
     * @param includeConcat true if concatenation should be included in the process
     * @return returns true if the calibration was solvable
     */
    private static boolean canProduceTarget (long target, long[] nums, boolean includeConcat) {

        // call evaluate with index 0 and the first number of the right hand side
        return evaluate(target, nums, 0, nums[0], includeConcat);
    }

    /**
     * A method which uses recursive backtracking to try every combination of operators to check if the calibration is solvable
     * @param target the left hand side from the calibration (equation)
     * @param nums the array with the given numbers (the right hand side of the calibration (equation))
     * @param index the index of the current number to work with
     * @param currentValue the current value of the evaluation with the different operators
     * @param includeConcat true if concatenation should be included in the process
     * @return true if the calibration was solvable
     */
    private static boolean evaluate (long target, long[] nums, int index, long currentValue, boolean includeConcat) {

        // return to higher recursion level if it's the last number
        if (index == nums.length - 1) {
            return currentValue == target;
        }
        // try to add the next number: if the calibration was solved, return true
        if (evaluate(target, nums, index + 1, currentValue + nums[index + 1], includeConcat)) {
            return true;
        }

        // try to multiply the next number: if the calibration was solved, return true
        if (evaluate(target, nums, index + 1, currentValue * nums[index + 1], includeConcat)) {
            return true;
        }

        // try to concat (||) the next number if includeConcat is true: if the calibration was solved, return true
        if (includeConcat) {
            long concatenatedValue = Long.parseLong(currentValue + "" + nums[index + 1]);
            return evaluate(target, nums, index + 1, concatenatedValue, true);
        }
        // return false, if it wasn't solvable
        return false;
    }
}
