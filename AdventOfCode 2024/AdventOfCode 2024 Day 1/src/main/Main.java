package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    // Calculates and prints to the console the 'total distance' and 'similarity score' between the two lists of the puzzle input
    public static void main(String[] args) {

        final String filePath = "res/PuzzleInputDay01.txt";

        System.out.println(getTotalDistanceBetweenLists(filePath));

        System.out.println(getSimilarityScoreBetweenLists(filePath));
    }

    // calculates the 'distance between both lists' of a given InputFilePath
    private static int getTotalDistanceBetweenLists (String filePath) {
        
        ArrayList<Integer> leftNumbers = new ArrayList<>();
        ArrayList<Integer> rightNumbers = new ArrayList<>();
        
        int totalDistance = 0;

        // add both numbers from every line of the puzzle input to separated lists
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            
            int lineCounter = 0;
            String line;
            String[] numberStrings;

            while ((line = br.readLine()) != null) {
                
                numberStrings = line.split("   ");

                leftNumbers.add(Integer.parseInt(numberStrings[0]));
                rightNumbers.add(Integer.parseInt(numberStrings[1]));
                
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(leftNumbers);
        Collections.sort(rightNumbers);
        
        // count the total distance between both lists
        for (int i = 0; i < leftNumbers.size(); i++) {

            totalDistance += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
        }

        return totalDistance;
    }

    // calculates the 'similarityscore' of a given InputFilePath
    private static int getSimilarityScoreBetweenLists(String filePath) {

        ArrayList<Integer> leftNumbers = new ArrayList<>();
        ArrayList<Integer> rightNumbers = new ArrayList<>();

        int similarityScore = 0;

        // add both numbers from every line of the puzzle input to separated lists
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            int lineCounter = 0;
            String line;
            String[] numberStrings;

            while ((line = br.readLine()) != null) {

                numberStrings = line.split("   ");

                leftNumbers.add(Integer.parseInt(numberStrings[0]));
                rightNumbers.add(Integer.parseInt(numberStrings[1]));

                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberToCheck;
        int appearanceCounter = 0;

        // Count the times every number from the left list appears on the right list and add the product of number and appearances to the similarityScore
        for (Integer leftNumber : leftNumbers) {

            numberToCheck = leftNumber;

            for (Integer rightNumber : rightNumbers) {

                if (numberToCheck == rightNumber) {
                    appearanceCounter++;
                }
            }

            similarityScore += numberToCheck * appearanceCounter;

            appearanceCounter = 0;
        }

        return similarityScore;
    }
}