import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizCourseSelector extends JFrame {
    private String studentName;

    public QuizCourseSelector(String studentName, List<String> courses) {
        this.studentName = studentName;

        // === Colors and Fonts ===
        Color backgroundColor = new Color(245, 245, 250);
        Color primaryColor = new Color(69, 103, 183);
        Color buttonColor = new Color(98, 143, 230);
        Color buttonHoverColor = new Color(78, 123, 210);
        Font headingFont = new Font("SansSerif", Font.BOLD, 24);
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 16);

        setTitle("📝 Select Course for Quiz");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(backgroundColor);

        // === Heading ===
        JLabel heading = new JLabel("📝 Select a Course to Attempt Quiz", SwingConstants.CENTER);
        heading.setFont(headingFont);
        heading.setForeground(primaryColor);
        heading.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // === Course Panel ===
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBackground(backgroundColor);
        coursePanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        Map<String, String[]> quizInfoMap = new HashMap<>();
        quizInfoMap.put("HTML & CSS", new String[]{"HTML & CSS Quiz", "HTML Basics", "WEB101", "Miss Sana", "5", "2", "20"});
        quizInfoMap.put("Python Programming", new String[]{"Python Basics Quiz", "Python Programming", "CS102", "Dr. Bilal", "6", "3", "30"});
        quizInfoMap.put("AI Fundamentals", new String[]{"AI Concepts Quiz", "AI Fundamentals", "AI200", "Dr. Ayesha", "4", "2", "25"});
        quizInfoMap.put("Java Basics", new String[]{"Java OOP Quiz", "Object-Oriented Programming", "CS203", "Dr. Ali Khan", "4", "2", "50"});

        for (String course : courses) {
            JButton courseBtn = new JButton("🧪  " + course + " Quiz");
            courseBtn.setFont(buttonFont);
            courseBtn.setFocusPainted(false);
            courseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            courseBtn.setMaximumSize(new Dimension(500, 45));
            courseBtn.setBackground(buttonColor);
            courseBtn.setForeground(Color.WHITE);
            courseBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            courseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            courseBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    courseBtn.setBackground(buttonHoverColor);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    courseBtn.setBackground(buttonColor);
                }
            });

            courseBtn.addActionListener(e -> {
                String[] info = quizInfoMap.get(course);
                if (info != null) {
                    new QuizInstructions(
                            info[0],
                            info[1],
                            info[2],
                            info[3],
                            Integer.parseInt(info[4]),
                            Integer.parseInt(info[5]),
                            Integer.parseInt(info[6]),
                            studentName
                    );
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Quiz details not found for: " + course,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            coursePanel.add(courseBtn);
            coursePanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(backgroundColor);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // === Back Button ===
        JButton backBtn = new JButton("⬅ Back to Dashboard");
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backBtn.setBackground(new Color(220, 220, 220));
        backBtn.setForeground(Color.DARK_GRAY);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.addActionListener(e -> {
            new StudentDashboard(studentName);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(backBtn);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
