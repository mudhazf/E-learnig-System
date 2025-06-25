import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ProgressTracker extends JFrame {
    private JProgressBar progressBar;

    public ProgressTracker(List<String> completedLessons, int totalLessons, String studentName) {
        Color primaryBlue = new Color(69, 103, 183);
        Color lightGray = new Color(245, 245, 245);
        Color cardColor = new Color(255, 255, 255);
        Color successGreen = new Color(76, 175, 80);

        setTitle("\uD83D\uDCC8 Progress Tracker");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(lightGray);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("\uD83D\uDCC8 Progress Tracker", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(primaryBlue);
        heading.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        JPanel lessonsPanel = new JPanel();
        lessonsPanel.setLayout(new BoxLayout(lessonsPanel, BoxLayout.Y_AXIS));
        lessonsPanel.setBackground(lightGray);
        lessonsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        for (String lesson : completedLessons) {
            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setBackground(cardColor);
            card.setMaximumSize(new Dimension(920, 50));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    new EmptyBorder(10, 10, 10, 10)
            ));

            JLabel icon = new JLabel("\uD83D\uDCDA");
            icon.setFont(new Font("SansSerif", Font.PLAIN, 22));
            card.add(icon, BorderLayout.WEST);

            JLabel title = new JLabel(lesson);
            title.setFont(new Font("SansSerif", Font.PLAIN, 16));
            card.add(title, BorderLayout.CENTER);

            lessonsPanel.add(card);
            lessonsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(lessonsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Completed Lessons"));
        scrollPane.setPreferredSize(new Dimension(960, 400));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(lightGray);
        bottomPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        JLabel progressLabel = new JLabel("Course Completion:");
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(progressLabel);

        bottomPanel.add(Box.createVerticalStrut(5));

        progressBar = new JProgressBar(0, 100);
        int percentage = totalLessons == 0 ? 0 : (completedLessons.size() * 100 / totalLessons);
        progressBar.setValue(percentage);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("SansSerif", Font.BOLD, 16));
        progressBar.setForeground(successGreen);
        progressBar.setBackground(Color.WHITE);
        progressBar.setPreferredSize(new Dimension(600, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(progressBar);

        bottomPanel.add(Box.createVerticalStrut(5));

        JButton backBtn = new JButton("⬅️ Back to Course Selection");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(220, 220, 220));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(backBtn);

        backBtn.addActionListener(e -> {
            new ProgressTrackerCourseSelector(studentName);
            dispose();
        });

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
