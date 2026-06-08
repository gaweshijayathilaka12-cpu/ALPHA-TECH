import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

public class DeliveryAssignmentPanel extends JPanel {

    public DeliveryAssignmentPanel() {
        // Main Setup
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 250, 252));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Header Section
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Center Content (Top Cards + Table)
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);

        // Top 3 Cards Container
        JPanel topCards = new JPanel(new GridLayout(1, 3, 20, 20));
        topCards.setOpaque(false);
        topCards.add(createAssignmentDetailsCard());
        topCards.add(createOrderSummaryCard());
        topCards.add(createAssignedRouteCard());

        centerPanel.add(topCards, BorderLayout.NORTH);
        centerPanel.add(createTableCard(), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);

        JLabel title = new JLabel("Delivery Assignment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(15, 23, 42));

        JLabel subtitle = new JLabel("Assign delivery agents to orders and track delivery status.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(subtitle);

        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnBox.setOpaque(false);

        JButton btnMap = createStyledButton("View Map", Color.WHITE, Color.BLACK, new Color(200, 200, 200));
        JButton btnAssign = createStyledButton("+ Assign New Delivery", new Color(34, 139, 34), Color.WHITE,
                new Color(34, 139, 34));

        btnBox.add(btnMap);
        btnBox.add(btnAssign);

        header.add(titleBox, BorderLayout.WEST);
        header.add(btnBox, BorderLayout.EAST);

        return header;
    }

    // ================= CARD 1: ASSIGNMENT DETAILS =================
    private JPanel createAssignmentDetailsCard() {
        JPanel card = createBaseCard("Assignment Details");

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.weightx = 1.0;

        addFormField(form, gbc, 0, "Order *", new JComboBox<>(new String[] { "ORD006 - Eco Mart" }));
        addFormField(form, gbc, 1, "Delivery Agent *", new JComboBox<>(new String[] { "D003 - Kasun Fernando" }));
        addFormField(form, gbc, 2, "Vehicle *", new JComboBox<>(new String[] { "WP - EF 9012" }));
        addFormField(form, gbc, 3, "Assignment Date *", new JTextField("17-05-2026"));
        addFormField(form, gbc, 4, "Estimated Delivery Date *", new JTextField("20-05-2026"));
        addFormField(form, gbc, 5, "Priority", new JComboBox<>(new String[] { "Normal", "High" }));

        JTextArea notesArea = new JTextArea("Please deliver between 9 AM - 5 PM.\nCall customer before delivery.");
        notesArea.setRows(3);
        notesArea.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addFormField(form, gbc, 6, "Notes", new JScrollPane(notesArea));

        card.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnSave = createStyledButton("Assign Delivery", new Color(34, 139, 34), Color.WHITE,
                new Color(34, 139, 34));
        JButton btnClear = createStyledButton("Clear", Color.WHITE, Color.BLACK, new Color(203, 213, 225));

        btnPanel.add(btnSave);
        btnPanel.add(btnClear);
        card.add(btnPanel, BorderLayout.SOUTH);

        return card;
    }

    // ================= CARD 2: ORDER SUMMARY =================
    private JPanel createOrderSummaryCard() {
        JPanel card = createBaseCard("Order Summary");

        JPanel details = new JPanel(new GridLayout(7, 2, 10, 15));
        details.setBackground(Color.WHITE);
        details.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        addSummaryRow(details, "Client", "Eco Mart (C001)");
        addSummaryRow(details, "Order Date", "17-05-2026");
        addSummaryRow(details, "Payment Method", "Cash on Delivery");
        addSummaryRow(details, "Total Items", "3");
        addSummaryRow(details, "Subtotal (Rs.)", "1,325.00");
        addSummaryRow(details, "Discount (Rs.)", "0.00");
        addSummaryRow(details, "Tax (Rs.)", "0.00");

        card.add(details, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(240, 253, 244)); // Light green
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(187, 247, 208)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTotalText = new JLabel("Grand Total (Rs.)");
        lblTotalText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalText.setForeground(new Color(22, 101, 52));

        JLabel lblTotalAmount = new JLabel("1,325.00");
        lblTotalAmount.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotalAmount.setForeground(new Color(22, 101, 52));

        totalPanel.add(lblTotalText, BorderLayout.WEST);
        totalPanel.add(lblTotalAmount, BorderLayout.EAST);

        card.add(totalPanel, BorderLayout.SOUTH);

        return card;
    }

    // ================= CARD 3: ASSIGNED ROUTE =================
    private JPanel createAssignedRouteCard() {
        JPanel card = createBaseCard("Assigned Route (3 Stops)");

        JLabel mapArea = new JLabel("MAP PLACEHOLDER", SwingConstants.CENTER);
        mapArea.setPreferredSize(new Dimension(200, 160));
        mapArea.setBackground(new Color(233, 236, 239));
        mapArea.setOpaque(true);
        mapArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.add(mapArea, BorderLayout.NORTH);

        JPanel routeList = new JPanel();
        routeList.setLayout(new BoxLayout(routeList, BoxLayout.Y_AXIS));
        routeList.setBackground(Color.WHITE);
        routeList.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        routeList.add(createRouteItem("1", "Eco Mart (C001)", "123, Galle Road, Colombo 03.", "Pickup",
                new Color(34, 139, 34)));
        routeList.add(
                createRouteItem("2", "Green Store (C002)", "45, Kandy Road, Kandy.", "Stop 1", new Color(37, 99, 235)));
        routeList.add(createRouteItem("3", "Nature Lanka (C003)", "78, Negombo Road, Negombo.", "Stop 2",
                new Color(234, 88, 12)));

        card.add(routeList, BorderLayout.CENTER);

        return card;
    }

    // ================= TABLE SECTION =================
    private JPanel createTableCard() {
        JPanel card = createBaseCard("Today's Assignments");

        // Top Filter Bar
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterBar.setBackground(Color.WHITE);
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JTextField searchField = new JTextField("Search assignments...");
        searchField.setPreferredSize(new Dimension(200, 30));
        JComboBox<String> statusFilter = new JComboBox<>(new String[] { "All Status" });
        statusFilter.setPreferredSize(new Dimension(120, 30));

        filterBar.add(searchField);
        filterBar.add(statusFilter);
        card.add(filterBar, BorderLayout.NORTH);

        // Table
        String[] columns = { "Assignment ID", "Order ID", "Client", "Delivery Agent", "Vehicle No.", "Assigned Date",
                "Est. Delivery Date", "Status", "Actions" };
        Object[][] data = {
                { "ASG1005", "ORD006", "Eco Mart", "Kasun Fernando (D003)", "WP - EF 9012", "17-05-2026", "20-05-2026",
                        "Assigned", "View | Edit" },
                { "ASG1004", "ORD005", "Green Store", "Nimal Silva (D002)", "WP - CD 5678", "17-05-2026", "19-05-2026",
                        "In Transit", "View | Edit" },
                { "ASG1003", "ORD004", "Nature Lanka", "Saman Perera (D001)", "WP - AB 1234", "17-05-2026",
                        "18-05-2026", "Delivered", "View | Edit" },
                { "ASG1002", "ORD003", "Organic Shop", "Ruwan Jayasekara (D004)", "WP - GH 3456", "16-05-2026",
                        "18-05-2026", "Delivered", "View | Edit" },
                { "ASG1001", "ORD002", "Daily Needs", "Tharindu Lakmal (D005)", "WP - IJ 7890", "16-05-2026",
                        "19-05-2026", "Pending", "View | Edit" }
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(240, 253, 244));
        table.getTableHeader().setForeground(new Color(22, 101, 52));
        table.getTableHeader().setPreferredSize(new Dimension(100, 35));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        card.add(scrollPane, BorderLayout.CENTER);

        // Pagination
        JPanel pagination = new JPanel(new BorderLayout());
        pagination.setBackground(Color.WHITE);
        pagination.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel entriesInfo = new JLabel("Showing 1 to 5 of 5 entries");
        entriesInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        entriesInfo.setForeground(Color.GRAY);

        JPanel pageBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pageBtns.setOpaque(false);
        pageBtns.add(createStyledButton("Previous", Color.WHITE, Color.GRAY, Color.LIGHT_GRAY));
        pageBtns.add(createStyledButton("1", new Color(22, 101, 52), Color.WHITE, new Color(22, 101, 52)));
        pageBtns.add(createStyledButton("Next", Color.WHITE, Color.GRAY, Color.LIGHT_GRAY));

        pagination.add(entriesInfo, BorderLayout.WEST);
        pagination.add(pageBtns, BorderLayout.EAST);

        card.add(pagination, BorderLayout.SOUTH);

        return card;
    }

    // ================= UTILITY METHODS =================

    private JPanel createBaseCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(22, 101, 52));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        return card;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int y, String labelText, JComponent field) {
        gbc.gridy = y;

        gbc.gridx = 0;
        gbc.weightx = 0.3;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        if (field instanceof JTextField || field instanceof JComboBox) {
            field.setPreferredSize(new Dimension(150, 30));
        }
        panel.add(field, gbc);
    }

    private void addSummaryRow(JPanel panel, String label, String value) {
        JLabel lblLeft = new JLabel(label);
        lblLeft.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblLeft.setForeground(Color.DARK_GRAY);

        JLabel lblRight = new JLabel(value, SwingConstants.RIGHT);
        lblRight.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRight.setForeground(Color.BLACK);

        panel.add(lblLeft);
        panel.add(lblRight);
    }

    private JPanel createRouteItem(String num, String title, String address, String type, Color color) {
        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)));

        JLabel icon = new JLabel(" " + num + " ");
        icon.setOpaque(true);
        icon.setBackground(color);
        icon.setForeground(Color.WHITE);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JPanel texts = new JPanel(new GridLayout(2, 1));
        texts.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JLabel lblAddr = new JLabel(address);
        lblAddr.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblAddr.setForeground(Color.GRAY);
        texts.add(lblTitle);
        texts.add(lblAddr);

        JLabel lblType = new JLabel(type);
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblType.setForeground(color);

        item.add(icon, BorderLayout.WEST);
        item.add(texts, BorderLayout.CENTER);
        item.add(lblType, BorderLayout.EAST);

        return item;
    }

    private JButton createStyledButton(String text, Color bg, Color fg, Color border) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border, 1, true),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}