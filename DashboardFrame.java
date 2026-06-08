import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private ArrayList<SidebarButton> sidebarButtons = new ArrayList<>();

    public DashboardFrame() {
        setTitle("GreenLoop - Dashboard");
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // 1. LEFT SIDEBAR PANEL
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.setBounds(0, 0, 260, 820);
        add(sidebar);

        // 2. RIGHT MAIN CONTENT AREA (CardLayout used for robust panel switching)
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBounds(260, 0, 1020, 820);
        add(mainContentPanel);

        // --- DASHBOARD VIEW ---
        JPanel dashboardView = createDashboardView(sidebar);
        mainContentPanel.add(dashboardView, "Dashboard");
        // ---PRODUCT MANAGEMENR VIEW ---
        ProductManagementPanel productView = new ProductManagementPanel();
        mainContentPanel.add(productView, "Products");
        
        // --- CLIENT MANAGEMENT VIEW ---
        ClientManagementPanel clientView = new ClientManagementPanel();
        mainContentPanel.add(clientView, "Clients");
        InventoryManagementPanel inventoryView =
        new InventoryManagementPanel();
mainContentPanel.add(inventoryView, "Inventory");

OrderProcessingPanel orderView =
        new OrderProcessingPanel();
mainContentPanel.add(orderView, "Orders");

DeliveryAgentPanel deliveryView =
        new DeliveryAgentPanel();
mainContentPanel.add(deliveryView, "Delivery Agents");

// --- DELIVERY ASSIGNMENT VIEW ---
DeliveryAssignmentPanel deliveryAssignView = new DeliveryAssignmentPanel();
mainContentPanel.add(deliveryAssignView, "Delivery Assignment"); // මෙතැන නම හරියටම දෙන්න
ReportsPanel reportsView =
        new ReportsPanel();
mainContentPanel.add(reportsView, "Reports");

        
        // --- EMAIL NOTIFICATIONS VIEW ---
        EmailNotificationPanel emailView = new EmailNotificationPanel();
        mainContentPanel.add(emailView, "Email Notifications");

        setVisible(true);
    }

    // Fixed Switch Panel logic with revalidate & repaint to ensure smooth transitions
    public void switchPanel(String panelName) {
        cardLayout.show(mainContentPanel, panelName);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
        for (SidebarButton btn : sidebarButtons) {
            btn.setActive(btn.getMenuText().equals(panelName));
            btn.repaint();
        }
    }

    private JPanel createDashboardView(SidebarPanel sidebar) {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 247, 245));

        JLabel lblHeader = new JLabel("Dashboard");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblHeader.setForeground(new Color(30, 40, 30));
        lblHeader.setBounds(30, 20, 200, 35);
        panel.add(lblHeader);

        JLabel lblWelcome = new JLabel("Welcome, Admin!");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblWelcome.setForeground(new Color(100, 115, 100));
        lblWelcome.setBounds(30, 52, 200, 20);
        panel.add(lblWelcome);

        RoundedPanel timeCard = new RoundedPanel(12);
        timeCard.setBounds(740, 20, 250, 55);
        timeCard.setBackground(Color.WHITE);
        timeCard.setLayout(null);
        JLabel lblCalendar = new JLabel("\uD83D\uDCC5");
lblCalendar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
lblCalendar.setBounds(15, 10, 30, 30);
timeCard.add(lblCalendar);
        String curDay = new SimpleDateFormat("EEEE, dd MMM yyyy").format(new Date());
        JLabel lblDayStr = new JLabel(curDay);
        lblDayStr.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDayStr.setForeground(new Color(120, 130, 120));
        lblDayStr.setBounds(60, 10, 180, 16);
        timeCard.add(lblDayStr);

        final JLabel lblTimeStr = new JLabel(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
        lblTimeStr.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTimeStr.setForeground(new Color(46, 125, 50));
        lblTimeStr.setBounds(60, 28, 180, 18);
        timeCard.add(lblTimeStr);
        panel.add(timeCard);

        panel.add(new StatCard("Total Products", "125", "View all products", new Color(230, 245, 232), new Color(46, 125, 50), "Products", 30, 90));
        panel.add(new StatCard("Total Clients", "58", "View all clients", new Color(225, 240, 255), new Color(21, 101, 192), "Clients", 350, 90));
        panel.add(new StatCard("Total Orders", "84", "View all orders", new Color(255, 243, 224), new Color(230, 124, 11), "Orders", 670, 90));
        
        panel.add(new StatCard("Low Stock Items", "8", "View details", new Color(255, 235, 235), new Color(198, 40, 40), "Inventory", 30, 195));
        panel.add(new StatCard("Pending Deliveries", "12", "View deliveries", new Color(243, 229, 245), new Color(106, 27, 154), "Delivery", 350, 195));
        panel.add(new StatCard("Delivered Orders", "62", "View delivered", new Color(224, 242, 241), new Color(0, 105, 92), "Reports", 670, 195));

        RoundedPanel pnlRecentOrders = new RoundedPanel(15);
        pnlRecentOrders.setBounds(30, 310, 460, 240);
        pnlRecentOrders.setBackground(Color.WHITE);
        pnlRecentOrders.setLayout(null);
        createRecentOrdersTable(pnlRecentOrders);
        panel.add(pnlRecentOrders);

        RoundedPanel pnlLowStock = new RoundedPanel(15);
        pnlLowStock.setBounds(510, 310, 460, 240);
        pnlLowStock.setBackground(Color.WHITE);
        pnlLowStock.setLayout(null);
        createLowStockTable(pnlLowStock);
        panel.add(pnlLowStock);

        SalesChartPanel pnlChart = new SalesChartPanel();
        pnlChart.setBounds(30, 565, 460, 205);
        panel.add(pnlChart);

        RoundedPanel pnlSummary = new RoundedPanel(15);
        pnlSummary.setBounds(510, 565, 460, 205);
        pnlSummary.setBackground(Color.WHITE);
        pnlSummary.setLayout(null);
        createSalesSummary(pnlSummary);
        panel.add(pnlSummary);

        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTimeStr.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));
                sidebar.updateLiveTime();
            }
        }).start();

        return panel;
    }

    private void createRecentOrdersTable(JPanel panel) {
        JLabel title = new JLabel("Recent Orders");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setBounds(15, 12, 150, 20);
        panel.add(title);

        String[] cols = {"Order ID", "Client", "Order Date", "Status"};
        Object[][] data = {
            {"ORD00124", "Eco Mart", "17-05-2026", "Pending"},
            {"ORD00123", "Green Store", "16-05-2026", "Assigned"},
            {"ORD00122", "Nature Lanka", "16-05-2026", "Delivered"},
            {"ORD00121", "Organic Shop", "15-05-2026", "Pending"},
            {"ORD00120", "Daily Needs", "15-05-2026", "Assigned"}
        };

        JTable table = new JTable(new DefaultTableModel(data, cols));
        styleTable(table);
        
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object val, boolean isS, boolean hasF, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, val, isS, hasF, r, c);
                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setFont(new Font("Segoe UI", Font.BOLD, 11));
                l.setOpaque(true);
                if ("Pending".equals(val)) { 
                    l.setBackground(new Color(255, 243, 224)); l.setForeground(new Color(230, 124, 11)); 
                } else if ("Assigned".equals(val)) { 
                    l.setBackground(new Color(227, 242, 253)); l.setForeground(new Color(21, 101, 192)); 
                } else { 
                    l.setBackground(new Color(230, 245, 232)); l.setForeground(new Color(46, 125, 50)); 
                }
                return l;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(15, 45, 430, 180);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll);
    }

    private void createLowStockTable(JPanel panel) {
        JLabel title = new JLabel("Low Stock Alert");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setBounds(15, 12, 150, 20);
        panel.add(title);

        String[] cols = {"Product", "Stock", "Reorder"};
        Object[][] data = {
            {"Biodegradable Bag (S)", "150", "200"},
            {"Recycled Paper Box", "60", "80"},
            {"Compostable Food Box", "40", "50"},
            {"Paper Wrap Roll", "100", "120"},
            {"Biodegradable Bag (L)", "80", "100"}
        };

        JTable table = new JTable(new DefaultTableModel(data, cols));
        styleTable(table);

        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object val, boolean isS, boolean hasF, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, val, isS, hasF, r, c);
                l.setForeground(new Color(198, 40, 40));
                l.setFont(new Font("Segoe UI", Font.BOLD, 12));
                return l;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(15, 45, 430, 180);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(240, 245, 240));
        table.getTableHeader().setForeground(new Color(50, 70, 50));
        table.getTableHeader().setPreferredSize(new Dimension(100, 28));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }

    private void createSalesSummary(JPanel panel) {
        JLabel title = new JLabel("Sales Summary (May 2026)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setBounds(15, 12, 300, 20);
        panel.add(title);

        String[][] metrics = {
            {"Total Revenue", "Rs. 250,000.00"},
            {"Total Orders", "84"},
            {"Average Order Value", "Rs. 2,976.19"},
            {"Best Selling Product", "Biodegradable Bag (S)"}
        };

        int y = 45;
        for (int i = 0; i < metrics.length; i++) {
            JLabel lblLabel = new JLabel(metrics[i][0]);
            lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblLabel.setBounds(20, y, 180, 25);
            panel.add(lblLabel);

            JLabel lblVal = new JLabel(metrics[i][1], SwingConstants.RIGHT);
            lblVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblVal.setForeground(metrics[i][0].contains("Revenue") ? new Color(46, 125, 50) : Color.BLACK);
            lblVal.setBounds(240, y, 200, 25);
            panel.add(lblVal);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(240, 240, 240));
            sep.setBounds(15, y + 28, 430, 2);
            panel.add(sep);
            y += 36;
        }
    }

    class StatCard extends JPanel {
        private Color bgIcon;
        public StatCard(String title, String value, String footer, Color bgIcon, Color fgIcon, String type, int x, int y) {
            this.bgIcon = bgIcon;
            setBounds(x, y, 300, 92);
            setOpaque(false);
            setLayout(null);

            JPanel iconCircle = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(StatCard.this.bgIcon); 
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.setColor(fgIcon);
                    g2.setStroke(new BasicStroke(2.5f));
                    g2.drawOval(15, 15, 14, 14);
                    g2.dispose();
                }
            };
            iconCircle.setBounds(15, 15, 45, 45);
            iconCircle.setOpaque(false);
            add(iconCircle);

            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblTitle.setForeground(new Color(130, 140, 130));
            lblTitle.setBounds(75, 12, 150, 18);
            add(lblTitle);

            JLabel lblVal = new JLabel(value);
            lblVal.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblVal.setBounds(75, 30, 150, 32);
            add(lblVal);

            JLabel lblFoot = new JLabel(footer + "  ›");
            lblFoot.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lblFoot.setForeground(fgIcon);
            lblFoot.setBounds(75, 64, 180, 15);
            add(lblFoot);
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.setColor(new Color(230, 235, 230)); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.dispose();
        }
    }

    class SalesChartPanel extends JPanel {
        public SalesChartPanel() { setOpaque(false); setLayout(null); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(new Color(230, 235, 230)); g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

            g2.setColor(Color.BLACK); g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g2.drawString("Monthly Sales Overview", 15, 25);

            int[] xPoints = {50, 100, 150, 200, 250, 300, 350, 420};
            int[] yPoints = {160, 120, 135, 80, 115, 90, 130, 155};

            Polygon area = new Polygon();
            area.addPoint(50, 170);
            for(int i=0; i<xPoints.length; i++) area.addPoint(xPoints[i], yPoints[i]);
            area.addPoint(420, 170);
            g2.setPaint(new GradientPaint(0, 50, new Color(46, 125, 50, 80), 0, 170, new Color(255, 255, 255, 0)));
            g2.fill(area);

            g2.setColor(new Color(240, 243, 240));
            g2.drawLine(50, 170, 420, 170); g2.drawLine(50, 120, 420, 120); g2.drawLine(50, 70, 420, 70);

            g2.setColor(new Color(46, 125, 50));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawPolyline(xPoints, yPoints, xPoints.length);

            g2.setColor(new Color(27, 94, 32));
            for(int i=0; i<xPoints.length; i++) g2.fillOval(xPoints[i]-3, yPoints[i]-3, 6, 6);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10)); g2.setColor(Color.GRAY);
            g2.drawString("1", 50, 185); g2.drawString("10", 150, 185); g2.drawString("20", 250, 185); g2.drawString("30", 350, 185);
            g2.dispose();
        }
    }

    class SidebarPanel extends JPanel {
        private JLabel lblSideTime;
        public SidebarPanel() {
            setLayout(null);
            setBackground(new Color(11, 65, 24));

            JLabel lblBrand = new JLabel("GreenLoop");
            lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblBrand.setForeground(Color.WHITE);
            lblBrand.setBounds(30, 25, 200, 30); add(lblBrand);

            JLabel lblSub = new JLabel("PACKAGING MANAGEMENT SYSTEM");
            lblSub.setFont(new Font("Segoe UI", Font.BOLD, 8)); lblSub.setForeground(new Color(170, 205, 170));
            lblSub.setBounds(30, 55, 200, 15); add(lblSub);

String[] menuItems = {"Dashboard", "Products", "Clients", "Inventory", "Orders", "Delivery Agents", "Delivery Assignment", "Reports", "Email Notifications"};            int startY = 110;
            for (int i = 0; i < menuItems.length; i++) {
                SidebarButton btn = new SidebarButton(menuItems[i], menuItems[i].equals("Dashboard"));
                btn.setBounds(15, startY, 230, 42);
                btn.addActionListener(e -> switchPanel(btn.getMenuText()));
                sidebarButtons.add(btn);
                add(btn); 
                startY += 48;
            }
            
            JSeparator sep = new JSeparator(); sep.setForeground(new Color(30, 95, 45));
            sep.setBounds(15, startY + 10, 230, 2); add(sep);

           SidebarButton btnLogout = new SidebarButton("Logout", false);
btnLogout.setBounds(15, startY + 22, 230, 42);

// මෙය අලුතින් එක් කරන්න:
btnLogout.addActionListener(e -> {
    int response = JOptionPane.showConfirmDialog(DashboardFrame.this, 
            "Do you want to logout?", 
            "Logout", 
            JOptionPane.YES_NO_OPTION);
    
    if (response == JOptionPane.YES_OPTION) {
        dispose(); // Dashboard එක වසා දමයි
        new LoginFrame().setVisible(true); // ඔබේ ලොගින් පිටුව (LoginPage පන්තිය) විවෘත කරයි
    }
});

add(btnLogout);

            JPanel footerPanel = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fillOval(15, 15, 25, 25);
                    g2.dispose();
                }
            };
            footerPanel.setOpaque(false);
            footerPanel.setLayout(null);
            footerPanel.setBounds(0, 710, 260, 80);
            
            JLabel lblLogged = new JLabel("Logged in as");
            lblLogged.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lblLogged.setForeground(new Color(180, 215, 180));
            lblLogged.setBounds(55, 12, 180, 15);
            footerPanel.add(lblLogged);

            JLabel lblAdmin = new JLabel("Admin");
            lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblAdmin.setForeground(Color.WHITE);
            lblAdmin.setBounds(55, 28, 180, 20);
            footerPanel.add(lblAdmin);

            lblSideTime = new JLabel(new SimpleDateFormat("dd MMM yyyy | hh:mm a").format(new Date()));
            lblSideTime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            lblSideTime.setForeground(new Color(150, 190, 150));
            lblSideTime.setBounds(55, 48, 200, 15);
            footerPanel.add(lblSideTime);

            add(footerPanel);
        }

        public void updateLiveTime() {
            if (lblSideTime != null) {
                lblSideTime.setText(new SimpleDateFormat("dd MMM yyyy | hh:mm a").format(new Date()));
            }
        }
    }

    class SidebarButton extends JButton {
        private boolean isActive; 
        private String menuText;
        
        public SidebarButton(String text, boolean isActive) {
            this.isActive = isActive; this.menuText = text;
            setText("              " + text); setHorizontalAlignment(SwingConstants.LEFT);
            setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 14));
            setOpaque(false); setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR)); setForeground(isActive ? Color.WHITE : new Color(200, 225, 200));
        }

        public String getMenuText() { return menuText; }
        public void setActive(boolean active) { 
            this.isActive = active; 
            setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 14));
            setForeground(isActive ? Color.WHITE : new Color(200, 225, 200));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (isActive) { g2.setColor(new Color(46, 125, 50)); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); }
            else if (getModel().isRollover()) { g2.setColor(new Color(20, 85, 35)); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); }
            
            g2.setColor(Color.WHITE); g2.setStroke(new BasicStroke(1.8f));
            int ix = 18; int iy = 12;
            
            if ("Dashboard".equals(menuText)) {
                g2.drawRect(ix + 2, iy + 6, 14, 11); int[] xH = {ix, ix + 9, ix + 18}; int[] yH = {iy + 6, iy, iy + 6}; g2.drawPolyline(xH, yH, 3);
            } else if ("Products".equals(menuText)) {
                g2.drawRect(ix, iy + 4, 18, 12); g2.drawLine(ix, iy + 4, ix + 4, iy); g2.drawLine(ix + 18, iy + 4, ix + 14, iy); g2.drawLine(ix + 4, iy, ix + 14, iy);
            } else if ("Clients".equals(menuText)) {
                g2.drawOval(ix + 4, iy, 8, 8); g2.drawArc(ix, iy + 9, 16, 10, 0, 180);
            } else if ("Inventory".equals(menuText)) {
                g2.drawRect(ix + 2, iy, 14, 16); g2.drawLine(ix + 5, iy + 5, ix + 13, iy + 5); g2.drawLine(ix + 5, iy + 9, ix + 13, iy + 9);
            } else if ("Orders".equals(menuText)) {
                g2.drawRoundRect(ix + 1, iy, 15, 17, 4, 4); g2.fillRect(ix + 5, iy + 4, 7, 2); g2.fillRect(ix + 5, iy + 8, 7, 2);
            } else if ("Delivery Agents".equals(menuText)) {
                g2.drawRect(ix, iy + 3, 11, 10); g2.drawRect(ix + 11, iy + 6, 6, 7); g2.drawOval(ix + 2, iy + 12, 4, 4); g2.drawOval(ix + 11, iy + 12, 4, 4);
            } else if ("Delivery Assignment".equals(menuText)) {
    g2.drawRect(ix, iy + 2, 14, 12);       // පිටත කොටුව
    g2.drawLine(ix + 4, iy + 5, ix + 10, iy + 5); // ඇතුළත ඉරි
    g2.drawLine(ix + 4, iy + 9, ix + 8, iy + 9);
    g2.fillOval(ix + 12, iy + 10, 5, 5);  // කුඩා තිතක් (අයිකනයක් ලෙස)
            
            } else if ("Reports".equals(menuText)) {
                g2.drawLine(ix, iy + 15, ix + 17, iy + 15); g2.fillRect(ix + 2, iy + 8, 3, 7); g2.fillRect(ix + 7, iy + 3, 3, 12);
            } else if ("Email Notifications".equals(menuText)) {
                g2.drawRect(ix, iy + 2, 18, 12); g2.drawLine(ix, iy + 2, ix + 9, iy + 8); g2.drawLine(ix + 18, iy + 2, ix + 9, iy + 8);
            } else if ("Settings".equals(menuText)) {
                g2.drawOval(ix + 4, iy + 4, 10, 10); g2.drawLine(ix + 9, iy + 2, ix + 9, iy + 4); g2.drawLine(ix + 9, iy + 14, ix + 9, iy + 16);
            } else if ("Logout".equals(menuText)) {
                g2.drawArc(ix, iy, 14, 16, 90, 180); g2.drawLine(ix + 6, iy + 8, ix + 18, iy + 8);
            }
            g2.dispose(); super.paintComponent(g);
        }
    }

 class RoundedPanel extends JPanel {
    private int r;

    public RoundedPanel(int r) {
        this.r = r;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
        g2.dispose();
    }
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new DashboardFrame();
        }
    });
}
}


