import java.io.*;
import java.nio.Buffer;
import java.util.*;


public class Day11 {

    public static void main (String[] args) throws IOException {
        long stoneCount = 0L;
        HashMap<Long, Long> stones = new HashMap<>();
        long stone;
        stones.put(125L, 1L);
        stones.put(17L, 1L);



        for (int iteration = 0; iteration < 25; iteration++) {

            ArrayList<Long> stonesList = new ArrayList<>();
            for (HashMap.Entry<Long, Long> entry : stones.entrySet()) {
                stonesList.add(entry.getKey());
            }
            stones.clear();

            for (int index = 0; index < stonesList.size(); index++) {

                long number = stonesList.get(index);
                long count = stones.containsKey(number) ? stones.get(number) : 1;
                int digits = 0;

                if (number == 0) {
                    addStone(stones, 1, count);
                    stoneCount++;
                }
                else {
                    if ((digits = numberOfDigits(number)) % 2 == 0) {
                        double divisor = Math.pow (10, digits);
                        addStone(stones, number / (long)divisor, count);
                        addStone(stones, number % (long) divisor, count);
                        stoneCount += count * 2;
                    } else {
                        addStone(stones, number * 2024, count);
                        stoneCount += count;
                    }
                }


            }
        }
        System.out.println("stoneCount = " + stoneCount);

    }

    public static int numberOfDigits (long number) {

        int digitCounter = 0;
        while (number > 0) {
            number /= 10;
            digitCounter++;
        }
        return digitCounter;
    }

    public static void addStone (HashMap<Long, Long> stones, long number, long add) {

        if (!stones.containsKey(number)) {
            stones.put(number, add);
        } else {
            stones.put(number, stones.get(number) + add);
        }
    }




    static class Stone {

        public long postBlinkStoneCounter;
        int initialNumber;
        public Stone (int initial) {
            this.postBlinkStoneCounter = 1;
            this.initialNumber = initial;
        }
        public void blink (long number, int remainingBlinks) {

            if (remainingBlinks == 0) {
                return;
            }

            int digitCounter = 0;
            long numberCopy = number;
            while (numberCopy > 0) {
                numberCopy /= 10;
                digitCounter++;
            }

            if (number == 0) {
                blink(1, remainingBlinks - 1);
            } else if (digitCounter % 2 == 0) {
                this.postBlinkStoneCounter++;
                blink((long) (number / Math.pow((double) 10, (double) digitCounter / 2)), remainingBlinks - 1);
                blink((long) (number % Math.pow((double) 10, (double) digitCounter / 2)), remainingBlinks - 1);
            } else {
                blink(number * 2024, remainingBlinks - 1);
            }

        /*ArrayList<Long> stones = new ArrayList<>();
        stones.add(number);
        String numberString;

        for (int iteration = 0; iteration < iterations; iteration++) {
            for (int index = 0; index < stones.size(); index++) {
                int digitCount = 0;
                long numbercopy = stones.get(index);
                while (numbercopy > 0) {
                    numbercopy /= 10;
                    digitCount++;
                }


                // if the stone has the number 0, replace it with a stone with the number 1
                if (stones.get(index) == 0) {
                    stones.set(index, 1L);
                }

                // if the number on the stone has an even number of digits, split it in two
                else if (digitCount % 2 == 0 ) {
                    numberString = Long.toString(stones.get(index));
                    stones.set(index, Long.parseLong(numberString.substring(0, numberString.length() / 2)));
                    stones.add(index + 1, Long.parseLong(numberString.substring(numberString.length() / 2)));
                    index++;
                }

                // else multiply the number with 2024
                else {
                    stones.set(index, stones.get(index) * 2024);
                }
            }
        }*/
        }
    }
}




