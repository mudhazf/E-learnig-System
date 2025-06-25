import javax.swing.*;
import java.awt.*;

public class QuizInstructions extends JFrame {

    public QuizInstructions(String quizTitle, String courseName, String courseCode,
                            String instructor, int totalQuestions, int totalTimeInMinutes,
                            int passingScore, String studentName) {

        setTitle("📘 Quiz Instructions");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(250, 250, 255));
        setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel(quizTitle, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JTextArea details = new JTextArea(
                "📚 Course: " + courseName + "\n" +
                        "🔖 Code: " + courseCode + "\n" +
                        "👨‍🏫 Instructor: " + instructor + "\n\n" +
                        "❓ Total Questions: " + totalQuestions + "\n" +
                        "⏱ Time Limit: " + totalTimeInMinutes + " minutes\n" +
                        "🎯 Passing Score: " + passingScore + "%"
        );
        details.setEditable(false);
        details.setFont(new Font("SansSerif", Font.PLAIN, 16));
        details.setBackground(new Color(250, 250, 255));
        details.setMargin(new Insets(10, 20, 10, 20));
        add(details, BorderLayout.CENTER);

        JButton startQuizBtn = new JButton("🚀 Start Quiz");
        startQuizBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        startQuizBtn.setBackground(new Color(52, 152, 219));
        startQuizBtn.setForeground(Color.WHITE);
        startQuizBtn.setFocusPainted(false);
        startQuizBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startQuizBtn.setPreferredSize(new Dimension(150, 40));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(250, 250, 255));
        btnPanel.add(startQuizBtn);
        add(btnPanel, BorderLayout.SOUTH);

        startQuizBtn.addActionListener(e -> {
            dispose();
            new Quiz(courseName, studentName);
        });

        setVisible(true);
    }
}
