import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day9 {

    /**
     * A method to compact memoryfiles of a given diskmap from a textfile and calculate the checksum
     * @param filePath filepath to the given textfile of the diskmap
     * @param movingWholeFiles true if the method should move a whole block of the same fileID or one file at a time
     * @return returns the checksum of the compacted memory which is the sum of positionIndex times fileID number
     * @throws IOException if the textfile couldn't be read
     */
    public static long calcCheckSumOfCompactedMemory (String filePath, boolean movingWholeFiles) throws IOException {

        // initialize the checksum
        long checkSum = 0;

        // read the diskMap from the given textfile
        String diskMap = "";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        diskMap = br.readLine();


        // convert the diskmap into a memory list which contains the files and the freespaces
        ArrayList<Object> memoryList = new ArrayList<>();

        for (int id = 0; id * 2 < diskMap.length(); id++) {

            // add the files with the right amount and id's to the memory list
            int blockAmount = Integer.parseInt(String.valueOf(diskMap.charAt(id * 2)));

            for (int i = 0; i < blockAmount; i++) {
                memoryList.add(id);
            }

            // add the right amount of free spaces in between
            if (((id * 2) + 1) < diskMap.length()) {
                blockAmount = Integer.parseInt(String.valueOf(diskMap.charAt((id * 2) + 1)));

                for (int diskIndex = 0; diskIndex < blockAmount; diskIndex++) {
                    memoryList.add('.');
                }
            }
        }
        Object[] arr = memoryList.toArray();


        // compact the memory by moving whole fileID-blocks or one file at a time
        if (movingWholeFiles) {

            int blockSize = 0;
            int fileBlockBeginIndex = arr.length - 1;
            Object currFileID = arr[fileBlockBeginIndex];
            int freeSpaceCounter = 0;

            // iterate backwards through the list and move the blocks to free spaces to the left
            for (int arrIndex = arr.length - 1; arrIndex > 0; arrIndex--) {

                // skip through the memory as long as it is free space
                if (arr[arrIndex].equals('.')) {
                    continue;
                }

                // count the blocksize while the id stays the same
                else if (arr[arrIndex].equals(currFileID)) {
                    blockSize++;
                }

                // start searching for free disk space from left to right as soon as the file-block is over
                else {

                    for (int freeIndex = 0; freeIndex < fileBlockBeginIndex; freeIndex++) {

                        // count the free space
                        if (arr[freeIndex].equals('.')) {
                            freeSpaceCounter++;

                            // move the whole file-block to the new space if there are enough free blocks
                            if (freeSpaceCounter == blockSize) {
                                for (int i = 0; i < blockSize; i++) {
                                    arr[freeIndex - i] = currFileID;
                                    arr[fileBlockBeginIndex - i] = '.';
                                }
                                break;
                            }
                        }
                        // reset the timer if the block of free spaces wasn't large enough
                        else {
                            freeSpaceCounter = 0;
                        }
                    }

                    // reset the counters for the next file-block
                    fileBlockBeginIndex = arrIndex;
                    blockSize = 1;
                    currFileID = arr[arrIndex];
                    freeSpaceCounter = 0;

                }
            }
        }

        // move one file at a time
        else {

            int noSpaceBeforeThis = 0;

            // iterate backwards through the memory until the last spot that a memory was moved to compact it
            for (int j = arr.length - 1; j > noSpaceBeforeThis; j--) {

                // search for the next free space
                for (int k = noSpaceBeforeThis; k < j; k++) {

                    // move the file to the free space
                    if (arr[k].equals('.')) {
                        arr[k] = arr[j];
                        arr[j] = '.';
                        noSpaceBeforeThis = k;
                        break;
                    }
                }
            }
        }

        // calculate the checkSum
        for (int index = 0; index < arr.length; index++) {
            Object nextChar = arr[index];
            if (nextChar.equals('.')) {
            } else {
                int fileID = Integer.parseInt(String.valueOf(nextChar));
                int product = index * fileID;
                checkSum += product;
            }
        }

        return checkSum;
    }
}
