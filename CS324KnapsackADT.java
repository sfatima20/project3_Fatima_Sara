import java.util.*;
import javax.swing.*;

public class CS324KnapsackADT {
    public static class KnapsackResult {
        private final int maxProfit;
        private final List<SaraItemType> items;
        
        public KnapsackResult(int maxProfit, List<SaraItemType> items) {
            this.maxProfit = maxProfit;
            this.items = Collections.unmodifiableList(new ArrayList<>(items));
        }
        
        public int getMaxProfit() { return maxProfit; }
        public List<SaraItemType> getItems() { return items; }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Maximum Profit: ").append(maxProfit).append("\n");
            sb.append("Items Selected: ").append(items.size()).append("\n");
            for (SaraItemType item : items) {
                sb.append("  ").append(item).append("\n");
            }
            return sb.toString();
        }
    }
    
    public static KnapsackResult solve(int W, List<SaraItemType> items, boolean showDPTable) {
        int n = items.size();
        int[][] V = new int[n+1][W+1];
        
        // Build DP table
        for (int i = 1; i <= n; i++) {
            SaraItemType current = items.get(i-1);
            for (int w = 0; w <= W; w++) {
                if (current.getWeight() <= w) {
                    V[i][w] = Math.max(V[i-1][w], 
                                      V[i-1][w-current.getWeight()] + current.getProfit());
                } else {
                    V[i][w] = V[i-1][w];
                }
            }
        }
        
        // Show DP table visualization if requested
        if (showDPTable) {
            SwingUtilities.invokeLater(() -> {
                new GraphicalDPViewer(V, items, W);
            });
        }

        // Backtrack to find items
        List<SaraItemType> selectedItems = new ArrayList<>();
        int w = W;
        for (int i = n; i > 0; i--) {
            if (V[i][w] != V[i-1][w]) {
                selectedItems.add(items.get(i-1));
                w -= items.get(i-1).getWeight();
            }
        }
        
        return new KnapsackResult(V[n][W], selectedItems);
    }

    // Greedy approximation (by profit/weight ratio)
    public static KnapsackResult solveGreedy(int W, List<SaraItemType> items) {
        List<SaraItemType> sortedItems = new ArrayList<>(items);
        sortedItems.sort((a, b) -> {
            double ratioA = (double)a.getProfit() / a.getWeight();
            double ratioB = (double)b.getProfit() / b.getWeight();
            return Double.compare(ratioB, ratioA);
        });
        
        int currentWeight = 0;
        int totalProfit = 0;
        List<SaraItemType> selectedItems = new ArrayList<>();
        
        for (SaraItemType item : sortedItems) {
            if (currentWeight + item.getWeight() <= W) {
                selectedItems.add(item);
                currentWeight += item.getWeight();
                totalProfit += item.getProfit();
            }
        }
        
        return new KnapsackResult(totalProfit, selectedItems);
    }
}