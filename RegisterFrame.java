import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class RegisterFrame extends JFrame {

    private RoundedTextField nameField, emailField, usernameField;
    private RoundedPasswordField passwordField, confirmPasswordField;

    public RegisterFrame() {
        setTitle("GreenLoop - Register");
        setSize(950, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(null); 
        setContentPane(mainPanel);

        // --- HEADER AREA ---
        JPanel headerArea = new JPanel(null);
        headerArea.setOpaque(false);
        headerArea.setBounds(225, 20, 500, 100); 
        
        JLabel mainTitle = new JLabel("GreenLoop", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 42)); 
        mainTitle.setForeground(new Color(46, 125, 50)); 
        mainTitle.setBounds(0, 10, 500, 45);
        headerArea.add(mainTitle);

        JLabel subTitle = new JLabel("CREATE AN ACCOUNT TO GET STARTED", SwingConstants.CENTER);
        subTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        subTitle.setForeground(new Color(120, 120, 120)); 
        subTitle.setBounds(0, 55, 500, 20);
        headerArea.add(subTitle);

        mainPanel.add(headerArea);

        // --- REGISTER BOX ---
        RoundedPanel regBox = new RoundedPanel(30); 
        regBox.setBounds(255, 130, 440, 510); // උස ප්‍රමාණය සහ Bounds නිවැරදි කලා
        mainPanel.add(regBox);

        // Full Name
        JLabel nLabel = new JLabel("Full Name");
        nLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nLabel.setBounds(40, 20, 360, 20);
        regBox.add(nLabel);

        nameField = new RoundedTextField("Enter full name"); 
        nameField.setBounds(40, 42, 360, 40);
        regBox.add(nameField);

        // Email
        JLabel eLabel = new JLabel("Email Address");
        eLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        eLabel.setBounds(40, 92, 360, 20);
        regBox.add(eLabel);

        emailField = new RoundedTextField("Enter email address"); 
        emailField.setBounds(40, 114, 360, 40);
        regBox.add(emailField);

        // Username
        JLabel uLabel = new JLabel("Username");
        uLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        uLabel.setBounds(40, 164, 360, 20);
        regBox.add(uLabel);

        usernameField = new RoundedTextField("Create username"); 
        usernameField.setBounds(40, 186, 360, 40);
        regBox.add(usernameField);

        // Password
        JLabel pLabel = new JLabel("Password");
        pLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pLabel.setBounds(40, 236, 360, 20);
        regBox.add(pLabel);

        passwordField = new RoundedPasswordField("Create password"); 
        passwordField.setBounds(40, 258, 360, 40);
        regBox.add(passwordField);

        // Confirm Password
        JLabel cpLabel = new JLabel("Confirm Password");
        cpLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cpLabel.setBounds(40, 308, 360, 20);
        regBox.add(cpLabel);

        confirmPasswordField = new RoundedPasswordField("Confirm password");
        confirmPasswordField.setBounds(40, 330, 360, 40);
        regBox.add(confirmPasswordField);

        // Terms Checkbox
        JCheckBox terms = new JCheckBox("I agree to the Terms & Conditions");
        terms.setBackground(Color.WHITE);
        terms.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        terms.setBounds(36, 380, 250, 25);
        regBox.add(terms);

        // Register Button
        RoundedButton regBtn = new RoundedButton("Register Now", new Color(46, 125, 50), Color.WHITE);
        regBtn.setBounds(40, 415, 360, 40);
        regBtn.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Fields හිස්දැයි බැලීම
            if (nameField.getText().trim().isEmpty()
                    || emailField.getText().trim().isEmpty()
                    || usernameField.getText().trim().isEmpty()
                    || password.trim().isEmpty()
                    || confirmPassword.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Terms accept කරලා නැත්නම්
            if (!terms.isSelected()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please accept the Terms & Conditions!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Passwords සමාන නැත්නම්
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Password and Confirm Password do not match!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        });
        regBox.add(regBtn);

       // Back to Login Link
JLabel backToLogin = new JLabel("Already have an account? Login", SwingConstants.CENTER);
backToLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
backToLogin.setForeground(new Color(46, 125, 50));
backToLogin.setBounds(0, 465, 440, 25);
backToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

backToLogin.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // මෙයයි ඔබ එක් කළ යුතු වැදගත්ම කොටස
        new LoginFrame().setVisible(true); // Login පිටුව අලුතින් විවෘත කිරීම
        RegisterFrame.this.dispose();      // Register පිටුව වසා දැමීම
    }
});
regBox.add(backToLogin);
        // Footer
        JLabel footer = new JLabel("© 2026 GreenLoop. All rights reserved.", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(new Color(100, 100, 100));
        footer.setBounds(0, 670, 950, 30);
        mainPanel.add(footer);

        setVisible(true);
    }

    // --- UI HELPERS ---
    class BackgroundPanel extends JPanel {
        private Image backgroundImage = null;
        public BackgroundPanel() {
            try {
                File file = new File("images/background.png");
                if (file.exists()) backgroundImage = new ImageIcon("images/background.png").getImage();
            } catch (Exception e) {}
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            else { g.setColor(new Color(245, 248, 245)); g.fillRect(0, 0, getWidth(), getHeight()); }
        }
    }

    class RoundedPanel extends JPanel {
        private int radius;
        public RoundedPanel(int radius) { this.radius = radius; setOpaque(false); setLayout(null); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(new Color(235, 238, 235));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    class RoundedTextField extends JTextField implements FocusListener {
        private String hint;
        public RoundedTextField(String hint) {
            this.hint = hint; setOpaque(false); addFocusListener(this);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(hasFocus() ? new Color(46, 125, 50) : new Color(215, 220, 215));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            if (getText().isEmpty() && !hasFocus()) {
                g2.setColor(new Color(170, 175, 170)); g2.drawString(hint, 15, getHeight() / 2 + 5);
            }
            g2.dispose(); super.paintComponent(g);
        }
        public void focusGained(FocusEvent e) { repaint(); }
        public void focusLost(FocusEvent e) { repaint(); }
    }

    class RoundedPasswordField extends JPasswordField implements FocusListener {
        private String hint;
        public RoundedPasswordField(String hint) {
            this.hint = hint; setOpaque(false); addFocusListener(this);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(hasFocus() ? new Color(46, 125, 50) : new Color(215, 220, 215));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            if (getPassword().length == 0 && !hasFocus()) {
                g2.setColor(new Color(170, 175, 170)); g2.drawString(hint, 15, getHeight() / 2 + 5);
            }
            g2.dispose(); super.paintComponent(g);
        }
        public void focusGained(FocusEvent e) { repaint(); }
        public void focusLost(FocusEvent e) { repaint(); }
    }

    class RoundedButton extends JButton {
        private Color bg;
        public RoundedButton(String text, Color bg, Color fg) {
            super(text); this.bg = bg;
            setOpaque(false); setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
            setForeground(fg); setFont(new Font("Segoe UI", Font.BOLD, 14)); setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose(); super.paintComponent(g);
        }
    }

       public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterFrame());
    }
}