import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class ReportsPanel extends JPanel {

    // --- Helper Methods (මේවායින් කෝඩ් එක ගොඩක් කෙටි වෙනවා) ---
    private ImageIcon getIcon(String p, int w, int h) {
        try {
            return new ImageIcon(new ImageIcon(p).getImage().getScaledInstance(w, h, 4));
        } catch (Exception e) {
            return null;
        }
    }

    private JLabel newLbl(String t, Font f, Color c, int x, int y, int w, int h) {
        JLabel l = new JLabel(t);
        l.setFont(f);
        if (c != null)
            l.setForeground(c);
        l.setBounds(x, y, w, h);
        return l;
    }

    private RoundedPanel newRP(int x, int y, int w, int h, LayoutManager l) {
        RoundedPanel p = new RoundedPanel(15);
        p.setBounds(x, y, w, h);
        p.setBackground(Color.WHITE);
        p.setLayout(l);
        return p;
    }

    private void makeTable(JPanel p, String title, String[] c, Object[][] d, int w) {
        p.add(newLbl(title, new Font("Segoe UI", 1, 13), null, 10, 10, 200, 20));
        JTable t = new JTable(new DefaultTableModel(d, c));
        t.setRowHeight(22);
        t.setShowGrid(false);
        t.setFont(new Font("Segoe UI", 0, 11));
        t.getTableHeader().setFont(new Font("Segoe UI", 1, 11));
        t.getTableHeader().setBackground(new Color(245, 247, 245));
        JScrollPane s = new JScrollPane(t);
        s.setBounds(10, 35, w, 125);
        s.setBorder(null);
        p.add(s);
    }

    public ReportsPanel() {
        setBounds(0, 0, 1020, 820);
        setBackground(new Color(245, 247, 245));
        setLayout(null);
        add(newLbl("Reports", new Font("Segoe UI", 1, 26), new Color(30, 40, 30), 20, 15, 200, 35));
        add(newLbl("View and analyze business performance and operations.", new Font("Segoe UI", 0, 13),
                new Color(100, 115, 100), 20, 45, 400, 20));

        RoundedPanel pD = newRP(650, 20, 200, 35, new FlowLayout(1, 10, 8));
        pD.add(newLbl("📅  01-05-2026   to   17-05-2026   v", new Font("Segoe UI", 0, 11), null, 0, 0, 0, 0));
        add(pD);
        JButton bE = new JButton("📥 Export Report");
        bE.setFont(new Font("Segoe UI", 1, 12));
        bE.setBackground(new Color(46, 125, 50));
        bE.setForeground(Color.WHITE);
        bE.setBounds(860, 20, 130, 35);
        add(bE);

        add(new StatCardReport("Total Orders", "152", "↑ 12.5% vs last period", new Color(46, 125, 50),
                "images/orders.png", 20, 80, 184));
        add(new StatCardReport("Total Revenue", "Rs. 189,450.00", "↑ 15.8% vs last period", new Color(21, 101, 192),
                "images/revenue.png", 212, 80, 184));
        add(new StatCardReport("Total Deliveries", "138", "↑ 10.2% vs last period", new Color(230, 124, 11),
                "images/delivery.png", 404, 80, 184));
        add(new StatCardReport("Active Clients", "58", "↑ 8.9% vs last period", new Color(106, 27, 154),
                "images/clients.png", 596, 80, 184));
        add(new StatCardReport("Low Stock Items", "8", "↓ 11.1% vs last period", new Color(0, 150, 136),
                "images/stock.png", 788, 80, 184));

        RoundedPanel pF = newRP(20, 180, 460, 220, null);
        pF.add(newLbl("Report Filters", new Font("Segoe UI", 1, 14), null, 15, 15, 150, 20));
        String[] lbs = { "Report Type", "Date Range", "Client", "Delivery Agent", "Payment Method" };
        for (int i = 0, y = 45; i < 5; i++, y += 32) {
            pF.add(newLbl(lbs[i], new Font("Segoe UI", 0, 12), null, 15, y, 100, 25));
            if (i == 1) {
                JTextField d1 = new JTextField("01-05-2026");
                d1.setBounds(130, y, 130, 25);
                pF.add(d1);
                pF.add(newLbl("to", new Font("Segoe UI", 0, 12), null, 270, y, 20, 25));
                JTextField d2 = new JTextField("17-05-2026");
                d2.setBounds(295, y, 130, 25);
                pF.add(d2);
            } else {
                JComboBox<String> c = new JComboBox<>(
                        new String[] { i == 0 ? "Order Summary" : "All " + lbs[i].split(" ")[0] + "s" });
                c.setBounds(130, y, 295, 25);
                c.setBackground(Color.WHITE);
                pF.add(c);
            }
        }
        JButton bG = new JButton("Generate Report");
        bG.setBackground(new Color(46, 125, 50));
        bG.setForeground(Color.WHITE);
        bG.setBounds(130, 205, 150, 28);
        pF.add(bG);
        add(pF);

        RoundedPanel pQ = newRP(500, 180, 490, 220, null);
        pQ.add(newLbl("Quick Reports", new Font("Segoe UI", 1, 14), null, 15, 15, 150, 20));
        String[] rpt = { "Sales Report", "Delivery Report", "Order Summary", "Client Report", "Inventory Report",
                "Agent Performance Report" };
        for (int i = 0, x = 15, y = 45; i < 6; i++) {
            JButton b = new JButton("   " + rpt[i] + "                  >");
            b.setHorizontalAlignment(2);
            b.setBackground(Color.WHITE);
            b.setFont(new Font("Segoe UI", 0, 12));
            b.setBounds(x, y, 220, 40);
            pQ.add(b);
            if (i % 2 == 0)
                x += 235;
            else {
                x = 15;
                y += 50;
            }
        }
        add(pQ);

        RoundedPanel pL = newRP(20, 415, 460, 200, new BorderLayout());
        pL.add(new LineChartPanel(), BorderLayout.CENTER);
        add(pL);
        RoundedPanel pB = newRP(500, 415, 490, 200, new BorderLayout());
        pB.add(new BarChartPanel(), BorderLayout.CENTER);
        add(pB);

        RoundedPanel pT1 = newRP(20, 630, 360, 170, null);
        add(pT1);
        makeTable(pT1, "Top Clients by Revenue", new String[] { "#", "Client Name", "Orders", "Rev (Rs.)", "% Total" },
                new Object[][] { { "1", "Eco Mart", "24", "48,650", "25%" },
                        { "2", "Green Store", "18", "34,720", "18%" }, { "3", "Nature Lanka", "16", "27,850", "14%" },
                        { "4", "Organic Shop", "14", "24,300", "12%" }, { "5", "Daily Needs", "12", "18,900", "10%" } },
                340);

        RoundedPanel pPie = newRP(395, 630, 260, 170, new BorderLayout());
        pPie.add(new DoughnutChartPanel(), BorderLayout.CENTER);
        add(pPie);

        RoundedPanel pT2 = newRP(670, 630, 320, 170, null);
        add(pT2);
        makeTable(pT2, "Payment Method Summary", new String[] { "Method", "Orders", "Rev (Rs.)", "% Total" },
                new Object[][] { { "COD", "120", "129,250", "68%" }, { "Bank Transfer", "22", "38,750", "20%" },
                        { "Online", "10", "16,450", "8%" }, { "Credit", "0", "5,000", "2%" } },
                300);
    }

    class StatCardReport extends JPanel {
        public StatCardReport(String t, String v, String tr, Color c, String ic, int x, int y, int w) {
            setBounds(x, y, w, 80);
            setOpaque(false);
            setLayout(null);
            JPanel p = new JPanel(null);
            p.setBackground(c);
            p.setBounds(15, 20, 40, 40);
            JLabel l = new JLabel();
            if (ic != null)
                l.setIcon(getIcon(ic, 24, 24));
            l.setBounds(8, 8, 24, 24);
            p.add(l);
            add(p);
            add(newLbl(t, new Font("Segoe UI", 0, 12), null, 65, 15, 120, 15));
            add(newLbl(v, new Font("Segoe UI", 1, 18), null, 65, 30, 120, 25));
            JLabel trL = newLbl(tr, new Font("Segoe UI", 0, 10), tr.contains("↑") ? new Color(46, 125, 50) : Color.RED,
                    65, 55, 120, 15);
            add(trL);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(new Color(230, 235, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            g2.dispose();
        }
    }

    class LineChartPanel extends JPanel {
        public LineChartPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("Segoe UI", 1, 14));
            g2.drawString("Orders vs Deliveries", 15, 25);
            g2.setFont(new Font("Segoe UI", 0, 11));
            g2.setColor(new Color(46, 125, 50));
            g2.fillRect(150, 15, 10, 3);
            g2.setColor(Color.BLACK);
            g2.drawString("Orders", 165, 20);
            g2.setColor(new Color(21, 101, 192));
            g2.fillRect(220, 15, 10, 3);
            g2.setColor(Color.BLACK);
            g2.drawString("Deliveries", 235, 20);
            int[] x = { 40, 80, 120, 160, 200, 240, 280, 320, 360, 400 },
                    y1 = { 130, 90, 110, 60, 120, 100, 130, 80, 110, 140 },
                    y2 = { 150, 120, 140, 90, 140, 120, 150, 110, 140, 160 };
            for (int i = 0; i <= 4; i++) {
                g2.setColor(new Color(230, 230, 230));
                g2.drawLine(35, 50 + (i * 30), 430, 50 + (i * 30));
                g2.setColor(Color.GRAY);
                g2.drawString(20 - (i * 5) + "", 15, 54 + (i * 30));
            }
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(46, 125, 50));
            g2.drawPolyline(x, y1, 10);
            g2.setColor(new Color(21, 101, 192));
            g2.drawPolyline(x, y2, 10);
            for (int i = 0; i < 10; i++) {
                g2.setColor(new Color(46, 125, 50));
                g2.fillOval(x[i] - 3, y1[i] - 3, 6, 6);
                g2.setColor(new Color(21, 101, 192));
                g2.fillOval(x[i] - 3, y2[i] - 3, 6, 6);
            }
        }
    }

    class BarChartPanel extends JPanel {
        public BarChartPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(new Font("Segoe UI", 1, 14));
            g2.drawString("Revenue Overview", 15, 25);
            g2.setColor(new Color(46, 125, 50));
            g2.fillRect(380, 15, 10, 10);
            g2.setFont(new Font("Segoe UI", 0, 11));
            g2.setColor(Color.BLACK);
            g2.drawString("Revenue (Rs.)", 395, 24);
            int[] b = { 120, 60, 110, 80, 115, 90, 140, 90, 95, 120, 90, 120, 60 };
            g2.setColor(new Color(46, 125, 50));
            for (int i = 0, sx = 50; i < b.length; i++, sx += 30)
                g2.fillRect(sx, 170 - b[i], 14, b[i]);
        }
    }

    class DoughnutChartPanel extends JPanel {
        public DoughnutChartPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("Segoe UI", 1, 13));
            g2.drawString("Orders by Status", 10, 20);
            Rectangle r = new Rectangle(20, 40, 100, 100);
            g2.setColor(new Color(46, 125, 50));
            g2.fill(new Arc2D.Double(r, 45, 290, 2));
            g2.setColor(new Color(21, 101, 192));
            g2.fill(new Arc2D.Double(r, 0, 45, 2));
            g2.setColor(new Color(198, 40, 40));
            g2.fill(new Arc2D.Double(r, 335, 25, 2));
            g2.setColor(Color.WHITE);
            g2.fillOval(45, 65, 50, 50);
            g2.setFont(new Font("Segoe UI", 0, 10));
            int lx = 140;
            g2.setColor(new Color(46, 125, 50));
            g2.fillRect(lx, 60, 8, 8);
            g2.setColor(Color.BLACK);
            g2.drawString("Delivered (90.8%)", lx + 15, 68);
            g2.setColor(new Color(21, 101, 192));
            g2.fillRect(lx, 85, 8, 8);
            g2.setColor(Color.BLACK);
            g2.drawString("Pending (7.9%)", lx + 15, 93);
            g2.setColor(new Color(198, 40, 40));
            g2.fillRect(lx, 110, 8, 8);
            g2.setColor(Color.BLACK);
            g2.drawString("Cancelled (1.3%)", lx + 15, 118);
            g2.setFont(new Font("Segoe UI", 1, 11));
            g2.drawString("Total Orders: 152", 40, 160);
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
            g2.setColor(new Color(230, 235, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, r, r);
            g2.dispose();
        }
    }
}