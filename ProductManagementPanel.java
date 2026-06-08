import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductManagementPanel extends JPanel {

    private JTextField txtProductId, txtProductName, txtPrice, txtStock, txtReorder, txtSearch;
    private JTextArea txtDescription;
    private JComboBox<String> cmbEcoRating;
    private JTable productTable;
    private DefaultTableModel tableModel;

    private final Color GREEN = new Color(46, 125, 50);
    private final Color DARK_GREEN = new Color(18, 82, 35);
    private final Color BLUE = new Color(36, 101, 170);
    private final Color RED = new Color(205, 55, 45);
    private final Color BORDER = new Color(225, 230, 225);

    public ProductManagementPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        add(createHeaderPanel());
        add(createTopCards());
        add(createTablePanel());
        loadProductsFromMongoDB();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(null);
        header.setBackground(Color.WHITE);
        header.setBounds(20, 15, 980, 60);

        JLabel title = new JLabel("Product Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(30, 35, 35));
        title.setBounds(0, 0, 350, 30);
        header.add(title);

        JLabel sub = new JLabel("Add, update, delete and manage products.");
        sub.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sub.setForeground(Color.BLACK);
        sub.setBounds(0, 32, 360, 20);
        header.add(sub);

        txtSearch = new JTextField("  🔍  Search products...");
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBounds(660, 5, 190, 32);
        txtSearch.setBorder(BorderFactory.createLineBorder(BORDER));
        header.add(txtSearch);

        JButton btnRefresh = createSmallButton("⟳  Refresh", GREEN, Color.WHITE);
        btnRefresh.setBounds(860, 5, 90, 32);
        header.add(btnRefresh);

        return header;
    }

    private JPanel createTopCards() {
        JPanel top = new JPanel(null);
        top.setBackground(Color.WHITE);
        top.setBounds(20, 80, 980, 300);

        JPanel details = createFormPanel();
        details.setBounds(0, 0, 430, 300);
        top.add(details);

        JPanel image = createImagePanel();
        image.setBounds(445, 0, 265, 300);
        top.add(image);

        JPanel actions = createActionPanel();
        actions.setBounds(725, 0, 255, 300);
        top.add(actions);

        return top;
    }

    private JPanel createFormPanel() {
        JPanel panel = new RoundedBorderPanel();
    panel.setLayout(null);
    panel.setBackground(Color.WHITE);

    JLabel title = cardTitle("Product Details");
    title.setBounds(15, 10, 180, 20);
    panel.add(title);

    txtProductId = createTextField("P007");
    txtProductName = createTextField("Biodegradable Bag (Small)");
    txtPrice = createTextField("25.00");
    txtStock = createTextField("150");
    txtReorder = createTextField("20");

    cmbEcoRating = new JComboBox<>(new String[]{"5 - Excellent", "4 - Very Good", "3 - Good", "2 - Fair"});
    cmbEcoRating.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    cmbEcoRating.setBackground(Color.WHITE);

    txtDescription = new JTextArea("Small biodegradable bag suitable for\nretail packaging.");
    txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    txtDescription.setLineWrap(true);
    txtDescription.setWrapStyleWord(true);

    addLabelAndField(panel, "Product ID", txtProductId, 45);
    addLabelAndField(panel, "Product Name *", txtProductName, 78);
    addLabelAndField(panel, "Price (Rs.) *", txtPrice, 111);

    JLabel ecoLabel = formLabel("Eco Rating *");
    ecoLabel.setBounds(15, 144, 130, 22);
    panel.add(ecoLabel);
    cmbEcoRating.setBounds(145, 144, 190, 28);
    panel.add(cmbEcoRating);

    addLabelAndField(panel, "Quantity in Stock *", txtStock, 177);
    addLabelAndField(panel, "Reorder Level *", txtReorder, 210);

    JLabel descLabel = formLabel("Description");
    descLabel.setBounds(15, 243, 130, 22);
    panel.add(descLabel);

    JScrollPane scroll = new JScrollPane(txtDescription);
    scroll.setBounds(145, 243, 190, 45);
    scroll.setBorder(BorderFactory.createLineBorder(BORDER));
    panel.add(scroll);

    return panel;
    }

    private void addLabelAndField(JPanel panel, String text, JTextField field, int y) {
        JLabel label = formLabel(text);
        label.setBounds(15, y, 130, 22);
        panel.add(label);

        field.setBounds(145, y, 190, 28);
        panel.add(field);
    }

    private JPanel createImagePanel() {
        JPanel panel = new RoundedBorderPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = cardTitle("Product Image");
        title.setBounds(15, 10, 180, 20);
        panel.add(title);

        JLabel image = new JLabel();
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setBorder(BorderFactory.createDashedBorder(new Color(190, 190, 190)));

        ImageIcon icon = new ImageIcon("images/product_bag.png");
        if (icon.getIconWidth() > 0) {
            Image scaled = icon.getImage().getScaledInstance(130, 150, Image.SCALE_SMOOTH);
            image.setIcon(new ImageIcon(scaled));
        } else {
            image.setText("<html><center>Product<br>Image</center></html>");
            image.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        image.setBounds(40, 35, 180, 170);
        panel.add(image);

        JButton upload = createSmallButton("⬆  Upload Image", Color.WHITE, Color.BLACK);
        upload.setBounds(55, 210, 110, 28);
        panel.add(upload);

        JButton remove = createSmallButton("🗑  Remove", Color.WHITE, RED);
        remove.setBounds(170, 210, 80, 28);
        panel.add(remove);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new RoundedBorderPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = cardTitle("Actions");
        title.setBounds(15, 10, 120, 20);
        panel.add(title);

        JButton btnAdd = createBigButton("+  Add New Product", GREEN, Color.WHITE);
        JButton btnUpdate = createBigButton("▣  Update Product", BLUE, Color.WHITE);
        JButton btnDelete = createBigButton("🗑  Delete Product", RED, Color.WHITE);
        JButton btnClear = createBigButton("×  Clear Fields", Color.WHITE, Color.BLACK);

        btnAdd.setBounds(20, 55, 215, 38);
        btnUpdate.setBounds(20, 105, 215, 38);
        btnDelete.setBounds(20, 155, 215, 38);
        btnClear.setBounds(20, 205, 215, 38);

        btnClear.setBorder(BorderFactory.createLineBorder(BORDER));

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearFields());

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new RoundedBorderPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(20, 395, 980, 380);

        JLabel title = cardTitle("Product List");
        title.setBounds(15, 10, 160, 20);
        panel.add(title);

        String[] columns = {"ID", "Product Name", "Price (Rs.)", "Eco Rating", "Stock", "Reorder Level", "Description", "Actions"};

        Object[][] data = {
                {"P001", "Biodegradable Bag (Small)", "25.00", "5 - Excellent", "150", "20", "Small biodegradable bag...", "✎   🗑"},
                {"P002", "Biodegradable Bag (Large)", "35.00", "5 - Excellent", "80", "20", "Large biodegradable bag...", "✎   🗑"},
                {"P003", "Recycled Paper Box", "60.00", "4 - Very Good", "60", "15", "Recycled paper box for...", "✎   🗑"},
                {"P004", "Compostable Food Box", "45.00", "5 - Excellent", "40", "10", "Compostable food containe...", "✎   🗑"},
                {"P005", "Paper Wrap Roll", "30.00", "4 - Very Good", "100", "20", "Paper wrap roll for...", "✎   🗑"},
                {"P006", "Biodegradable Cutlery Set", "20.00", "5 - Excellent", "120", "30", "Eco-friendly cutlery set...", "✎   🗑"}
        };

        tableModel = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setRowHeight(35);
        productTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        productTable.setSelectionBackground(new Color(220, 235, 220));
        productTable.setSelectionForeground(Color.BLACK);
        productTable.setShowGrid(true);
        productTable.setGridColor(new Color(230, 235, 230));

        JTableHeader header = productTable.getTableHeader();
        header.setBackground(new Color(238, 247, 238));
        header.setForeground(DARK_GREEN);
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setPreferredSize(new Dimension(100, 34));

        productTable.getColumnModel().getColumn(0).setPreferredWidth(55);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(110);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(170);
        productTable.getColumnModel().getColumn(7).setPreferredWidth(70);

        productTable.getColumnModel().getColumn(7).setCellRenderer(new ActionRenderer());

        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loadSelectedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(15, 42, 950, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        panel.add(scrollPane);

        JLabel info = new JLabel("Showing 1 to 6 of 6 entries");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        info.setBounds(15, 360, 220, 25);
        panel.add(info);

        JButton prev = createSmallButton("Previous", Color.WHITE, Color.GRAY);
        prev.setBounds(790, 360, 75, 28);
        panel.add(prev);

        JButton page = createSmallButton("1", DARK_GREEN, Color.WHITE);
        page.setBounds(870, 360, 35, 28);
        panel.add(page);

        JButton next = createSmallButton("Next", Color.WHITE, Color.GRAY);
        next.setBounds(910, 360, 55, 28);
        panel.add(next);

        return panel;
    }

    private JTextField createTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(4, 7, 4, 7)
        ));
        return field;
    }

    private JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return label;
    }

    private JLabel cardTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(DARK_GREEN);
        return label;
    }

    private JButton createBigButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSmallButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void addProduct() {
    try {
        String productId = txtProductId.getText().trim();
        String productName = txtProductName.getText().trim();
        String price = txtPrice.getText().trim();
        String stock = txtStock.getText().trim();
        String reorderLevel = txtReorder.getText().trim();
        String ecoRating = cmbEcoRating.getSelectedItem().toString();
        String description = txtDescription.getText().trim();

        if (productId.isEmpty() || productName.isEmpty() || price.isEmpty()
                || stock.isEmpty() || reorderLevel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields");
            return;
        }

        String jsonInputString = "{"
                + "\"productId\":\"" + productId + "\","
                + "\"productName\":\"" + productName + "\","
                + "\"price\":" + price + ","
                + "\"stock\":" + stock + ","
                + "\"reorderLevel\":" + reorderLevel + ","
                + "\"ecoRating\":\"" + ecoRating + "\","
                + "\"description\":\"" + description + "\""
                + "}";

        URL url = new URL("http://localhost:5000/api/products");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == 201) {
            JOptionPane.showMessageDialog(this, "Product added successfully to MongoDB");

            tableModel.addRow(new Object[]{
                    productId,
                    productName,
                    price,
                    ecoRating,
                    stock,
                    reorderLevel,
                    description
            });

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add product. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        ex.printStackTrace();
    }
}
private void loadProductsFromMongoDB() {
    
    try {
        URL url = new URL("http://localhost:5000/api/products");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8")
            );

            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            br.close();

            String json = response.toString();

            tableModel.setRowCount(0);

            String[] products = json.split("\\{\"_id\"");

            for (int i = 1; i < products.length; i++) {
                String item = products[i];
                String mongoId = extractMongoId(item);

                String productId = extractValue(item, "productId");
                String productName = extractValue(item, "productName");
                String price = extractNumberValue(item, "price");
                String stock = extractNumberValue(item, "stock");
                String reorderLevel = extractNumberValue(item, "reorderLevel");
                String ecoRating = extractValue(item, "ecoRating");
                String description = extractValue(item, "description");

                tableModel.addRow(new Object[]{
                        productId,
                        productName,
                        price,
                        ecoRating,
                        stock,
                        reorderLevel,
                        description,
                        "",
                        mongoId
                        
                });
            }

        } else {
            JOptionPane.showMessageDialog(this, "Failed to load products. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage());
        ex.printStackTrace();
    }
}
private String extractMongoId(String json) {
    try {
        String searchKey = "\"_id\":\"";
        int start = json.indexOf(searchKey);

        if (start == -1) {
            return "";
        }

        start = start + searchKey.length();
        int end = json.indexOf("\"", start);

        return json.substring(start, end);
    } catch (Exception e) {
        return "";
    }
}
private String extractValue(String json, String key) {
    try {
        String searchKey = "\"" + key + "\":\"";
        int start = json.indexOf(searchKey);

        if (start == -1) {
            return "";
        }

        start = start + searchKey.length();
        int end = json.indexOf("\"", start);

        return json.substring(start, end);
    } catch (Exception e) {
        return "";
    }
}

private String extractNumberValue(String json, String key) {
    try {
        String searchKey = "\"" + key + "\":";
        int start = json.indexOf(searchKey);

        if (start == -1) {
            return "";
        }

        start = start + searchKey.length();
        int end = json.indexOf(",", start);

        if (end == -1) {
            end = json.indexOf("}", start);
        }

        return json.substring(start, end).trim();
    } catch (Exception e) {
        return "";
    }
}
   private void updateProduct() {
    try {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update");
            return;
        }

        String mongoId = tableModel.getValueAt(selectedRow, 8).toString();

        String productId = txtProductId.getText().trim();
        String productName = txtProductName.getText().trim();
        String price = txtPrice.getText().trim();
        String stock = txtStock.getText().trim();
        String reorderLevel = txtReorder.getText().trim();
        String ecoRating = cmbEcoRating.getSelectedItem().toString();
        String description = txtDescription.getText().trim();

        if (productId.isEmpty() || productName.isEmpty() || price.isEmpty()
                || stock.isEmpty() || reorderLevel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields");
            return;
        }

        String jsonInputString = "{"
                + "\"productId\":\"" + productId + "\","
                + "\"productName\":\"" + productName + "\","
                + "\"price\":" + price + ","
                + "\"stock\":" + stock + ","
                + "\"reorderLevel\":" + reorderLevel + ","
                + "\"ecoRating\":\"" + ecoRating + "\","
                + "\"description\":\"" + description + "\""
                + "}";

        URL url = new URL("http://localhost:5000/api/products/" + mongoId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            JOptionPane.showMessageDialog(this, "Product updated successfully");

            loadProductsFromMongoDB();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update product. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error updating product: " + ex.getMessage());
        ex.printStackTrace();
    }
}

    private void deleteProduct() {
    try {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete");
            return;
        }

        String mongoId = tableModel.getValueAt(selectedRow, 8).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        URL url = new URL("http://localhost:5000/api/products/" + mongoId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            JOptionPane.showMessageDialog(this, "Product deleted successfully");

            loadProductsFromMongoDB();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete product. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error deleting product: " + ex.getMessage());
        ex.printStackTrace();
    }
}

    private void loadSelectedRow() {
        int row = productTable.getSelectedRow();
        if (row == -1) return;

        txtProductId.setText(tableModel.getValueAt(row, 0).toString());
        txtProductName.setText(tableModel.getValueAt(row, 1).toString());
        txtPrice.setText(tableModel.getValueAt(row, 2).toString());
        cmbEcoRating.setSelectedItem(tableModel.getValueAt(row, 3).toString());
        txtStock.setText(tableModel.getValueAt(row, 4).toString());
        txtReorder.setText(tableModel.getValueAt(row, 5).toString());
        txtDescription.setText(tableModel.getValueAt(row, 6).toString());
    }

    private void clearFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtReorder.setText("");
        txtDescription.setText("");
        cmbEcoRating.setSelectedIndex(0);
        productTable.clearSelection();
    }

    class RoundedBorderPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

            g2.setColor(BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        }
    }

    class ActionRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 5));
        panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);

        JButton edit = new JButton("E");
        edit.setFont(new Font("Segoe UI", Font.BOLD, 11));
        edit.setForeground(BLUE);
        edit.setBackground(Color.WHITE);
        edit.setFocusPainted(false);
        edit.setBorder(BorderFactory.createLineBorder(new Color(120, 160, 190)));
        edit.setPreferredSize(new Dimension(24, 24));

        JButton del = new JButton("D");
        del.setFont(new Font("Segoe UI", Font.BOLD, 10));
        del.setForeground(RED);
        del.setBackground(Color.WHITE);
        del.setFocusPainted(false);
        del.setBorder(BorderFactory.createLineBorder(new Color(210, 160, 150)));
        del.setPreferredSize(new Dimension(24, 24));

        panel.add(edit);
        panel.add(del);

        return panel;
        }
    }
}