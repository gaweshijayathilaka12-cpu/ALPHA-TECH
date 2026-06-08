import javax.swing.*;
import java.awt.*;

public class ForgotPasswordFrame extends JFrame {

    private Image bgImage;

    public ForgotPasswordFrame() {

        setTitle("GreenLoop - Reset Password");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load Background Image
        bgImage = new ImageIcon("images/background.png").getImage();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setLayout(null);
        setContentPane(panel);

        // White Card
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBounds(150, 80, 400, 280);

        JLabel title = new JLabel("Reset Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(95, 20, 250, 30);

        JLabel msg = new JLabel(
            "<html><center>Enter your email address and we'll send a reset link.</center></html>"
        );
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        msg.setBounds(40, 55, 320, 40);

        JLabel emailLbl = new JLabel("Email Address");
        emailLbl.setBounds(40, 110, 120, 20);

        JTextField emailField = new JTextField();
        emailField.setBounds(40, 135, 320, 40);

        JButton sendBtn = new JButton("Send Reset Link");
        sendBtn.setBounds(40, 195, 320, 40);
        sendBtn.setBackground(new Color(46, 125, 50));
        sendBtn.setForeground(Color.WHITE);

        sendBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Password reset link sent successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        card.add(title);
        card.add(msg);
        card.add(emailLbl);
        card.add(emailField);
        card.add(sendBtn);

        panel.add(card);

        setVisible(true);
    }
}