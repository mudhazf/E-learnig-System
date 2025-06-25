import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressTrackerCourseSelector extends JFrame {
    private final String studentName;

    public ProgressTrackerCourseSelector(String studentName) {
        this.studentName = studentName;
        setTitle("📚 Course Progress Tracker");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout(10, 10));

        // Sample course data
        Map<String, List<String>> completedLessonsMap = new HashMap<>();
        Map<String, Integer> totalLessonsMap = new HashMap<>();

        completedLessonsMap.put("HTML & CSS", List.of("Tags", "Forms"));
        totalLessonsMap.put("HTML & CSS", 5);

        completedLessonsMap.put("Python Programming", List.of("Variables", "Loops", "Functions"));
        totalLessonsMap.put("Python Programming", 6);

        completedLessonsMap.put("AI Fundamentals", List.of("Intro to AI", "Neural Networks"));
        totalLessonsMap.put("AI Fundamentals", 4);

        completedLessonsMap.put("Java Basics", List.of("Syntax", "Conditions", "Loops", "OOP"));
        totalLessonsMap.put("Java Basics", 5);

        // Header
        JLabel heading = new JLabel("📚 Select a Course to View Progress", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        // Course List
        JPanel courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBackground(new Color(250, 250, 250));

        for (String course : completedLessonsMap.keySet()) {
            JPanel row = new JPanel(new BorderLayout(10, 10));
            row.setMaximumSize(new Dimension(680, 45));
            row.setBackground(Color.WHITE);
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));

            JButton courseBtn = new JButton(course);
            courseBtn.setFocusPainted(false);
            courseBtn.setPreferredSize(new Dimension(220, 30));
            courseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            courseBtn.setBackground(new Color(66, 133, 244));
            courseBtn.setForeground(Color.WHITE);
            courseBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            courseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            int total = totalLessonsMap.get(course);
            int completed = completedLessonsMap.get(course).size();
            int percent = (total == 0) ? 0 : completed * 100 / total;

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(percent);
            bar.setStringPainted(true);
            bar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            bar.setForeground(new Color(76, 175, 80));
            bar.setPreferredSize(new Dimension(320, 22));
            bar.setBackground(new Color(240, 240, 240));
            bar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

            courseBtn.addActionListener(e -> {
                new ProgressTracker(completedLessonsMap.get(course), total, studentName);
                dispose();
            });

            row.add(courseBtn, BorderLayout.WEST);
            row.add(bar, BorderLayout.EAST);
            courseListPanel.add(row);
            courseListPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backBtn = new JButton("⬅ Back to Dashboard");
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setPreferredSize(new Dimension(200, 35));
        backBtn.setBackground(new Color(244, 67, 54));
        backBtn.setForeground(Color.WHITE);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backBtn.addActionListener(e -> {
            new StudentDashboard(studentName);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(250, 250, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}