import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

public class OrderProcessingPanel extends JPanel {

    public OrderProcessingPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(248, 250, 252)); // Light background to make panels pop
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- HEADER SECTION ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Order Processing");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(15, 23, 42));

        JLabel subtitle = new JLabel("Create new orders, manage order items and track order status.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        JButton backBtn = new JButton("← Back to Orders");
        styleButton(backBtn, Color.WHITE, new Color(51, 65, 85), new Color(226, 232, 240));

        header.add(titlePanel, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // --- CENTER SECTION (Forms & Tables) ---
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setOpaque(false);

        JPanel topSection = new JPanel(new GridLayout(1, 2, 15, 15));
        topSection.setOpaque(false);

        topSection.add(createOrderDetailsPanel());
        topSection.add(createOrderItemsPanel());

        centerPanel.add(topSection, BorderLayout.NORTH);
        centerPanel.add(createRecentOrdersPanel(), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createOrderDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTitle = new JLabel(" 📋 Order Details");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(22, 101, 52));
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {
                "<html>Order ID <font color='red'>*</font></html>",
                "<html>Client <font color='red'>*</font></html>",
                "<html>Order Date <font color='red'>*</font></html>",
                "<html>Delivery Agent <font color='red'>*</font></html>",
                "Delivery Date",
                "<html>Status <font color='red'>*</font></html>",
                "Payment Method",
                "Order Notes"
        };

        Component[] fields = {
                createStyledTextField("ORD006", false),
                createStyledComboBox(new String[] { "Eco Mart (C001)" }),
                createStyledTextField("17-05-2026 📅", true),
                createStyledComboBox(new String[] { "Saman Perera (D001)" }),
                createStyledTextField("20-05-2026 📅", true),
                createStyledComboBox(new String[] { "Assigned" }),
                createStyledComboBox(new String[] { "Cash on Delivery" }),
                new JScrollPane(createStyledTextArea("Please deliver before 5 PM.\nCall customer prior to delivery."))
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            formPanel.add(lbl, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            if (i == 7) {
                fields[i].setPreferredSize(new Dimension(200, 60));
            }
            formPanel.add(fields[i], gbc);
        }

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(240, 253, 244)); // Light Green
        totalPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(187, 247, 208), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lbl1 = new JLabel("Grand Total (Rs.)");
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl1.setForeground(new Color(22, 101, 52));

        JLabel lbl2 = new JLabel("1,325.00");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbl2.setForeground(new Color(22, 101, 52));

        totalPanel.add(lbl1, BorderLayout.WEST);
        totalPanel.add(lbl2, BorderLayout.EAST);

        panel.add(totalPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrderItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTitle = new JLabel(" Order Items");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(22, 101, 52));
        panel.add(lblTitle, BorderLayout.NORTH);

        String[] cols = { "#", "Product", "Quantity", "Unit Price (Rs.)", "Total (Rs.)", "Actions" };
        Object[][] data = {
                { 1, "Biodegradable Bag (Small)", "20  ↕", "25.00", "500.00", "🗑" },
                { 2, "Recycled Paper Box", "10  ↕", "60.00", "600.00", "🗑" },
                { 3, "Compostable Food Box", "5  ↕", "45.00", "225.00", "🗑" }
        };

        JTable table = new JTable(new DefaultTableModel(data, cols));
        styleTable(table);
        styleActionColumn(table, 5, Color.RED);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(226, 232, 240)));
        scrollPane.setPreferredSize(new Dimension(400, 150));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPanel.setOpaque(false);
        JButton addBtn = new JButton("+ Add Item");
        styleButton(addBtn, new Color(34, 139, 34), Color.WHITE, null);
        JButton clearBtn = new JButton("↻ Clear Items");
        styleButton(clearBtn, new Color(241, 245, 249), new Color(51, 65, 85), new Color(203, 213, 225));
        btnPanel.add(addBtn);
        btnPanel.add(clearBtn);

        JPanel summary = new JPanel(new GridLayout(4, 2, 5, 8));
        summary.setOpaque(false);
        summary.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10));

        addSummaryRow(summary, "Subtotal (Rs.)", "1,325.00", false);
        addSummaryRow(summary, "Discount (Rs.)", "0.00", true);
        addSummaryRow(summary, "Tax (Rs.)", "0.00", false);

        JLabel lblGrandTxt = new JLabel("Grand Total (Rs.)");
        lblGrandTxt.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lblGrandVal = new JLabel("1,325.00", SwingConstants.RIGHT);
        lblGrandVal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGrandVal.setForeground(new Color(22, 101, 52));

        summary.add(lblGrandTxt);
        summary.add(lblGrandVal);

        bottom.add(btnPanel, BorderLayout.WEST);
        bottom.add(summary, BorderLayout.EAST);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRecentOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JLabel lblTitle = new JLabel("Recent Orders");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(22, 101, 52));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);
        JTextField searchField = createStyledTextField("Search orders...      🔍", true);
        searchField.setPreferredSize(new Dimension(200, 32));
        JComboBox<String> statusFilter = createStyledComboBox(new String[] { "All Status" });

        filterPanel.add(searchField);
        filterPanel.add(statusFilter);

        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(filterPanel, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        String[] cols = { "Order ID", "Client", "Order Date", "Total (Rs.)", "Status", "Delivery Agent",
                "Delivery Date", "Actions" };
        Object[][] data = {
                { "ORD005", "Green Store", "16-05-2026", "875.00", "Pending", "Nimal Silva (D002)", "19-05-2026",
                        "👁 ✏ 🗑" },
                { "ORD004", "Nature Lanka", "16-05-2026", "1,510.00", "Delivered", "Kasun Fernando (D003)",
                        "18-05-2026", "👁 ✏ 🗑" },
                { "ORD003", "Organic Shop", "15-05-2026", "960.00", "Assigned", "Ruwan Jayasekara (D004)", "18-05-2026",
                        "👁 ✏ 🗑" },
                { "ORD002", "Daily Needs", "15-05-2026", "645.00", "Pending", "Tharindu Lakmal (D005)", "19-05-2026",
                        "👁 ✏ 🗑" },
                { "ORD001", "Eco Mart", "15-05-2026", "1,250.00", "Delivered", "Saman Perera (D001)", "17-05-2026",
                        "👁 ✏ 🗑" }
        };

        JTable table = new JTable(new DefaultTableModel(data, cols));
        styleTable(table);

        // Custom renderer for Status column
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object val, boolean isS, boolean hasF, int r,
                    int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, val, isS, hasF, r, c);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setFont(new Font("Segoe UI", Font.BOLD, 11));
                l.setOpaque(true);
                if ("Pending".equals(val)) {
                    l.setBackground(new Color(255, 243, 224));
                    l.setForeground(new Color(230, 124, 11));
                } else if ("Assigned".equals(val)) {
                    l.setBackground(new Color(227, 242, 253));
                    l.setForeground(new Color(21, 101, 192));
                } else {
                    l.setBackground(new Color(230, 245, 232));
                    l.setForeground(new Color(46, 125, 50));
                }
                return l;
            }
        });

        styleActionColumn(table, 7, new Color(59, 130, 246));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(226, 232, 240)));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Footer of Recent Orders
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setOpaque(false);
        bottomBar.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionButtons.setOpaque(false);
        JButton placeBtn = new JButton("🛒 Place Order");
        styleButton(placeBtn, new Color(37, 99, 235), Color.WHITE, null); // Blue
        JButton cancelBtn = new JButton("✕ Cancel");
        styleButton(cancelBtn, new Color(239, 68, 68), Color.WHITE, null); // Red
        JButton clearBtn = new JButton("↻ Clear");
        styleButton(clearBtn, new Color(241, 245, 249), new Color(51, 65, 85), new Color(203, 213, 225));

        actionButtons.add(placeBtn);
        actionButtons.add(cancelBtn);
        actionButtons.add(clearBtn);

        JLabel lblShowing = new JLabel("Showing 1 to 5 of 5 entries");
        lblShowing.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblShowing.setForeground(new Color(100, 116, 139));

        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        paginationPanel.setOpaque(false);
        JButton prev = new JButton("Previous");
        styleButton(prev, Color.WHITE, Color.GRAY, new Color(226, 232, 240));
        JButton page1 = new JButton("1");
        styleButton(page1, new Color(22, 101, 52), Color.WHITE, null); // Active green page
        JButton next = new JButton("Next");
        styleButton(next, Color.WHITE, Color.GRAY, new Color(226, 232, 240));

        paginationPanel.add(prev);
        paginationPanel.add(page1);
        paginationPanel.add(next);

        JPanel infoAndPagination = new JPanel(new BorderLayout());
        infoAndPagination.setOpaque(false);
        infoAndPagination.add(lblShowing, BorderLayout.WEST);
        infoAndPagination.add(paginationPanel, BorderLayout.EAST);

        bottomBar.add(actionButtons, BorderLayout.WEST);
        bottomBar.add(infoAndPagination, BorderLayout.EAST);

        panel.add(bottomBar, BorderLayout.SOUTH);

        return panel;
    }

    // --- UTILITY METHODS FOR STYLING ---

    private JTextField createStyledTextField(String text, boolean isEditable) {
        JTextField tf = new JTextField(text);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(new CompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        tf.setBackground(isEditable ? Color.WHITE : new Color(248, 250, 252));
        tf.setEditable(isEditable);
        return tf;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBackground(Color.WHITE);
        return cb;
    }

    private JTextArea createStyledTextArea(String text) {
        JTextArea ta = new JTextArea(text);
        ta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        return ta;
    }

    private void styleButton(JButton btn, Color bg, Color fg, Color border) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (border != null) {
            btn.setBorder(new CompoundBorder(
                    new LineBorder(border, 1, true),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        } else {
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        }
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(241, 245, 249));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(240, 253, 244)); // Light green header
        table.getTableHeader().setForeground(new Color(22, 101, 52));
        table.getTableHeader().setPreferredSize(new Dimension(100, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    }

    private void styleActionColumn(JTable table, int colIndex, Color fgColor) {
        table.getColumnModel().getColumn(colIndex).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object val, boolean isS, boolean hasF, int r,
                    int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, val, isS, hasF, r, c);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
                l.setForeground(fgColor); // Red for trash, Blue for views
                return l;
            }
        });
    }

    private void addSummaryRow(JPanel panel, String label, String val, boolean isInput) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lbl);

        if (isInput) {
            JTextField tf = createStyledTextField(val, true);
            tf.setHorizontalAlignment(JTextField.RIGHT);
            panel.add(tf);
        } else {
            JLabel lblVal = new JLabel(val, SwingConstants.RIGHT);
            lblVal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panel.add(lblVal);
        }
    }
}