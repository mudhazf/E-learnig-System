import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {
    private final Map<String, String[]> users = new HashMap<>();

    public Login() {
        users.put("student1@example.com", new String[]{"pass123", "Ali"});
        users.put("student2@example.com", new String[]{"abc123", "Aneesa"});
        users.put("student3@example.com", new String[]{"xyz789", "Mahak"});

        setTitle("Student Login - E-learning System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Outer panel with contrasting background
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(new Color(44, 62, 80)); // Deep navy

        // Inner white panel (like a login card)
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(420, 360));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("📘 Student Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(41, 128, 185));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Email Label
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(emailLabel, gbc);

        // Email Field
        gbc.gridx = 1;
        JTextField emailField = new JTextField(18);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(200, 30));
        panel.add(emailField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(passLabel, gbc);

        // Password Field
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        panel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(41, 128, 185));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setPreferredSize(new Dimension(180, 40));
        loginBtn.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 1, true));
        panel.add(loginBtn, gbc);

        // Login Action
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim().toLowerCase();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(email) && users.get(email)[0].equals(password)) {
                String name = users.get(email)[1];
                new StudentDashboard(name); // Replace with your dashboard class
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        outerPanel.add(panel); // Center the white panel
        add(outerPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
