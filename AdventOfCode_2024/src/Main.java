public class Main {

    public static void main(String[] args) {

        final String filePath = "res/PuzzleInputDay3.txt";

//        System.out.println("The sum of the multiplications is: " + Day3.calcProductSumFromCorruptedMemory(filePath));

        System.out.println("The sum of the multiplications with switches is: " + Day3.calcProductSumFromCorruptedMemoryWithSwitches(filePath));
    }
}
