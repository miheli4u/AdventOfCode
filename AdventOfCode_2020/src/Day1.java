import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1 {

    // find two numbers which add up to 2020 in a given list and return their product
    public static int calcSummandProduct (String filePath) {

        String numberString;
        ArrayList<Integer> numList = new ArrayList<>();
        int numToFind;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // add every number in the given file to a list
            while ((numberString = br.readLine()) != null) {

                numList.add(Integer.parseInt(numberString));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // for every number: if the other needed summand is in the list return both numbers product
        for (Integer numToCheck: numList) {
            numToFind = 2020 - numToCheck;
            if (numList.contains(numToFind)) {
                return numToCheck * numToFind;
            }
        }
        return -1;
    }

    // find the product of three summands that add up to 2020 in a given textfile
    public static int calcThreeSummandsProduct (String filePath) {

        int sumToFind = 2020;

        String numberString;
        ArrayList<Integer> numList = new ArrayList<>();
        int numToFind;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // add every number in the given file to a list
            while ((numberString = br.readLine()) != null) {

                numList.add(Integer.parseInt(numberString));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // find the summand trio which add up to 2020
        for (int index1 = 0; index1 < numList.size(); index1++) {
            for (int index2 = 1; index2 < numList.size(); index2++) {
                numToFind = sumToFind - numList.get(index1) - numList.get(index2);

                // if the first two numbers aren't already too big, check the list for a third summand
                if (numToFind <= 0) {
                    continue;
                } else {
                    for (int index3 = index2 + 1; index3 < numList.size(); index3++) {
                        if (numList.get(index3) == numToFind) {
                            return numList.get(index1) * numList.get(index2) * numList.get(index3);
                        }
                    }
                }
            }
        }
        return -1;
    }
}
