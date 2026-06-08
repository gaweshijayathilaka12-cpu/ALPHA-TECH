import javax.swing.*; import javax.swing.table.*; import java.awt.*;

public class EmailNotificationPanel extends JPanel {

    // --- Helper Methods (මේවායින් කෝඩ් එක ගොඩක් කෙටි වෙනවා) ---
    private JLabel lbl(String t, int st, int sz, Color c, int x, int y, int w, int h) {
        JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", st, sz)); if(c!=null) l.setForeground(c); l.setBounds(x, y, w, h); return l;
    }
    private JButton btn(String t, Color bg, Color fg, int x, int y, int w, int h) {
        JButton b = new JButton(t); b.setBounds(x,y,w,h); b.setBackground(bg); if(fg!=null) b.setForeground(fg); b.setFocusPainted(false); b.setOpaque(true); b.setBorderPainted(false); return b;
    }
    private RoundedPanel rp(int x, int y, int w, int h) {
        RoundedPanel p = new RoundedPanel(15); p.setBounds(x,y,w,h); p.setBackground(Color.WHITE); p.setLayout(null); return p;
    }

    public EmailNotificationPanel() {
        setBounds(0, 0, 1020, 820); setBackground(new Color(245, 247, 245)); setLayout(null);
        
        add(lbl("Email Notifications", 1, 26, new Color(30,40,30), 20, 15, 300, 35));
        add(lbl("Create, manage and send email notifications.", 0, 13, new Color(100,115,100), 20, 45, 400, 20));
        add(btn("Email Settings", Color.WHITE, null, 720, 20, 130, 35));
        add(btn("+ New Email", new Color(46,125,50), Color.WHITE, 860, 20, 130, 35));

        add(lbl("Email Templates", 1, 14, new Color(46,125,50), 30, 90, 150, 20));
        JPanel tL=new JPanel(); tL.setBackground(new Color(46,125,50)); tL.setBounds(30,115,120,3); add(tL);
        add(lbl("Sent History", 0, 14, Color.GRAY, 170, 90, 100, 20));
        JPanel fL=new JPanel(); fL.setBackground(new Color(230,230,230)); fL.setBounds(20,117,970,1); add(fL);

        // --- LEFT PANEL ---
        RoundedPanel pL = rp(20, 130, 480, 400); add(pL);
        pL.add(lbl("Email Templates", 1, 14, null, 15, 15, 150, 20)); pL.add(lbl("Manage your email templates.", 0, 11, Color.GRAY, 15, 35, 200, 15));
        JTextField sT = new JTextField("  Search templates..."); sT.setBounds(15,60,250,30); sT.setBorder(BorderFactory.createLineBorder(new Color(220,220,220))); pL.add(sT);
        JComboBox<String> cT = new JComboBox<>(new String[]{"All Triggers"}); cT.setBounds(280,60,185,30); cT.setBackground(Color.WHITE); pL.add(cT);
        
        Object[][] d1 = {{"Order Confirmation","Order Placed","Active",""},{"Order Shipped","Order Shipped","Active",""},{"Order Delivered","Order Delivered","Active",""},{"Low Stock Alert","Low Stock","Active",""},{"New Client Welcome","New Client","Active",""},{"Payment Confirmation","Payment Received","Active",""}};
        JTable t1 = new JTable(new DefaultTableModel(d1, new String[]{"Template Name","Trigger","Status","Actions"})); t1.setRowHeight(35); t1.setShowGrid(false); t1.getTableHeader().setFont(new Font("Segoe UI", 1, 11)); t1.getTableHeader().setBackground(new Color(245,247,245)); t1.setFont(new Font("Segoe UI", 0, 11));
        t1.getColumnModel().getColumn(2).setCellRenderer(new SRend(true)); t1.getColumnModel().getColumn(3).setCellRenderer(new ARend(true));
        JScrollPane sc1 = new JScrollPane(t1); sc1.setBounds(15,105,450,240); sc1.setBorder(null); pL.add(sc1);
        pL.add(lbl("Showing 1 to 6 of 6 templates", 0, 11, Color.GRAY, 15, 365, 200, 20));
        JPanel pg1=new JPanel(new FlowLayout(2,5,0)); pg1.setOpaque(false); pg1.setBounds(370,360,100,30); pg1.add(btn("<",Color.WHITE,null,0,0,0,0)); pg1.add(btn("1",new Color(46,125,50),Color.WHITE,0,0,0,0)); pg1.add(btn(">",Color.WHITE,null,0,0,0,0)); pL.add(pg1);

        // --- RIGHT PANEL ---
        RoundedPanel pR = rp(510, 130, 480, 400); add(pR);
        pR.add(lbl("Create / Edit Email Template", 1, 14, new Color(46,125,50), 15, 15, 250, 20));
        pR.add(lbl("Template Name *", 1, 11, null, 15, 45, 150, 15)); JTextField tn = new JTextField(" Order Confirmation"); tn.setBounds(15,65,210,30); tn.setBorder(BorderFactory.createLineBorder(new Color(220,220,220))); pR.add(tn);
        pR.add(lbl("Trigger *", 1, 11, null, 240, 45, 100, 15)); JComboBox<String> tr = new JComboBox<>(new String[]{"Order Placed"}); tr.setBounds(240,65,130,30); tr.setBackground(Color.WHITE); pR.add(tr);
        pR.add(lbl("Status *", 1, 11, null, 385, 45, 80, 15)); JComboBox<String> st = new JComboBox<>(new String[]{"Active"}); st.setBounds(385,65,80,30); st.setBackground(Color.WHITE); pR.add(st);
        pR.add(lbl("Subject *", 1, 11, null, 15, 105, 150, 15)); JTextField sb = new JTextField(" Your order {{order_id}} is confirmed"); sb.setBounds(15,125,450,30); sb.setBorder(BorderFactory.createLineBorder(new Color(220,220,220))); pR.add(sb);
        pR.add(lbl("Email Content *", 1, 11, null, 15, 165, 150, 15));
        JPanel tb=new JPanel(new FlowLayout(0,10,5)); tb.setBounds(15,185,300,30); tb.setBackground(new Color(245,245,245)); tb.setBorder(BorderFactory.createLineBorder(new Color(220,220,220))); tb.add(new JLabel("B   I   U   ≡   [Img]   { }")); pR.add(tb);
        JTextArea bdy = new JTextArea(" Hi {{client_name}},\n\n Thank you for your order!\n\n Your order (ID: {{order_id}}) has been confirmed and will be processed soon.\n\n Order Date: {{order_date}}\n Total Amount: {{order_total}}\n\n We will notify you once your order is shipped.\n\n Thank you,\n The GreenLoop Team"); bdy.setFont(new Font("Segoe UI", 0, 11)); bdy.setBorder(BorderFactory.createLineBorder(new Color(220,220,220))); JScrollPane sbB = new JScrollPane(bdy); sbB.setBounds(15,214,300,130); pR.add(sbB);
        pR.add(lbl("Available Variables", 1, 11, new Color(46,125,50), 330, 185, 120, 15)); JTextArea vrs = new JTextArea("{{order_id}}       Order ID\n{{client_name}}  Client Name\n{{product_name}} Product Name\n{{quantity}}        Quantity\n{{order_date}}     Order Date\n{{order_total}}    Order Total"); vrs.setFont(new Font("Segoe UI", 0, 9)); vrs.setForeground(Color.GRAY); vrs.setEditable(false); vrs.setBounds(330,205,140,130); pR.add(vrs);
        pR.add(btn("Save Template", new Color(46,125,50), Color.WHITE, 15, 355, 130, 30)); pR.add(btn("Cancel", new Color(230,230,230), Color.BLACK, 155, 355, 90, 30));

        // --- BOTTOM PANEL ---
        RoundedPanel pB = rp(20, 540, 970, 230); add(pB);
        pB.add(lbl("Sent History", 1, 14, null, 15, 15, 150, 20)); pB.add(lbl("View history of sent email notifications.", 0, 11, Color.GRAY, 15, 35, 250, 15));
        pB.add(lbl("Date: 01-05-2026 to 17-05-2026", 0, 12, null, 590, 15, 180, 30)); JComboBox<String> cs = new JComboBox<>(new String[]{"All Status"}); cs.setBounds(780,15,90,30); cs.setBackground(Color.WHITE); pB.add(cs); JTextField sh = new JTextField(" Search..."); sh.setBounds(880,15,75,30); sh.setBorder(BorderFactory.createLineBorder(new Color(220,220,220))); pB.add(sh);
        Object[][] d2 = {{"EML0006","Order Confirmation","john.doe@email.com","Order Placed","17-05-2026 10:15 AM","Sent",""},{"EML0005","Order Shipped","sarah.khan@email.com","Order Shipped","17-05-2026 09:45 AM","Sent",""},{"EML0004","Low Stock Alert","store.manager@email.com","Low Stock","17-05-2026 08:30 AM","Sent",""},{"EML0003","New Client Welcome","new.client@email.com","New Client","16-05-2026 04:20 PM","Sent",""},{"EML0002","Order Delivered","mike.fernando@email.com","Order Delivered","16-05-2026 02:10 PM","Failed",""}};
        JTable t2 = new JTable(new DefaultTableModel(d2, new String[]{"Email ID","Template","To","Trigger","Sent Date","Status","Actions"})); t2.setRowHeight(35); t2.setShowGrid(false); t2.getTableHeader().setFont(new Font("Segoe UI", 1, 11)); t2.getTableHeader().setBackground(new Color(245,247,245)); t2.setFont(new Font("Segoe UI", 0, 11));
        t2.getColumnModel().getColumn(5).setCellRenderer(new SRend(false)); t2.getColumnModel().getColumn(6).setCellRenderer(new ARend(false));
        JScrollPane sc2 = new JScrollPane(t2); sc2.setBounds(15,60,940,130); sc2.setBorder(null); pB.add(sc2);
        pB.add(lbl("Showing 1 to 5 of 6 records", 0, 11, Color.GRAY, 15, 195, 200, 20));
        JPanel pg2=new JPanel(new FlowLayout(2,5,0)); pg2.setOpaque(false); pg2.setBounds(780,195,170,30); pg2.add(btn("Previous",Color.WHITE,null,0,0,0,0)); pg2.add(btn("1",new Color(46,125,50),Color.WHITE,0,0,0,0)); pg2.add(btn("Next",Color.WHITE,null,0,0,0,0)); pB.add(pg2);
    }

    // --- Condensed Renderers & Custom UI Classes ---
    class SRend extends DefaultTableCellRenderer {
        boolean t; public SRend(boolean t){this.t=t;}
        @Override public Component getTableCellRendererComponent(JTable j, Object v, boolean s, boolean f, int r, int c) {
            JLabel l = (JLabel)super.getTableCellRendererComponent(j,v,s,f,r,c); l.setHorizontalAlignment(CENTER);
            if(t){ l.setOpaque(true); l.setBackground(new Color(230,245,232)); l.setForeground(new Color(46,125,50)); }
            else{ l.setForeground("Sent".equals(v)?new Color(46,125,50):Color.RED); } return l;
        }
    }
    class ARend extends DefaultTableCellRenderer {
        boolean e; public ARend(boolean e){this.e=e;}
        @Override public Component getTableCellRendererComponent(JTable j, Object v, boolean s, boolean f, int r, int c) {
            JPanel p = new JPanel(new FlowLayout(1,5,0)); p.setBackground(Color.WHITE);
            if(e){ p.add(new DI(new Color(66,133,244),1)); p.add(new DI(new Color(211,47,47),2)); } else p.add(new DI(new Color(66,133,244),3)); return p;
        }
    }
    class DI extends JPanel {
        Color c; int t; public DI(Color c, int t){this.c=c; this.t=t; setPreferredSize(new Dimension(24,24)); setBackground(Color.WHITE);}
        @Override protected void paintComponent(Graphics g) { super.paintComponent(g); Graphics2D g2=(Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); g2.setColor(c); g2.drawRoundRect(0,0,23,23,6,6); g2.setStroke(new BasicStroke(1.5f));
        if(t==1){g2.drawLine(6,16,14,8); g2.drawLine(6,16,8,18);} else if(t==2){g2.drawRect(8,8,8,10); g2.drawLine(6,8,18,8);} else{g2.drawOval(6,8,12,8); g2.drawOval(10,10,4,4);} g2.dispose(); }
    }
    class RoundedPanel extends JPanel {
        int r; public RoundedPanel(int r){this.r=r; setOpaque(false);}
        @Override protected void paintComponent(Graphics g) { Graphics2D g2=(Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); g2.setColor(getBackground()); g2.fillRoundRect(0,0,getWidth(),getHeight(),r,r); g2.setColor(new Color(230,235,230)); g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,r,r); g2.dispose(); }
    }
}