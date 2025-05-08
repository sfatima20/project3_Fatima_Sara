import java.io.*;
import java.util.*;
import javax.swing.*;

public class FatimaKnapsackApp {
    private static final String INPUT_FILE = "fatima_knapsack_input.txt";
    private static final String OUTPUT_FILE = "fatima_knapsack_output.txt";
    private static final String BENCHMARK_FILE = "fatima_knapsack_benchmark.txt";

    public static void main(String[] args) {
        try {
            List<SaraItemType> items = new ArrayList<>();
            int W = readInput(items);
            
            runAllMethods(W, items);
            runBenchmarks();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int readInput(List<SaraItemType> items) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(INPUT_FILE));
        int W = scanner.nextInt();
        
        while (scanner.hasNext()) {
            String id = scanner.next();
            int weight = scanner.nextInt();
            int profit = scanner.nextInt();
            items.add(new SaraItemType(id, weight, profit));
        }
        scanner.close();
        return W;
    }

    private static void runAllMethods(int W, List<SaraItemType> items) throws FileNotFoundException {
        StringBuilder output = new StringBuilder();
        output.append("Knapsack Problem Solution - Multiple Methods\n");
        output.append("Capacity: ").append(W).append("\n\n");
        output.append("Available Items (").append(items.size()).append("):\n");
        for (SaraItemType item : items) {
            output.append("  ").append(item).append("\n");
        }
        
        // Dynamic Programming
        output.append("\n=== Dynamic Programming Solution ===\n");
        long startDP = System.nanoTime();
        CS324KnapsackADT.KnapsackResult dpResult = CS324KnapsackADT.solve(W, items, true);
        long endDP = System.nanoTime();
        output.append(dpResult);
        output.append(String.format("\nExecution Time: %.3f ms", (endDP - startDP)/1e6));
        
        // Greedy Approximation
        output.append("\n\n=== Greedy Approximation ===\n");
        long startGreedy = System.nanoTime();
        CS324KnapsackADT.KnapsackResult greedyResult = CS324KnapsackADT.solveGreedy(W, items);
        long endGreedy = System.nanoTime();
        output.append(greedyResult);
        output.append(String.format("\nExecution Time: %.3f ms", (endGreedy - startGreedy)/1e6));
        output.append(String.format("\nApproximation Ratio: %.2f%%", 
            100.0 * greedyResult.getMaxProfit() / dpResult.getMaxProfit()));
        
        // Write to file and console
        System.out.println(output);
        writeToFile(output.toString(), OUTPUT_FILE);
    }

    private static void runBenchmarks() throws FileNotFoundException {
        StringBuilder benchmark = new StringBuilder();
        benchmark.append("Knapsack Algorithm Benchmark\n");
        benchmark.append("Size\tDP Time(ms)\tGreedy Time(ms)\tDP Profit\tGreedy Profit\n");
        
        Random rand = new Random();
        int[] sizes = {10, 20, 30, 40, 50, 100, 200};
        
        for (int size : sizes) {
            List<SaraItemType> items = generateRandomItems(size, rand);
            int W = size * 10;
            
            long startDP = System.nanoTime();
            CS324KnapsackADT.KnapsackResult dpResult = CS324KnapsackADT.solve(W, items, false);
            long endDP = System.nanoTime();
            
            long startGreedy = System.nanoTime();
            CS324KnapsackADT.KnapsackResult greedyResult = CS324KnapsackADT.solveGreedy(W, items);
            long endGreedy = System.nanoTime();
            
            benchmark.append(String.format("%d\t%.3f\t%.3f\t%d\t%d\n", 
                size,
                (endDP - startDP)/1e6,
                (endGreedy - startGreedy)/1e6,
                dpResult.getMaxProfit(),
                greedyResult.getMaxProfit()));
        }
        
        writeToFile(benchmark.toString(), BENCHMARK_FILE);
        System.out.println("\nBenchmark results written to: " + BENCHMARK_FILE);
    }

    private static List<SaraItemType> generateRandomItems(int count, Random rand) {
        List<SaraItemType> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int weight = rand.nextInt(20) + 1;
            int profit = rand.nextInt(100) + 1;
            items.add(new SaraItemType("Item" + (i+1), weight, profit));
        }
        return items;
    }

    private static void writeToFile(String content, String filename) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename);
        writer.print(content);
        writer.close();
    }
}