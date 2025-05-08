import java.util.*;

public class KnapsackTest {
    public static void main(String[] args) {
        testTextbookExample();
        testLargeCases();
        testEdgeCases();
    }
    
    private static void testTextbookExample() {
        System.out.println("=== Textbook Example Test ===");
        List<SaraItemType> items = Arrays.asList(
            new SaraItemType("Item1", 5, 50),
            new SaraItemType("Item2", 10, 60),
            new SaraItemType("Item3", 20, 140)
        );
        testCase(30, items, "Textbook Example");
    }
    
    private static void testLargeCases() {
        System.out.println("\n=== Large Case Test ===");
        Random rand = new Random();
        for (int size : new int[]{50, 100, 200}) {
            List<SaraItemType> items = generateRandomItems(size, rand);
            testCase(size * 10, items, "Size " + size + " Case");
        }
    }
    
    private static void testEdgeCases() {
        System.out.println("\n=== Edge Case Test ===");
        // Zero capacity
        testCase(0, Arrays.asList(new SaraItemType("Item1", 1, 10)), "Zero Capacity");
        
        // Single item that fits
        testCase(5, Arrays.asList(new SaraItemType("Item1", 5, 10)), "Single Fitting Item");
        
        // Single item that doesn't fit
        testCase(4, Arrays.asList(new SaraItemType("Item1", 5, 10)), "Single Non-Fitting Item");
    }
    
    private static void testCase(int W, List<SaraItemType> items, String description) {
        System.out.println("\nTesting: " + description);
        System.out.println("Capacity: " + W);
        System.out.println("Items: " + items.size());
        
        System.out.println("\nDynamic Programming:");
        // Pass false for showDPTable to avoid showing GUI during tests
        CS324KnapsackADT.KnapsackResult dpResult = CS324KnapsackADT.solve(W, items, false);
        System.out.println(dpResult);
        
        System.out.println("\nGreedy Approximation:");
        CS324KnapsackADT.KnapsackResult greedyResult = CS324KnapsackADT.solveGreedy(W, items);
        System.out.println(greedyResult);
        
        if (dpResult.getMaxProfit() > 0) {
            System.out.printf("Approximation Ratio: %.2f%%\n",
                100.0 * greedyResult.getMaxProfit() / dpResult.getMaxProfit());
        }
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
}