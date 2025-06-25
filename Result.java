import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Result extends JFrame {
    public Result(int score, int total,
                  List<String> questions,
                  List<String> userAnswers,
                  List<String> correctAnswers,
                  List<Boolean> isCorrectList,
                  String studentName) {

        // Theme colors
        Color primaryBlue = new Color(69, 103, 183);
        Color softGreen = new Color(46, 160, 67);
        Color lightGray = new Color(245, 245, 250);
        Color white = Color.WHITE;
        Color softRed = new Color(200, 70, 70);
        Color darkText = new Color(50, 50, 50);

        setTitle("📘 Quiz Result");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(lightGray);

        // Heading
        JLabel heading = new JLabel("📘 Quiz Result", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Score Panel
        JPanel scorePanel = new JPanel(new GridLayout(3, 2, 15, 12));
        scorePanel.setBackground(white);
        scorePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryBlue, 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font valueFont = new Font("SansSerif", Font.PLAIN, 16);

        JLabel scoreLabel = new JLabel("Score:");
        JLabel scoreValue = new JLabel(score + " / " + total);

        JLabel percentageLabel = new JLabel("Percentage:");
        double percentage = ((double) score / total) * 100;
        JLabel percentageValue = new JLabel(String.format("%.2f", percentage) + "%");

        JLabel feedbackLabel = new JLabel("Feedback:");
        JTextField feedbackText = new JTextField();
        feedbackText.setEditable(false);
        feedbackText.setBackground(lightGray);
        feedbackText.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        feedbackText.setFont(valueFont);

        if (percentage >= 80)
            feedbackText.setText("🌟 Excellent performance!");
        else if (percentage >= 60)
            feedbackText.setText("👍 Good job, keep practicing.");
        else if (percentage >= 40)
            feedbackText.setText("⚠️ Needs improvement.");
        else
            feedbackText.setText("📚 Consider reviewing the material again.");

        for (JLabel label : new JLabel[]{scoreLabel, percentageLabel, feedbackLabel}) {
            label.setFont(labelFont);
            label.setForeground(darkText);
        }

        for (JLabel label : new JLabel[]{scoreValue, percentageValue}) {
            label.setFont(valueFont);
            label.setForeground(primaryBlue.darker());
        }

        scorePanel.add(scoreLabel);
        scorePanel.add(scoreValue);
        scorePanel.add(percentageLabel);
        scorePanel.add(percentageValue);
        scorePanel.add(feedbackLabel);
        scorePanel.add(feedbackText);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(lightGray);
        topPanel.add(heading, BorderLayout.NORTH);
        topPanel.add(scorePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"#", "Question", "Your Answer", "Correct Answer", "Result"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < questions.size(); i++) {
            tableModel.addRow(new Object[]{
                    (i + 1),
                    questions.get(i),
                    userAnswers.get(i),
                    correctAnswers.get(i),
                    isCorrectList.get(i) ? "✅" : "❌"
            });
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(4).setCellRenderer(new IconRenderer());

        // Row striping
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 255));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detailed Answer Review"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        bottomPanel.setBackground(lightGray);

        JButton continueBtn = createStyledButton("Continue", softGreen);
        continueBtn.addActionListener(e -> {
            new StudentDashboard(studentName).setVisible(true);
            dispose();
        });

        JButton logoutBtn = createStyledButton("Logout", softRed);
        logoutBtn.addActionListener(e -> {
            Main.main(null);
            dispose();
        });

        bottomPanel.add(continueBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 40));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return button;
    }

    static class IconRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.PLAIN, 20));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setForeground(value.toString().equals("✅") ? new Color(0, 140, 0) : new Color(200, 0, 0));
            return label;
        }
    }
}
