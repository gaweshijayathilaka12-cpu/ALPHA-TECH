import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LoginFrame extends JFrame {

    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;

    public LoginFrame() {
        setTitle("GreenLoop - Login");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background Panel
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(null); 
        setContentPane(mainPanel);

        // --- HEADER LOGO & TITLE AREA ---
        JPanel headerArea = new JPanel(null);
        headerArea.setOpaque(false);
        headerArea.setBounds(250, 25, 500, 140); 
        
        CustomImagePanel logoLabel = new CustomImagePanel("images/logo.png");
        logoLabel.setBounds(20, 10, 90, 90); 
        headerArea.add(logoLabel);

        JLabel mainTitle = new JLabel("GreenLoop");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 52)); 
        mainTitle.setForeground(new Color(46, 125, 50)); 
        mainTitle.setBounds(125, 12, 350, 55);
        headerArea.add(mainTitle);

        JLabel subTitle = new JLabel("PACKAGING MANAGEMENT SYSTEM");
        subTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        subTitle.setForeground(new Color(80, 90, 80)); 
        subTitle.setBounds(130, 68, 350, 20);
        headerArea.add(subTitle);

        ElegantDivider divider = new ElegantDivider();
        divider.setBounds(20, 105, 460, 20);
        headerArea.add(divider);

        mainPanel.add(headerArea);

        // --- LOGIN BOX (WHITE CARD) ---
        RoundedPanel loginBox = new RoundedPanel(25); 
        loginBox.setBounds(280, 180, 440, 460);
        mainPanel.add(loginBox);

        JLabel welcome = new JLabel("Welcome Back!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcome.setForeground(new Color(46, 125, 50));
        welcome.setBounds(0, 25, 440, 32);
        loginBox.add(welcome);

        JLabel msg = new JLabel("Please login to access your account", SwingConstants.CENTER);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        msg.setForeground(new Color(120, 120, 120));
        msg.setBounds(0, 58, 440, 20);
        loginBox.add(msg);

        // Username Textfield
        JLabel uLabel = new JLabel("Username");
        uLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        uLabel.setForeground(new Color(50, 50, 50));
        uLabel.setBounds(40, 95, 360, 20);
        loginBox.add(uLabel);

        usernameField = new RoundedTextField("Enter username", "user"); 
        usernameField.setBounds(40, 118, 360, 44);
        loginBox.add(usernameField);

        // Password Textfield
        JLabel pLabel = new JLabel("Password");
        pLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pLabel.setForeground(new Color(50, 50, 50));
        pLabel.setBounds(40, 178, 360, 20);
        loginBox.add(pLabel);

        passwordField = new RoundedPasswordField("Enter password"); 
        passwordField.setBounds(40, 200, 360, 44);
        loginBox.add(passwordField);

        // Remember Me Checkbox
        JCheckBox remember = new JCheckBox("Remember me");
        remember.setBackground(Color.WHITE);
        remember.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        remember.setForeground(new Color(100, 100, 100));
        remember.setBounds(36, 258, 140, 25);
        loginBox.add(remember);

        // Forgot Password
        JLabel forgot = new JLabel("Forgot Password?", SwingConstants.RIGHT);
        forgot.setForeground(new Color(46, 125, 50));
        forgot.setFont(new Font("Segoe UI", Font.BOLD, 13));
        forgot.setBounds(240, 258, 160, 25);
        forgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBox.add(forgot);
        forgot.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        new ForgotPasswordFrame();
    }
});

        // --- LOGIN BUTTON (DIRECT REDIRECT) ---
        RoundedButton loginBtn = new RoundedButton("Login", new Color(46, 125, 50), Color.WHITE, true);
        loginBtn.setBounds(40, 300, 360, 46);
        
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // User details nathi nam error eka pennanawa
                if (username.isEmpty() || username.equals("Enter username") || password.isEmpty() || password.equals("Enter password")) {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Please enter both Username and Password!", 
                        "Login Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 🚀 Login eka success nam Dashboard ekata maru wena code eka
                    DashboardFrame dashboard = new DashboardFrame();
                    dashboard.setVisible(true); // Dashboard eka pennanawa
                    dispose(); // Me thiyena login screen eka close karanawa
                }
            }
        });
        loginBox.add(loginBtn);

        // Or Separator Line
        OrSeparator orPanel = new OrSeparator();
        orPanel.setBounds(0, 358, 440, 25);
        loginBox.add(orPanel);

        // Create Account Button
        RoundedButton createBtn = new RoundedButton("Create New Account", Color.WHITE, new Color(46, 125, 50), false);
        createBtn.setBorderColor(new Color(46, 125, 50));
        createBtn.setBounds(40, 392, 360, 46);
        loginBox.add(createBtn);
        createBtn.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new RegisterFrame();
        dispose();
    }
});

        // Footer Text
        JLabel footer = new JLabel("© 2026 GreenLoop. All rights reserved.", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(new Color(100, 100, 100));
        footer.setBounds(0, 670, 1000, 30);
        mainPanel.add(footer);

        setVisible(true);
    }

    // --- UI DESIGN COMPONENTS ---

    class BackgroundPanel extends JPanel {
        private Image bgImg = null;
        public BackgroundPanel() {
            try {
                File f = new File("images/background.png");
                if (f.exists()) bgImg = new ImageIcon("images/background.png").getImage();
            } catch (Exception e) {}
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImg != null) {
                g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(245, 248, 245));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    class CustomImagePanel extends JComponent {
        private Image img = null;
        public CustomImagePanel(String path) {
            try {
                File f = new File(path);
                if (f.exists()) img = new ImageIcon(path).getImage();
            } catch (Exception e) {}
        }
        @Override
        protected void paintComponent(Graphics g) {
            if (img != null) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    class ElegantDivider extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 215, 200));
            g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
            g2.dispose();
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
            g2.setColor(new Color(230, 235, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    class RoundedTextField extends JTextField {
        private String hint; private String type;
        public RoundedTextField(String hint, String type) {
            this.hint = hint; this.type = type; setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 40));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(new Color(215, 220, 215)); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            if (getText().isEmpty()) {
                g2.setColor(new Color(170, 175, 170));
                g2.drawString(hint, 15, getHeight() / 2 + 5);
            }
            // User Icon Draw
            g2.setColor(new Color(150, 160, 150));
            g2.drawOval(getWidth() - 30, getHeight()/2 - 8, 8, 8);
            g2.drawArc(getWidth() - 34, getHeight()/2 + 2, 16, 10, 0, 180);
            g2.dispose(); super.paintComponent(g);
        }
    }

    class RoundedPasswordField extends JPasswordField {
        private String hint;
        public RoundedPasswordField(String hint) {
            this.hint = hint; setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 40));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(new Color(215, 220, 215)); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            if (getPassword().length == 0) {
                g2.setColor(new Color(170, 175, 170));
                g2.drawString(hint, 15, getHeight() / 2 + 5);
            }
            // Eye Icon Draw
            g2.setColor(new Color(150, 160, 150));
            g2.drawOval(getWidth() - 28, getHeight()/2 - 4, 8, 8);
            g2.drawArc(getWidth() - 34, getHeight()/2 - 8, 20, 16, 0, -180);
            g2.dispose(); super.paintComponent(g);
        }
    }

    class RoundedButton extends JButton {
        private Color bg; private Color borderColor = null; private boolean icon;
        public RoundedButton(String text, Color bg, Color fg, boolean icon) {
            super(text); this.bg = bg; this.icon = icon;
            setOpaque(false); setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
            setForeground(fg); setFont(new Font("Segoe UI", Font.BOLD, 14)); setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        public void setBorderColor(Color color) { this.borderColor = color; }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            if (borderColor != null) {
                g2.setColor(borderColor); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            g2.dispose(); super.paintComponent(g);
        }
    }

    class OrSeparator extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(225, 230, 225));
            g2.drawLine(40, getHeight()/2, 190, getHeight()/2);
            g2.drawLine(250, getHeight()/2, 400, getHeight()/2);
            g2.setColor(new Color(130, 140, 130));
            g2.drawString("or", 213, getHeight()/2 + 5);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}