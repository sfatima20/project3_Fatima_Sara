import java.io.Serializable;

public class SaraItemType implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private int weight;
    private int profit;
    
    public SaraItemType(String id, int weight, int profit) {
        if (weight <= 0 || profit <= 0)
            throw new IllegalArgumentException("Weight and profit must be positive");
        this.id = id;
        this.weight = weight;
        this.profit = profit;
    }
    
    public String getId() { return id; }
    public int getWeight() { return weight; }
    public int getProfit() { return profit; }
    
    @Override
    public String toString() {
        return String.format("%s (w:%d, p:%d)", id, weight, profit);
    }
}