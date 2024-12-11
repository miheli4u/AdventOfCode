import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;


public class Day11 {

    public static void main (String[] args) throws IOException {

        String input = "res/PuzzleInputDay11.txt";
        String output = "res/PuzzleOutputDay11.txt";

        int timesToBlink = 75;
        int chunksize = 100000;

        long[] numbers;

        // initial Reading
        BufferedReader br = new BufferedReader(new FileReader(input));

        numbers = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        ArrayList<Long> newStones = new ArrayList<>();

        for (int l = 0; l < numbers.length; l++) {
            newStones.add(numbers[l]);
        }
        // initial blink
        newStones = blink(newStones);

        BufferedWriter bw = new BufferedWriter(new FileWriter(output));

        // write the converted list to the output
        for (int m = 0; m < newStones.size(); m++) {
            bw.write(String.valueOf(newStones.get(m)) + "\n");
        }

        // swap in- and output
        String temp = input;
        input = output;
        output = temp;

        for (int n = 0; n < timesToBlink - 1; n++) {
            processChunks(input, output, chunksize);

            // swap in- and output
            temp = input;
            input = output;
            output = temp;
        }

        /*for (int k = 0; k < newStones.size(); k++) {
            System.out.println(newStones.get(k) + ",");
        }

        System.out.println("newStonesLen = " + newStones.size());*/
    }

    public static void processChunks (String fileInput, String fileOutput, int chunksize) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileInput));
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileOutput));

        String number;
        ArrayList<Long> chunk = new ArrayList<>();
        int chunkElementCounter = 0;

        while ((number = br.readLine()) != null) {

            if (chunkElementCounter < chunksize) {
                chunk.add(Long.parseLong(number));
            } else {
                writeChunk(bw, blink(chunk));
                chunk.clear();
            }


        }
    }

    public static void writeChunk (BufferedWriter bw, ArrayList<Long> chunk) throws IOException{

        for (int index = 0; index < chunk.size(); index++) {
            bw.write(String.valueOf(chunk.get(index)));
            bw.newLine();
        }
    }

    public static ArrayList<Long> blink (ArrayList<Long> stones) {

        String number;

        for (int i = 0; i < stones.size(); i++) {

            // if the stone has the number 0, replace it with a stone with the number 1
            if (stones.get(i) == 0) {
                stones.set(i, 1L);
            }

            // if the number on the stone has an even number of digits, split it in two
            else if ((number = Long.toString(stones.get(i))).length() % 2 == 0 ) {

                stones.set(i, Long.parseLong(number.substring(0, number.length() / 2)));
                stones.add(i + 1, Long.parseLong(number.substring(number.length() / 2)));
                i++;

            }

            // else multiply the number with 2024
            else {
                stones.set(i, stones.get(i) * 2024);
            }
        }

        return stones;
    }
}
