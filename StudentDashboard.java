import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;
import javax.swing.border.Border;

public class StudentDashboard extends JFrame {

    private String studentName;

    private static final Color SIDEBAR_BG = new Color(245, 245, 255);
    private static final Color RIGHT_PANEL_BG = new Color(250, 250, 255);
    private static final Color ACCENT = new Color(60, 120, 250);
    private static final Color ACCENT_TEXT = Color.WHITE;

    private static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 20);
    private static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public StudentDashboard(String studentName) {
        this.studentName = studentName;

        setTitle("Student Dashboard - Learn IT");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);

        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(200, 700));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));


        JLabel name = new JLabel(studentName.toUpperCase(), JLabel.CENTER);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setFont(BODY_FONT);
        sidebar.add(name);

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        sidebar.add(createStyledBtn("👤 View Profile", e -> showProfile()));
        sidebar.add(createStyledBtn("📄 Exam Slip", e -> showMessage("📄 Exam Slip downloaded successfully!")));
        sidebar.add(createStyledBtn("💳 Fee Challan", e -> showMessage("💳 Fee Challan downloaded successfully!")));

        return sidebar;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel heading = new JLabel("📚 Your Learning");
        heading.setFont(HEADER_FONT);
        main.add(heading, BorderLayout.NORTH);

        JPanel coursePanel = new JPanel(new GridLayout(0, 2, 20, 20));
        coursePanel.setBackground(Color.WHITE);
        coursePanel.add(createCourseCard("HTML & CSS", "Web design basics", 70));
        coursePanel.add(createCourseCard("Python Programming", "Beginner to Intermediate", 50));
        coursePanel.add(createCourseCard("AI Fundamentals", "Learn AI core topics", 40));
        coursePanel.add(createCourseCard("Database Systems", "Relational DBs & SQL", 90));

        JScrollPane scrollPane = new JScrollPane(coursePanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    private JPanel createRightPanel() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(RIGHT_PANEL_BG);
        right.setPreferredSize(new Dimension(250, 700));
        right.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel resultTitle = new JLabel("🎯 Test Result");
        resultTitle.setFont(HEADER_FONT);
        resultTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        right.add(resultTitle);

        JLabel progressPercent = new JLabel("95%", JLabel.CENTER);
        progressPercent.setFont(new Font("Arial", Font.BOLD, 36));
        progressPercent.setForeground(ACCENT);
        progressPercent.setAlignmentX(Component.CENTER_ALIGNMENT);
        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(progressPercent);

        right.add(Box.createRigidArea(new Dimension(0, 40)));

        right.add(createStyledBtn("📝 Take Quiz", e -> {
            new QuizCourseSelector(studentName, List.of("HTML & CSS", "Python Programming", "AI Fundamentals", "Java Basics"));
            dispose();
        }));

        right.add(createStyledBtn("📈 My Progress", e -> {
            new ProgressTrackerCourseSelector(studentName);
            dispose();
        }));

        return right;
    }

    private JButton createStyledBtn(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(BODY_FONT);
        btn.setForeground(ACCENT_TEXT);
        btn.setBackground(ACCENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setBorder(new RoundedBorder(10));
        btn.addActionListener(action);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ACCENT.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(ACCENT);
            }
        });

        btn.setMargin(new Insets(8, 20, 8, 20));
        btn.setOpaque(true);
        return btn;
    }

    private JPanel createCourseCard(String title, String desc, int progress) {
        JPanel card = new JPanel(null);
        card.setLayout(null);
        card.setPreferredSize(new Dimension(320, 180));
        card.setBackground(new Color(240, 240, 255));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel titleLabel = new JLabel("📘 " + title);
        titleLabel.setFont(BODY_FONT);
        titleLabel.setBounds(10, 10, 280, 25);
        card.add(titleLabel);

        JTextArea descArea = new JTextArea(desc);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setFont(BODY_FONT);
        descArea.setBounds(10, 40, 290, 40);
        card.add(descArea);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setBounds(10, 90, 290, 20);
        card.add(progressBar);

        JButton viewBtn = createMiniBtn("View Detail", e -> {
            new CourseDetail(studentName, title).setVisible(true);
            dispose();
        });
        viewBtn.setBounds(10, 130, 130, 30);
        card.add(viewBtn);

        JButton feedbackBtn = createMiniBtn("Give Feedback", e -> {
            new FeedbackFormFrame(studentName, title).setVisible(true);
            dispose();
        });
        feedbackBtn.setBounds(150, 130, 130, 30);
        card.add(feedbackBtn);

        return card;
    }

    private JButton createMiniBtn(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(BODY_FONT);
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new RoundedBorder(10));
        btn.addActionListener(action);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ACCENT.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(ACCENT);
            }
        });

        return btn;
    }

    private void showProfile() {
        String info = "Name: " + studentName +
                "\nCNIC: 12345-6789012-3\nAge: 20\nGender: Male\nProgram: BSCS";
        JOptionPane.showMessageDialog(this, info, "Student Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL == null) {
            System.err.println("❌ Couldn't find file: " + path);
            return null;
        }

        ImageIcon icon = new ImageIcon(imgURL);
        Image scaledImg = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
    public class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY); // Optional: customize border color
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
