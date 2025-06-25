import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackFormFrame extends JFrame {
    private JTextArea feedbackTextArea;
    private JComboBox<String> ratingComboBox, recommendComboBox, contentComboBox, instructorComboBox;
    private JButton submitButton, viewFeedbackButton;

    private static final List<Feedback> feedbackList = new ArrayList<>();
    private String studentName;
    private String courseName;

    public FeedbackFormFrame(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;

        setTitle("📝 Feedback - " + courseName);
        setSize(640, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 248, 255));
        setLayout(new BorderLayout(20, 20));

        // Header
        JLabel heading = new JLabel("📋 Feedback for " + courseName, SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(new Color(33, 64, 125));
        heading.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "🧾 Feedback Form",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(51, 102, 153)
        ));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Comments
        formPanel.add(new JLabel("📝 Your Comments:"), gbc);
        gbc.gridy++;
        feedbackTextArea = new JTextArea(4, 40);
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        feedbackTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
        formPanel.add(scrollPane, gbc);

        // Course Rating
        gbc.gridy++;
        formPanel.add(new JLabel("⭐ Overall Course Rating:"), gbc);
        gbc.gridy++;
        ratingComboBox = new JComboBox<>(new String[]{
                "⭐ 1 - Poor", "⭐ 2 - Fair", "⭐ 3 - Good", "⭐ 4 - Very Good", "⭐ 5 - Excellent"
        });
        ratingComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(ratingComboBox, gbc);

        // Recommendation
        gbc.gridy++;
        formPanel.add(new JLabel("🙋 Would you recommend this course?"), gbc);
        gbc.gridy++;
        recommendComboBox = new JComboBox<>(new String[]{"Yes", "No", "Maybe"});
        recommendComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(recommendComboBox, gbc);

        // Content Clarity
        gbc.gridy++;
        formPanel.add(new JLabel("📚 Course Content Clarity:"), gbc);
        gbc.gridy++;
        contentComboBox = new JComboBox<>(new String[]{"Unclear", "Average", "Clear", "Very Clear"});
        contentComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(contentComboBox, gbc);

        // Instructor Quality
        gbc.gridy++;
        formPanel.add(new JLabel("👨‍🏫 Instructor Performance:"), gbc);
        gbc.gridy++;
        instructorComboBox = new JComboBox<>(new String[]{"Poor", "Fair", "Good", "Excellent"});
        instructorComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(instructorComboBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(245, 248, 255));

        submitButton = new JButton("📨 Submit Feedback");
        viewFeedbackButton = new JButton("📄 View All Feedback");

        styleButton(submitButton, new Color(69, 103, 183));
        styleButton(viewFeedbackButton, new Color(76, 175, 80));

        buttonPanel.add(submitButton);
        buttonPanel.add(viewFeedbackButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        submitButton.addActionListener(new SubmitHandler());
        viewFeedbackButton.addActionListener(e -> new FeedbackListFrame(feedbackList));

        setVisible(true);
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private class SubmitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = feedbackTextArea.getText().trim();
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(FeedbackFormFrame.this,
                        "⚠️ Please enter your feedback message.", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Feedback feedback = new Feedback(
                    studentName,
                    courseName,
                    message,
                    (String) ratingComboBox.getSelectedItem(),
                    (String) recommendComboBox.getSelectedItem(),
                    (String) contentComboBox.getSelectedItem(),
                    (String) instructorComboBox.getSelectedItem()
            );

            feedbackList.add(feedback);

            JOptionPane.showMessageDialog(FeedbackFormFrame.this,
                    "✅ Thank you for your feedback!", "Submitted", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            feedbackTextArea.setText("");
            ratingComboBox.setSelectedIndex(0);
            recommendComboBox.setSelectedIndex(0);
            contentComboBox.setSelectedIndex(0);
            instructorComboBox.setSelectedIndex(0);
        }
    }

    // Feedback model
    static class Feedback {
        String student, course, message, rating, recommend, contentClarity, instructorQuality;

        public Feedback(String student, String course, String message, String rating,
                        String recommend, String contentClarity, String instructorQuality) {
            this.student = student;
            this.course = course;
            this.message = message;
            this.rating = rating;
            this.recommend = recommend;
            this.contentClarity = contentClarity;
            this.instructorQuality = instructorQuality;
        }

        public Object[] toTableRow() {
            return new Object[]{message, rating, recommend, contentClarity, instructorQuality};
        }
    }
}
