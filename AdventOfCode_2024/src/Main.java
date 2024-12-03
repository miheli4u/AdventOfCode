public class Main {

    public static void main(String[] args) {

        final String filePath = "res/PuzzleInputDay2.txt";

        System.out.println("The total of safe Reports is: " + Day2.calcTotalOfSafeReports(filePath));

        System.out.println("The total of safe Reports with Problem Dampener is: " + Day2.calcTotalOfSafeReportsWithProblemDampener(filePath));

    }
}
