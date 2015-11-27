package ua.testprojectsolveast;

import ua.testprojectsolveast.threading.FibCalcImpl;
import ua.testprojectsolveast.threading.PerformanceTesterImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Anton on 03.11.2015.
 */
public class Main {

    private void checkForExit(String line) {
        if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }

    public void run() throws IOException, InterruptedException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\nInput 3 values thought space and press Enter -> '{Fibonacci number} " +
                    "{calculation count} {count thread for calculate}', or input 'exit'/'quit' for close application.");
            System.out.println("For example: 25 40 10.\n");
            String line = in.readLine();
            checkForExit(line);
            try {
                String[] splitLine = line.split(" ");
                if (splitLine.length >= 3) {
                    int[] values = new int[3];
                    int j = 0;
                    for (int i = 0; i < splitLine.length; i++) {
                        checkForExit(splitLine[i]);
                        values[j++] = Integer.valueOf(splitLine[i]);
                    }
                    System.out.println(new PerformanceTesterImpl().runPerformanceTest(() -> new FibCalcImpl().fib(values[0]), values[1], values[2]));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error number. Please try again!\n");
                continue;
            }

        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        new Main().run();
    }

}


