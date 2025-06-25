import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class FeedbackListFrame extends JFrame {

    public FeedbackListFrame(List<FeedbackFormFrame.Feedback> feedbackList) {
        setTitle("📋 Feedback Dashboard");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel title = new JLabel("📊 Submitted Feedback Summary", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 64, 125));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // Table Columns
        String[] columnNames = {
                "💬 Message", "⭐ Rating", "🙋 Recommend", "📚 Content Clarity", "👨‍🏫 Instructor Quality"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            // Prevent cell editing
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add Feedback Data to Table
        for (FeedbackFormFrame.Feedback feedback : feedbackList) {
            tableModel.addRow(feedback.toTableRow());
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(220, 235, 250));
        header.setForeground(Color.DARK_GRAY);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(100, 40));

        // Alternate Row Color Renderer
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            private final Color evenColor = new Color(245, 250, 255);
            private final Color oddColor = Color.WHITE;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? evenColor : oddColor);
                } else {
                    c.setBackground(new Color(179, 212, 255));
                }
                setBorder(noFocusBorder);
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Scroll Pane Styling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Footer / Hint (optional)
        JLabel footer = new JLabel("✅ Feedback auto-saved from form submissions", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(new Color(120, 120, 120));
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }
}
