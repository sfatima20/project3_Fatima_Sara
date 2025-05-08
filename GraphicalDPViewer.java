import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphicalDPViewer extends JFrame {
    private int[][] dpTable;
    private String[] itemNames;
    private int capacity;

    public GraphicalDPViewer(int[][] dpTable, List<SaraItemType> items, int capacity) {
        this.dpTable = dpTable;
        this.capacity = capacity;
        this.itemNames = new String[items.size() + 1];
        itemNames[0] = "None";
        for (int i = 0; i < items.size(); i++) {
            itemNames[i + 1] = items.get(i).getId();
        }
        
        setTitle("DP Table Visualization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        add(new DPTablePanel());
        pack();
        setVisible(true);
    }

    class DPTablePanel extends JPanel {
        private static final int CELL_WIDTH = 80;
        private static final int CELL_HEIGHT = 30;
        private static final int HEADER_SIZE = 40;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Draw headers
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            
            // Column headers (weights)
            for (int w = 0; w <= capacity; w++) {
                g.drawString(Integer.toString(w), HEADER_SIZE + w * CELL_WIDTH + CELL_WIDTH/2, HEADER_SIZE/2);
            }
            
            // Row headers (items)
            for (int i = 0; i < dpTable.length; i++) {
                g.drawString(itemNames[i], 5, HEADER_SIZE + i * CELL_HEIGHT + CELL_HEIGHT/2);
            }
            
            // Draw DP table cells
            g.setFont(new Font("SansSerif", Font.PLAIN, 11));
            for (int i = 0; i < dpTable.length; i++) {
                for (int w = 0; w <= capacity; w++) {
                    // Cell border
                    g.drawRect(HEADER_SIZE + w * CELL_WIDTH, HEADER_SIZE + i * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
                    
                    // Cell value
                    String value = (w < dpTable[i].length) ? Integer.toString(dpTable[i][w]) : "";
                    g.drawString(value, 
                                HEADER_SIZE + w * CELL_WIDTH + CELL_WIDTH/2 - 5, 
                                HEADER_SIZE + i * CELL_HEIGHT + CELL_HEIGHT/2);
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(HEADER_SIZE + (capacity + 1) * CELL_WIDTH, 
                               HEADER_SIZE + dpTable.length * CELL_HEIGHT);
        }
    }
}