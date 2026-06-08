import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.Enumeration;

public class InventoryManagementPanel extends JPanel {

    // --- Exact Colors from Design ---
    private final Color THEME_DARK_GREEN = new Color(13, 79, 41);
    private final Color APP_BG = new Color(248, 250, 252);
    private final Color PRIMARY_BTN = new Color(39, 134, 67);
    private final Color BORDER_COLOR = new Color(226, 232, 240);
    private final Color TEXT_DARK = new Color(30, 41, 59);
    private final Color TEXT_GRAY = new Color(100, 116, 139);
    private final Color TABLE_HEADER_BG = new Color(234, 244, 237);

    // --- Form Components & Table Model ---
    private JTextField txtQuantity;
    private JTextField txtReorder;
    private JTextField txtNotes;
    private JTextField txtDate;
    private JComboBox<String> cmbProduct;
    private DefaultTableModel tableModel;

    public InventoryManagementPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(APP_BG);
        setBorder(new EmptyBorder(30, 35, 30, 35));

        // Modern UI Font Configuration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 13));
        } catch (Exception e) { 
            // Ignore if font configuration fails, defaults will be used
        }

        // Initialize Form Inputs
        txtQuantity = createRoundedTextField("50");
        txtReorder = createRoundedTextField("20");
        txtNotes = createRoundedTextField("Stock received from GreenPack Suppliers.");
        txtDate = createRoundedTextField("17-05-2026");
        cmbProduct = createRoundedCombo(new String[]{
            "Biodegradable Bag (Small) (P001)", 
            "Biodegradable Bag (Large) (P002)", 
            "Recycled Paper Box (P003)"
        });

        // Add Main Layout Components
        add(createHeader(), BorderLayout.NORTH);
        add(createMainScrollContent(), BorderLayout.CENTER);
    }

    // ==========================================
    // 1. HEADER SECTION
    // ==========================================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(APP_BG);
        
        JLabel lblTitle = new JLabel("<html><h1 style='color:#0d4f29; margin:0; font-family:Segoe UI; font-size:24px;'>Inventory Management</h1><p style='color:#64748b; font-size:12px; margin-top:2px;'>Track stock levels, update inventory and manage stock in and out.</p></html>");
        header.add(lblTitle, BorderLayout.WEST);

        JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionBtns.setBackground(APP_BG);
        
        JButton btnExport = createRoundedButton("\u2B73 Export Report", Color.WHITE, TEXT_DARK, BORDER_COLOR);
        JButton btnRefresh = createRoundedButton("\u27F3 Refresh", PRIMARY_BTN, Color.WHITE, PRIMARY_BTN);
        
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(InventoryManagementPanel.this, "Report Exported Successfully!", "Export", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(InventoryManagementPanel.this, "Data Refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        actionBtns.add(btnExport);
        actionBtns.add(btnRefresh);
        header.add(actionBtns, BorderLayout.EAST);

        return header;
    }

    // ==========================================
    // 2. MAIN SCROLLABLE CONTENT
    // ==========================================
    private JScrollPane createMainScrollContent() {
        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setBackground(APP_BG);

        centerWrapper.add(createStatsRow());
        centerWrapper.add(Box.createVerticalStrut(20));
        centerWrapper.add(createMiddleRow());
        centerWrapper.add(Box.createVerticalStrut(20));
        centerWrapper.add(createTableSection());

        JScrollPane mainScroll = new JScrollPane(centerWrapper);
        mainScroll.setBorder(null);
        mainScroll.getViewport().setBackground(APP_BG);
        mainScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);

        return mainScroll;
    }

    // ==========================================
    // 3. STATS CARDS ROW
    // ==========================================
    private JPanel createStatsRow() {
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsRow.setBackground(APP_BG);
        cardsRow.setPreferredSize(new Dimension(0, 120));
        cardsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        cardsRow.add(createCard("Total Products", "25", "View all products >", new Color(34, 197, 94), "\u25A4"));
        cardsRow.add(createCard("Total Stock Items", "125", "View all items >", new Color(249, 115, 22), "\u25F4"));
        cardsRow.add(createCard("Low Stock Items", "8", "View details >", new Color(239, 68, 68), "\u26A0"));
        cardsRow.add(createCard("Out of Stock Items", "2", "View details >", new Color(59, 130, 246), "\u2630"));

        return cardsRow;
    }

    private RoundedPanel createCard(String title, String value, String link, final Color circleBg, final String iconStr) {
        RoundedPanel card = new RoundedPanel(15, Color.WHITE, BORDER_COLOR);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 15, 20));

        JPanel topPart = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        topPart.setBackground(Color.WHITE);
        
        JPanel iconBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(circleBg);
                g2.fill(new Ellipse2D.Double(0, 0, 50, 50));
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
                FontMetrics fm = g2.getFontMetrics();
                int x = (50 - fm.stringWidth(iconStr)) / 2;
                int y = ((50 - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(iconStr, x, y);
            }
        };
        iconBox.setPreferredSize(new Dimension(50, 50));
        iconBox.setOpaque(false);

        JPanel textPart = new JPanel();
        textPart.setLayout(new BoxLayout(textPart, BoxLayout.Y_AXIS));
        textPart.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(TEXT_GRAY);
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setForeground(TEXT_DARK);
        
        textPart.add(lblTitle);
        textPart.add(lblValue);

        topPart.add(iconBox);
        topPart.add(textPart);

        JLabel lblLink = new JLabel(link);
        lblLink.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblLink.setForeground(TEXT_GRAY);
        lblLink.setHorizontalAlignment(SwingConstants.RIGHT);

        card.add(topPart, BorderLayout.CENTER);
        card.add(lblLink, BorderLayout.SOUTH);

        return card;
    }

    // ==========================================
    // 4. MIDDLE ROW (Form + Quick Actions)
    // ==========================================
    private JPanel createMiddleRow() {
        JPanel middleRow = new JPanel(new GridBagLayout());
        middleRow.setBackground(APP_BG);
        middleRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // --- FORM PANEL ---
        RoundedPanel formPanel = new RoundedPanel(15, Color.WHITE, BORDER_COLOR);
        formPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints fg = new GridBagConstraints();
        fg.fill = GridBagConstraints.HORIZONTAL; 
        fg.insets = new Insets(8, 0, 8, 20);

        JLabel lblFormTitle = new JLabel("Add / Stock In");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFormTitle.setForeground(THEME_DARK_GREEN);
        fg.gridx = 0; fg.gridy = 0; fg.gridwidth = 2; fg.insets = new Insets(0, 0, 15, 0);
        formPanel.add(lblFormTitle, fg);
        fg.gridwidth = 1; fg.insets = new Insets(8, 0, 8, 20);

        String[] labels = {"Product *", "Quantity *", "Reorder Level", "Notes", "Date *"};
        JComponent[] inputs = {cmbProduct, txtQuantity, txtReorder, txtNotes, txtDate};

        for (int i = 0; i < labels.length; i++) {
            fg.gridy = i + 1; fg.gridx = 0; fg.weightx = 0.2;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(TEXT_DARK);
            if(labels[i].contains("*")) lbl.setText("<html>" + labels[i].replace("*", "<span style='color:red'>*</span>") + "</html>");
            formPanel.add(lbl, fg);
            
            fg.gridx = 1; fg.weightx = 0.8;
            inputs[i].setPreferredSize(new Dimension(300, 35));
            formPanel.add(inputs[i], fg);
        }

        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        btnWrap.setBackground(Color.WHITE);
        
        JButton btnAdd = createRoundedButton("+ Add Stock", PRIMARY_BTN, Color.WHITE, PRIMARY_BTN);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String qty = txtQuantity.getText();
                String date = txtDate.getText();
                String reorder = txtReorder.getText();
                if(qty.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(InventoryManagementPanel.this, "Please fill required fields (*)", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String selectedProd = (String) cmbProduct.getSelectedItem();
                String prodName = selectedProd.substring(0, selectedProd.indexOf("(")).trim();
                String newId = "P" + String.format("%03d", tableModel.getRowCount() + 1);
                
                tableModel.addRow(new Object[]{newId, prodName, qty, reorder, "In Stock", date + " 10:00 AM", "actions"});
            }
        });

        JButton btnClear = createRoundedButton("\u27F3 Clear", new Color(241, 245, 249), TEXT_DARK, BORDER_COLOR);
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtQuantity.setText("");
                txtReorder.setText("");
                txtNotes.setText("");
                txtDate.setText("");
                if(cmbProduct.getItemCount() > 0) cmbProduct.setSelectedIndex(0);
            }
        });

        btnWrap.add(btnAdd);
        btnWrap.add(btnClear);
        fg.gridy = labels.length + 1; fg.gridx = 1; fg.insets = new Insets(10, 0, 0, 0);
        formPanel.add(btnWrap, fg);

        // --- QUICK ACTIONS PANEL ---
        RoundedPanel actionsPanel = new RoundedPanel(15, Color.WHITE, BORDER_COLOR);
        actionsPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        actionsPanel.setLayout(new BorderLayout());
        
        JLabel actTitle = new JLabel("Quick Actions");
        actTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        actTitle.setForeground(THEME_DARK_GREEN);
        actTitle.setBorder(new EmptyBorder(0, 0, 15, 0));
        actionsPanel.add(actTitle, BorderLayout.NORTH);

        JPanel actionGrid = new JPanel(new GridLayout(2, 2, 15, 15));
        actionGrid.setBackground(Color.WHITE);
        
        actionGrid.add(createQuickAction("\u2B63", "Stock In", "Add new stock", new Color(240, 253, 244), new Color(13, 90, 40), new Color(187, 247, 208)));
        actionGrid.add(createQuickAction("\u2B61", "Stock Out", "Remove stock", new Color(239, 246, 255), new Color(15, 50, 150), new Color(191, 219, 254)));
        actionGrid.add(createQuickAction("\u21C4", "Adjust Stock", "Modify stock level", new Color(250, 245, 255), new Color(70, 20, 120), new Color(233, 213, 255)));
        actionGrid.add(createQuickAction("\u25F7", "Stock History", "View history", new Color(255, 247, 237), new Color(150, 50, 10), new Color(254, 215, 170)));

        actionsPanel.add(actionGrid, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.weightx = 0.55; gbc.insets = new Insets(0, 0, 0, 10);
        middleRow.add(formPanel, gbc);
        gbc.gridx = 1; gbc.weightx = 0.45; gbc.insets = new Insets(0, 10, 0, 0);
        middleRow.add(actionsPanel, gbc);

        return middleRow;
    }

    private RoundedPanel createQuickAction(String iconStr, final String title, String sub, final Color bg, Color textCol, Color border) {
        final RoundedPanel pnl = new RoundedPanel(10, bg, border);
        pnl.setLayout(new GridBagLayout());
        pnl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        String html = "<html><center>"
                + "<span style='color:rgb("+textCol.getRed()+","+textCol.getGreen()+","+textCol.getBlue()+"); font-size:22px; font-weight:bold;'>"+iconStr+"</span><br/>"
                + "<b style='color:rgb("+textCol.getRed()+","+textCol.getGreen()+","+textCol.getBlue()+"); font-size:13px; font-family:Segoe UI;'>"+title+"</b><br/>"
                + "<span style='color:#64748b; font-size:11px; font-family:Segoe UI;'>"+sub+"</span>"
                + "</center></html>";
        
        JLabel lbl = new JLabel(html);
        pnl.add(lbl);

        pnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { pnl.setBackground(bg.darker()); pnl.repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { pnl.setBackground(bg); pnl.repaint(); }
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(InventoryManagementPanel.this, "Action Selected: " + title);
            }
        });

        return pnl;
    }

    // ==========================================
    // 5. TABLE SECTION
    // ==========================================
    private JPanel createTableSection() {
        RoundedPanel tableContainer = new RoundedPanel(15, Color.WHITE, BORDER_COLOR);
        tableContainer.setLayout(new BorderLayout());
        tableContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(Color.WHITE);
        topArea.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel title = new JLabel("Inventory List");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(THEME_DARK_GREEN);
        
        JPanel searchArea = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchArea.setBackground(Color.WHITE);
        searchArea.add(createRoundedTextField("Search product..."));
        searchArea.add(createRoundedCombo(new String[]{"All Status"}));
        
        topArea.add(title, BorderLayout.WEST);
        topArea.add(searchArea, BorderLayout.EAST);
        tableContainer.add(topArea, BorderLayout.NORTH);

        String[] columns = {"ID", "Product Name", "Stock", "Reorder Level", "Status", "Last Updated", "Actions"};
        Object[][] data = {
                {"P001", "Biodegradable Bag (Small)", "150", "20", "In Stock", "17-05-2026 09:45 AM", "actions"},
                {"P002", "Biodegradable Bag (Large)", "80", "20", "In Stock", "17-05-2026 09:20 AM", "actions"},
                {"P003", "Recycled Paper Box", "8", "15", "Low Stock", "17-05-2026 08:50 AM", "actions"},
                {"P004", "Compostable Food Box", "12", "10", "In Stock", "17-05-2026 09:10 AM", "actions"},
                {"P005", "Paper Wrap Roll", "30", "20", "In Stock", "17-05-2026 08:30 AM", "actions"},
                {"P006", "Biodegradable Cutlery Set", "0", "10", "Out of Stock", "17-05-2026 07:55 AM", "actions"},
                {"P007", "Compostable Bag (Medium)", "5", "10", "Low Stock", "17-05-2026 08:15 AM", "actions"},
                {"P008", "Recycled Tape", "60", "20", "In Stock", "17-05-2026 09:05 AM", "actions"}
        };

        tableModel = new DefaultTableModel(data, columns);
        final JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        table.setRowHeight(45);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_DARK);
        table.setSelectionBackground(new Color(241, 245, 249));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                int row = table.rowAtPoint(e.getPoint());
                if (col == 6 && row >= 0) {
                    String prod = table.getValueAt(row, 1).toString();
                    JOptionPane.showMessageDialog(InventoryManagementPanel.this, "Edit/View clicked for: " + prod);
                }
            }
        });

        JTableHeader th = table.getTableHeader();
        th.setBackground(TABLE_HEADER_BG);
        th.setForeground(THEME_DARK_GREEN);
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));
        th.setPreferredSize(new Dimension(100, 40));
        ((DefaultTableCellRenderer)th.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR), 
                    new EmptyBorder(0, 10, 0, 10)
                ));
                
                if(table.getValueAt(row, 4).toString().equals("Out of Stock")) {
                    c.setBackground(new Color(254, 242, 242));
                } else {
                    c.setBackground(isSelected ? new Color(241, 245, 249) : Color.WHITE);
                }

                if (column == 1) c.setHorizontalAlignment(SwingConstants.LEFT);
                
                if (column == 4) {
                    String stat = value.toString();
                    if (stat.equals("In Stock")) c.setText("<html><div style='background-color:#dcfce7; color:#166534; padding:4px 12px; border-radius:6px; font-weight:bold; font-size:10px;'>In Stock</div></html>");
                    else if (stat.equals("Low Stock")) c.setText("<html><div style='background-color:#ffedd5; color:#c2410c; padding:4px 12px; border-radius:6px; font-weight:bold; font-size:10px;'>Low Stock</div></html>");
                    else c.setText("<html><div style='background-color:#fee2e2; color:#b91c1c; padding:4px 12px; border-radius:6px; font-weight:bold; font-size:10px;'>Out of Stock</div></html>");
                }
                
                if (column == 6) {
                    c.setText("<html><span style='border:1px solid #cbd5e1; color:#1d4ed8; padding:3px 8px; border-radius:4px;'>\u270E</span> &nbsp; <span style='border:1px solid #cbd5e1; color:#b91c1c; padding:3px 8px; border-radius:4px;'>\u25F7</span></html>");
                    c.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel paginationPanel = new JPanel(new BorderLayout());
        paginationPanel.setBackground(Color.WHITE);
        paginationPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JLabel lblShowing = new JLabel("Showing 1 to 8 of 25 entries");
        lblShowing.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblShowing.setForeground(TEXT_GRAY);
        
        JPanel pageBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pageBtns.setBackground(Color.WHITE);
        pageBtns.add(createPageBtn("Previous", false));
        pageBtns.add(createPageBtn("1", true));
        pageBtns.add(createPageBtn("2", false));
        pageBtns.add(createPageBtn("3", false));
        pageBtns.add(createPageBtn("Next", false));

        paginationPanel.add(lblShowing, BorderLayout.WEST);
        paginationPanel.add(pageBtns, BorderLayout.EAST);
        tableContainer.add(paginationPanel, BorderLayout.SOUTH);

        return tableContainer;
    }

    private JButton createPageBtn(final String text, boolean active) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(active ? PRIMARY_BTN : Color.WHITE);
        btn.setForeground(active ? Color.WHITE : TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(active ? PRIMARY_BTN : BORDER_COLOR, 1, true),
            new EmptyBorder(5, 12, 5, 12)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ==========================================
    // UTILITY METHODS & CUSTOM COMPONENTS
    // ==========================================
    
    private JButton createRoundedButton(String text, Color bg, Color fg, final Color borderCol) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(borderCol);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTextField createRoundedTextField(String text) {
        JTextField tf = new JTextField(text) {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
            }
        };
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setForeground(TEXT_DARK);
        tf.setBorder(new EmptyBorder(5, 12, 5, 12));
        tf.setOpaque(false);
        return tf;
    }

    private JComboBox<String> createRoundedCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setForeground(TEXT_DARK);
        cb.setBackground(Color.WHITE);
        cb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(2, 5, 2, 5)
        ));
        return cb;
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
        }
    }

    class RoundedPanel extends JPanel {
        private int radius;
        private Color border;

        public RoundedPanel(int radius, Color bg, Color border) {
            this.radius = radius;
            this.border = border;
            setBackground(bg); 
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            if (border != null) {
                g2.setColor(border);
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            }
            g2.dispose();
        }
    }

    class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(203, 213, 225);
            this.trackColor = APP_BG;
        }
        @Override
        protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override
        protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
        private JButton createZeroButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            return btn;
        }
    }
}