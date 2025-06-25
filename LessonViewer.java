import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LessonViewer extends JFrame {
    private JTextArea lessonContentArea;
    private JButton nextBtn, prevBtn;
    private JLabel lessonTitle;
    private ArrayList<Lesson> lessons;
    private int currentLessonIndex = 0;
    private final ArrayList<LessonProgress> progressList = new ArrayList<>();

    public LessonViewer(String studentName, String courseName) {
        setTitle("📚 " + courseName + " | eLearn Pro");
        setSize(1024, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);

        // Top panel (Welcome + Title)
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setOpaque(false);

        JLabel welcome = new JLabel("Hello, " + studentName + "! 👋");
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcome.setForeground(new Color(60, 60, 60));
        topPanel.add(welcome);

        lessonTitle = new JLabel("Lesson Title");
        lessonTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lessonTitle.setForeground(new Color(44, 76, 130));
        topPanel.add(lessonTitle);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center (lesson content)
        lessonContentArea = new JTextArea();
        lessonContentArea.setFont(new Font("Serif", Font.PLAIN, 16));
        lessonContentArea.setLineWrap(true);
        lessonContentArea.setWrapStyleWord(true);
        lessonContentArea.setEditable(false);
        lessonContentArea.setMargin(new Insets(10, 10, 10, 10));
        lessonContentArea.setBackground(new Color(250, 250, 250));

        JScrollPane scrollPane = new JScrollPane(lessonContentArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom (navigation buttons)
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        navPanel.setOpaque(false);

        prevBtn = new JButton("⬅ Previous");
        nextBtn = new JButton("Next ➡");

        for (JButton btn : new JButton[]{prevBtn, nextBtn}) {
            btn.setPreferredSize(new Dimension(160, 40));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBackground(new Color(44, 130, 201));
            btn.setForeground(Color.WHITE);
        }

        navPanel.add(prevBtn);
        navPanel.add(nextBtn);
        mainPanel.add(navPanel, BorderLayout.SOUTH);

        // Load and display lesson
        loadLessons(courseName);
        displayLesson(currentLessonIndex);

        // Button actions
        prevBtn.addActionListener(e -> {
            if (currentLessonIndex > 0) {
                saveProgress();
                currentLessonIndex--;
                displayLesson(currentLessonIndex);
            }
        });

        nextBtn.addActionListener(e -> {
            if (currentLessonIndex < lessons.size() - 1) {
                saveProgress();
                currentLessonIndex++;
                displayLesson(currentLessonIndex);
            } else {
                JOptionPane.showMessageDialog(this, "🎉 You've completed all lessons!", "Finished", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add main panel and show frame
        add(mainPanel);
        setVisible(true);
    }

    private void loadLessons(String courseName) {
        lessons = new ArrayList<>();

        switch (courseName) {
            case "HTML Basics" -> {
                lessons.add(new Lesson("HTML Tags", "Learn how to use HTML tags to structure your webpage."));
                lessons.add(new Lesson("Forms & Inputs", "Explore how to create user input forms with input, textarea, and buttons."));
                lessons.add(new Lesson("Lists & Tables", "Design tables and create ordered/unordered lists in HTML."));
            }
            case "Python Programming" -> {
                lessons.add(new Lesson("Variables", "Learn how to declare and use variables in Python."));
                lessons.add(new Lesson("Conditions", "Use if, elif, and else to create logic in your Python programs."));
                lessons.add(new Lesson("Loops", "Repeat tasks using for and while loops."));
                lessons.add(new Lesson("Functions", "Break your code into reusable blocks using functions."));
            }
            case "Intro to AI" -> {
                lessons.add(new Lesson("What is AI?", "Understand the basics of Artificial Intelligence."));
                lessons.add(new Lesson("Machine Learning", "Dive into supervised and unsupervised learning methods."));
                lessons.add(new Lesson("Neural Networks", "Explore the building blocks of deep learning."));
            }
            default -> lessons.add(new Lesson("No Lessons", "This course has no lessons added yet."));
        }
    }

    private void displayLesson(int index) {
        Lesson lesson = lessons.get(index);
        lessonTitle.setText("📘 " + lesson.getTitle());
        lessonContentArea.setText(lesson.getContent());
    }

    private void saveProgress() {
        Lesson lesson = lessons.get(currentLessonIndex);
        progressList.add(new LessonProgress(lesson.getTitle(), "Completed"));
        System.out.println("Saved: " + lesson.getTitle());
    }

    class Lesson {
        private final String title;
        private final String content;

        public Lesson(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() { return title; }
        public String getContent() { return content; }
    }

    class LessonProgress {
        private final String lessonTitle;
        private final String status;

        public LessonProgress(String lessonTitle, String status) {
            this.lessonTitle = lessonTitle;
            this.status = status;
        }

        public String getLessonTitle() { return lessonTitle; }
        public String getStatus() { return status; }
    }
}