import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Arrays;

public class Quiz extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private JButton nextButton;
    private JLabel timerLabel;
    private JLabel feedbackLabel;

    private int currentQuestion = 0;
    private int score = 0;
    private int timeLeft = 30;
    private Timer timer;
    private ButtonGroup bg;

    private int total;
    private String studentName = "Student";
    private String subject;

    private String[] questions;
    private String[][] optionsData;
    private int[] correctAnswers;

    private List<String> userAnswers = new ArrayList<>();
    private List<String> correctAnswerTexts = new ArrayList<>();
    private List<Boolean> isCorrectList = new ArrayList<>();

    public Quiz(String subject, String studentName) {
        this.subject = subject;
        this.studentName = studentName;
        setTitle("🧠 Quiz - " + subject);

        initializeSubjectData(subject);

        total = questions.length;
        setupUI();
        loadQuestion();
        startTimer();
        setVisible(true);
    }

    private void setupUI() {
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Color palette
        Color primaryBlue = new Color(69, 103, 183);
        Color softBackground = new Color(248, 250, 252);
        Color cardWhite = Color.WHITE;
        Color softGreen = new Color(46, 160, 67);
        Color textGray = new Color(85, 85, 85);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(softBackground);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        add(mainPanel, BorderLayout.CENTER);

        // === Top panel with timer ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(softBackground);
        timerLabel = new JLabel("⏱ Time left: 30 sec");
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        timerLabel.setForeground(primaryBlue);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        topPanel.add(timerLabel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // === Question card ===
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBackground(cardWhite);
        questionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        questionLabel.setForeground(textGray);
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(20));

        // === Answer options ===
        options = new JRadioButton[4];
        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("SansSerif", Font.PLAIN, 15));
            options[i].setBackground(cardWhite);
            options[i].setForeground(textGray);
            options[i].setFocusPainted(false);
            bg.add(options[i]);
            questionPanel.add(options[i]);
            questionPanel.add(Box.createVerticalStrut(10));
        }

        // === Feedback label ===
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        feedbackLabel.setForeground(Color.DARK_GRAY);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(feedbackLabel);

        mainPanel.add(questionPanel, BorderLayout.CENTER);

        // === Bottom navigation ===
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(softBackground);

        nextButton = new JButton("Next ➡️");
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextButton.setBackground(softGreen);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(nextButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> checkAnswer());
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText((currentQuestion + 1) + ". " + questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(optionsData[currentQuestion][i]);
                options[i].setSelected(false);
            }
            bg.clearSelection();
            feedbackLabel.setText(" ");
            timeLeft = 30;
        } else {
            endQuiz();
        }
    }

    private void checkAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            feedbackLabel.setText("⚠️ Please select an answer.");
            return;
        }

        String selectedText = options[selected].getText();
        String correctText = optionsData[currentQuestion][correctAnswers[currentQuestion]];

        userAnswers.add(selectedText);
        correctAnswerTexts.add(correctText);
        boolean isCorrect = selected == correctAnswers[currentQuestion];
        isCorrectList.add(isCorrect);

        if (isCorrect) {
            score++;
            feedbackLabel.setText("✅ Correct!");
        } else {
            feedbackLabel.setText("❌ Incorrect. Correct answer: " + correctText);
        }

        currentQuestion++;
        Timer delay = new Timer(1500, e -> loadQuestion());
        delay.setRepeats(false);
        delay.start();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("⏱ Time left: " + timeLeft + " sec");
            if (timeLeft <= 0) {
                JOptionPane.showMessageDialog(this, "⏰ Time’s up for this question!");
                userAnswers.add("No Answer");
                correctAnswerTexts.add(optionsData[currentQuestion][correctAnswers[currentQuestion]]);
                isCorrectList.add(false);
                currentQuestion++;
                loadQuestion();
            }
        });
        timer.start();
    }

    private void endQuiz() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "✅ Quiz Completed! Score: " + score + "/" + total);
        dispose();

        new Result(score, total, List.of(questions),
                userAnswers, correctAnswerTexts, isCorrectList, studentName);
    }

    private void initializeSubjectData(String subject) {
        switch (subject) {
            case "Object-Oriented Programming":
                questions = new String[]{
                        "Which concept allows using the same method in different ways?",
                        "Which keyword is used to create an object?",
                        "Which access modifier makes variables accessible only within the class?",
                        "What does 'this' keyword refer to?"
                };
                optionsData = new String[][]{
                        {"Polymorphism", "Abstraction", "Inheritance", "Encapsulation"},
                        {"new", "class", "this", "import"},
                        {"public", "private", "protected", "default"},
                        {"Current object", "Superclass", "Static method", "None"}
                };
                correctAnswers = new int[]{0, 0, 1, 0};
                break;

            case "HTML Basics":
                questions = new String[]{
                        "What does HTML stand for?",
                        "Which tag is used to make text bold?",
                        "What is the correct syntax for a hyperlink?",
                        "Which tag is used for inserting a line break?"
                };
                optionsData = new String[][]{
                        {"Hyper Text Markup Language", "HighText Machine Language", "Hyper Tool Markup Language", "None"},
                        {"<b>", "<strong>", "<bold>", "<text>"},
                        {"<a href='url'>Link</a>", "<link>url</link>", "<a>url</a>", "<href='url'>Link</href>"},
                        {"<lb>", "<break>", "<br>", "<line>"}
                };
                correctAnswers = new int[]{0, 0, 0, 2};
                break;

            case "Python Programming":
                questions = new String[]{
                        "What keyword is used to define a function in Python?",
                        "Which symbol is used for comments in Python?",
                        "How do you insert a value into a list?",
                        "What is the output of len([1, 2, 3])?"
                };
                optionsData = new String[][]{
                        {"def", "func", "function", "define"},
                        {"//", "#", "/* */", "<!-- -->"},
                        {"insert()", "append()", "add()", "put()"},
                        {"2", "3", "4", "Error"}
                };
                correctAnswers = new int[]{0, 1, 1, 1};
                break;

            default:
                questions = new String[]{"No quiz available for this subject."};
                optionsData = new String[][]{{"OK", "", "", ""}};
                correctAnswers = new int[]{0};
        }
    }
}
