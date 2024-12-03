import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2 {

    // checks the total amount of safe reports of a given list of reports
    public static int calcTotalOfSafeReports (String filePath) {

        int safeReportTotal = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String[] reportArray;

            while ((line = br.readLine()) != null) {

                reportArray = line.split(" ");
                ArrayList<Integer> levelList = new ArrayList<>();

                for (int i = 0; i < reportArray.length; i++) {
                    levelList.add(Integer.parseInt(reportArray[i]));
                }

                if (isReportSafe(levelList)) {
                    safeReportTotal++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return safeReportTotal;
    }

    // checks the total amount of safe reports of a given list of reports with the possibility to remove one unsafe level of the report
    public static int calcTotalOfSafeReportsWithProblemDampener (String filePath) {

        int safeReportTotal = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String[] reportArray;

            while ((line = br.readLine()) != null) {

                reportArray = line.split(" ");
                ArrayList<Integer> levelList = new ArrayList<>();

                for (int i = 0; i < reportArray.length; i++) {
                    levelList.add(Integer.parseInt(reportArray[i]));
                }

                for (int index = 0; index < levelList.size(); index++) {

                    Integer removedInt = levelList.remove(index);

                    if (isReportSafe(levelList)) {
                        safeReportTotal++;
                        break;
                    }

                    levelList.add(index, removedInt);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return safeReportTotal;
    }

    // checks if the given levels are constantly in- or decreasing by 1-3
    private static boolean isReportSafe (ArrayList<Integer> levelList) {

        int levelDelta;
        int deltaSign = 0;

        for (int i = 1; i < levelList.size(); i++) {

            levelDelta = levelList.get(i) - levelList.get(i - 1);

            // report is unsafe if the value in- or decreases by 0 or more than 3
            if (levelDelta < -3 || levelDelta > 3 || levelDelta == 0) {
                return false;
            } else if (levelDelta < 0) {
                switch (deltaSign) {
                    case 1:
                        return false;
                    case -1:
                        continue;
                    case 0:
                        deltaSign = -1;
                }
            } else if (levelDelta > 0) {
                switch (deltaSign) {
                    case -1:
                        return false;
                    case 1:
                        continue;
                    case 0:
                        deltaSign = 1;
                }
            }
        }
        return true;
    }
}
