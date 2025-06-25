import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("E-Learning System");
        frame.setSize(1024, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Gradient header
        GradientPanel headerPanel = new GradientPanel(
                new Color(58, 123, 213),
                new Color(58, 91, 152)
        );
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("📘 Welcome to E-learning System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(1024, 70));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Get Started Tab
        JButton loginBtn = new JButton("Login to Start Learning");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new Dimension(260, 55));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        GradientPanel loginPanel = new GradientPanel(
                new Color(72, 85, 99),
                new Color(44, 62, 80)
        );
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            new Login().setVisible(true);
            frame.dispose();
        });

        tabbedPane.addTab(" Get Started", loginPanel);
        tabbedPane.addTab(" About Us", createTextPanel(getAboutUs()));
        tabbedPane.addTab("Help / FAQ", createTextPanel(getHelpText()));
        tabbedPane.addTab("Terms", createTextPanel(getTermsText()));
        tabbedPane.addTab("Privacy", createTextPanel(getPrivacyText()));
        tabbedPane.setSelectedIndex(0);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Text panel with gradient background
    private static JScrollPane createTextPanel(String text) {
        GradientPanel textPanel = new GradientPanel(
                new Color(72, 85, 99),
                new Color(44, 62, 80)
        );
        textPanel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(text);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setOpaque(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        textPanel.add(scrollPane, BorderLayout.CENTER);
        return new JScrollPane(textPanel);
    }

    private static String getAboutUs() {
        return " About Us\n\n" +
                "Welcome to eLearn Pro!\n\n" +
                "We are a team of software engineers, UI designers, and education specialists dedicated " +
                "to building accessible and learner-centered e-learning platforms.\n\n" +
                "Mission: Empower learners through innovation.\n" +
                "Vision: Make quality education accessible for everyone.\n\n" +
                "Contact: support@elearnpro.com";
    }

    private static String getHelpText() {
        return "Help & FAQ\n\n" +
                " Login / Registration:\n" +
                "• Register – Go to Login > Click 'Register' > Fill details.\n" +
                "• Forgot password? – Use 'Forgot Password' or contact support.\n\n" +
                "Courses:\n" +
                "• Enroll – Open dashboard > Choose a course.\n" +
                "• Interactive lessons? – Yes! Quizzes, videos, and exercises.\n\n" +
                " Quizzes:\n" +
                "• Attempt – Open lesson > Click 'Start Quiz'.\n" +
                "• Retakes – Allowed if instructor permits.\n\n" +
                "Progress:\n" +
                "• Track – Use the Progress Tracker tab.\n\n" +
                " Certificates:\n" +
                "• Receive after completing lessons + quiz.\n\n" +
                "⚙ Technical:\n" +
                "• Email: support@elearnpro.com";
    }

    private static String getTermsText() {
        return " Terms & Conditions\n\n" +
                "1. Registration is required to access content.\n" +
                "2. Use content only for personal, educational purposes.\n" +
                "3. No copying or commercial use without permission.\n" +
                "4. Violations may lead to account suspension.\n" +
                "5. Certificates are earned after completing course content.";
    }

    private static String getPrivacyText() {
        return " Privacy Policy\n\n" +
                "• We collect minimal data (name, email) for your experience.\n" +
                "• We never sell or share your data.\n" +
                "• Data is securely stored and encrypted.\n" +
                "• You can request account deletion anytime.\n\n" +
                " Contact: support@elearnpro.com";
    }
}

// Custom JPanel with gradient background
class GradientPanel extends JPanel {
    private final Color colorStart;
    private final Color colorEnd;

    public GradientPanel(Color colorStart, Color colorEnd) {
        this.colorStart = colorStart;
        this.colorEnd = colorEnd;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, colorStart, 0, h, colorEnd);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
        super.paintComponent(g);
    }
}
