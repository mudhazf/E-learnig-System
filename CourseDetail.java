import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CourseDetail extends JFrame {

    public CourseDetail(String studentName, String courseName) {
        setTitle(courseName + " - Course Details");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 248, 255));
        add(mainPanel);

        // Heading
        JLabel heading = new JLabel("📘 " + courseName + " - Details", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 24));
        heading.setForeground(new Color(51, 102, 153));
        mainPanel.add(heading, BorderLayout.NORTH);

        // Center Panel for description and modules
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Description
        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBackground(new Color(255, 255, 255));
        description.setBorder(BorderFactory.createTitledBorder("📄 Course Description"));

        String descText = switch (courseName) {
            case "HTML & CSS" -> "This course covers the fundamentals of web design using HTML and CSS, including layout, styling, and responsive techniques.";
            case "Python Programming" -> "Learn Python from basics to intermediate: syntax, data structures, control flow, functions, and hands-on projects.";
            case "AI Fundamentals" -> "Explore the foundational topics in Artificial Intelligence, including machine learning, neural networks, and AI ethics.";
            case "Database Systems" -> "Understand relational databases, ER diagrams, SQL queries, normalization, and transactions.";
            default -> "Course description not available.";
        };
        description.setText(descText);
        centerPanel.add(description);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Modules Label
        JLabel moduleLabel = new JLabel("📦 Modules Included:");
        moduleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        moduleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(moduleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Module List
        String[] modules = switch (courseName) {
            case "HTML & CSS" -> new String[]{
                    "1. HTML Structure",
                    "2. CSS Selectors",
                    "3. Box Model & Flexbox",
                    "4. Responsive Design"
            };
            case "Python Programming" -> new String[]{
                    "1. Variables & Data Types",
                    "2. Loops & Conditions",
                    "3. Functions & Modules",
                    "4. File Handling"
            };
            case "AI Fundamentals" -> new String[]{
                    "1. AI Overview",
                    "2. Machine Learning",
                    "3. Neural Networks",
                    "4. AI in Society"
            };
            case "Database Systems" -> new String[]{
                    "1. ER Modeling",
                    "2. SQL Queries",
                    "3. Normalization",
                    "4. Transactions & Indexing"
            };
            default -> new String[]{"No modules found."};
        };

        JList<String> moduleList = new JList<>(modules);
        moduleList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        moduleList.setVisibleRowCount(4);
        JScrollPane listScroll = new JScrollPane(moduleList);
        listScroll.setPreferredSize(new Dimension(600, 100));
        centerPanel.add(listScroll);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 255));

        JButton startBtn = new JButton("▶️ Start Lessons");
        JButton backBtn = new JButton("⬅ Back to Catalog");

        startBtn.setBackground(new Color(69, 103, 183));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        backBtn.setBackground(new Color(100, 100, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        buttonPanel.add(backBtn);
        buttonPanel.add(startBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        startBtn.addActionListener(e -> {
            new LessonViewer(studentName, courseName).setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            new StudentDashboard(studentName).setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
