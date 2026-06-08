import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DeliveryAgentPanel extends JPanel {

    // Define Theme Colors matching the GreenLoop aesthetic
    private final Color themeGreen = new Color(24, 90, 45);
    private final Color bgColor = new Color(250, 250, 250);

    public DeliveryAgentPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(bgColor);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Header Panel (Title and Search)
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Main Content Grid (4 Quadrants)
        JPanel mainContent = new JPanel(new GridLayout(2, 2, 20, 20));
        mainContent.setBackground(bgColor);

        mainContent.add(createAgentDetailsPanel());
        mainContent.add(createAgentListPanel());
        mainContent.add(createAgentPerformancePanel());
        mainContent.add(createAssignDeliveryPanel());

        add(mainContent, BorderLayout.CENTER);
    }

    // --- Header Section ---
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(bgColor);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(bgColor);
        JLabel titleLabel = new JLabel("Delivery Agent Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(themeGreen);
        JLabel subtitleLabel = new JLabel("Add, update, delete and manage delivery agents.");
        subtitleLabel.setForeground(Color.GRAY);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(bgColor);
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(themeGreen);
        searchBtn.setForeground(Color.WHITE);
        searchPanel.add(new JLabel("Search agents... "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    // --- Top Left: Agent Details Form ---
    private JPanel createAgentDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                "Agent Details", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), themeGreen));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        String[] labels = {"Agent ID *", "Agent Name *", "Phone *", "Email", "Address", "Vehicle No. *", "Status *"};
        int row = 0;

        for (String labelText : labels) {
            gbc.gridx = 0; gbc.gridy = row;
            gbc.weightx = 0.3;
            panel.add(new JLabel(labelText), gbc);

            gbc.gridx = 1; gbc.gridy = row;
            gbc.weightx = 0.7;
            if (labelText.contains("Status")) {
                panel.add(new JComboBox<>(new String[]{"Active", "Inactive"}), gbc);
            } else {
                panel.add(new JTextField(15), gbc);
            }
            row++;
        }

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(themeGreen);
        saveBtn.setForeground(Color.WHITE);
        JButton clearBtn = new JButton("Clear");
        btnPanel.add(saveBtn);
        btnPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        return panel;
    }

    // --- Top Right: Agent List Table ---
    private JPanel createAgentListPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                "Agent List (Active)", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), themeGreen));

        String[] cols = {"ID", "Name", "Phone", "Vehicle No.", "Status", "Actions"};
        Object[][] data = {
                {"D001", "Saman Perera", "071 987 6543", "WP - AB 1234", "Active", "..."},
                {"D002", "Nimal Silva", "072 222 3344", "WP - CD 5678", "Active", "..."},
                {"D003", "Kasun Fernando", "070 333 4555", "WP - EF 9012", "Active", "..."}
        };

        JTable table = new JTable(new DefaultTableModel(data, cols));
        table.setRowHeight(30);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton viewAllBtn = new JButton("View All Agents");
        JPanel btnWrap = new JPanel();
        btnWrap.setBackground(Color.WHITE);
        btnWrap.add(viewAllBtn);
        panel.add(btnWrap, BorderLayout.SOUTH);

        return panel;
    }

    // --- Bottom Left: Agent Performance ---
    private JPanel createAgentPerformancePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                "Agent Performance (This Month)", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), themeGreen));

        // Stat Cards Top
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.add(createStatCard("DELIVERIES", "62", new Color(40, 167, 69)));
        statsPanel.add(createStatCard("DELIVERED", "58", new Color(0, 123, 255)));
        statsPanel.add(createStatCard("PENDING", "4", new Color(253, 126, 20)));
        statsPanel.add(createStatCard("CANCELLED", "0", new Color(111, 66, 193)));
        panel.add(statsPanel, BorderLayout.NORTH);

        // Performance Table Bottom
        String[] cols = {"Agent ID", "Agent Name", "Total", "Delivered", "Pending", "Success Rate"};
        Object[][] data = {
                {"D001", "Saman Perera", "68", "65", "3", "95.59%"},
                {"D002", "Nimal Silva", "54", "51", "2", "94.44%"}
        };
        JTable table = new JTable(new DefaultTableModel(data, cols));
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    // Helper for Stat Cards
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        JLabel lblTitle = new JLabel(" " + title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblTitle.setForeground(Color.GRAY);
        JLabel lblVal = new JLabel(" " + value);
        lblVal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblVal.setForeground(color);
        card.add(lblTitle);
        card.add(lblVal);
        return card;
    }

    // --- Bottom Right: Assign New Delivery ---
    private JPanel createAssignDeliveryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                "Assign New Delivery", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), themeGreen));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        int row = 0;
        gbc.gridy = row++; panel.add(new JLabel("Order *"), gbc);
        gbc.gridy = row++; panel.add(new JComboBox<>(new String[]{"ORD006 - Eco Mart"}), gbc);
        
        gbc.gridy = row++; panel.add(new JLabel("Delivery Agent *"), gbc);
        gbc.gridy = row++; panel.add(new JComboBox<>(new String[]{"D003 - Kasun Fernando"}), gbc);
        
        gbc.gridy = row++; panel.add(new JLabel("Assigned Date *"), gbc);
        gbc.gridy = row++; panel.add(new JTextField("17-05-2026"), gbc);
        
        gbc.gridy = row++; panel.add(new JLabel("Notes"), gbc);
        gbc.gridy = row++; panel.add(new JTextField("Please deliver between 9 AM - 5 PM."), gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        JButton assignBtn = new JButton("Assign");
        assignBtn.setBackground(themeGreen);
        assignBtn.setForeground(Color.WHITE);
        JButton clearBtn = new JButton("Clear");
        btnPanel.add(assignBtn);
        btnPanel.add(clearBtn);

        gbc.gridy = row++;
        panel.add(btnPanel, gbc);

        return panel;
    }
}