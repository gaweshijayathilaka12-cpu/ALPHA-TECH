import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientManagementPanel extends JPanel {

    private JTextField txtClientId, txtClientName, txtPhone, txtEmail, txtCity, txtCreditLimit, txtSearch;
    private JTextArea txtAddress;
    private JComboBox<String> cmbStatus;
    private JTable clientTable, recentOrderTable;

    private final Color GREEN = new Color(20, 83, 45);
    private final Color BTN_GREEN = new Color(58, 125, 35);
    private final Color RED = new Color(190, 55, 45);
    private final Color LIGHT_GREEN = new Color(232, 243, 225);
    private final Color BORDER = new Color(225, 225, 225);

    public ClientManagementPanel() {
        setLayout(new BorderLayout(0, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        loadClientsFromMongoDB();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Client Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.BLACK);

        JLabel sub = new JLabel("Add, update, delete and manage clients.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        titlePanel.add(title);
        titlePanel.add(sub);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setBackground(Color.WHITE);

        txtSearch = new JTextField("Search clients...");
        txtSearch.setPreferredSize(new Dimension(260, 38));
        JButton addBtn = greenButton("+  Add New Client", 170, 38);

        addBtn.addActionListener(e -> addClient());

        right.add(txtSearch);
        right.add(addBtn);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    private JPanel createContent() {
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(Color.WHITE);

        JPanel top = new JPanel(new GridLayout(1, 2, 15, 0));
        top.setBackground(Color.WHITE);
        top.setPreferredSize(new Dimension(950, 335));

        top.add(createClientDetailsPanel());
        top.add(createRightPanel());

        main.add(top, BorderLayout.NORTH);
        main.add(createClientListPanel(), BorderLayout.CENTER);

        return main;
    }

    private JPanel createClientDetailsPanel() {
        JPanel panel = cardPanel();
        panel.setLayout(new BorderLayout(0, 12));
        panel.add(sectionTitle("Client Details"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtClientId = field("C005");
        txtClientName = field("Eco Solutions (Pvt) Ltd");
        txtPhone = field("071 456 7890");
        txtEmail = field("info@ecosolutions.lk");

        txtAddress = new JTextArea("45, Park Road,\nColombo 05.");
        txtAddress.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);

        txtCity = field("Colombo");
        txtCreditLimit = field("100,000.00");
        cmbStatus = new JComboBox<>(new String[] { "Active", "Inactive" });

        addRow(form, gbc, 0, "Client ID *", txtClientId);
        addRow(form, gbc, 1, "Client Name *", txtClientName);
        addRow(form, gbc, 2, "Phone *", txtPhone);
        addRow(form, gbc, 3, "Email", txtEmail);
        addRow(form, gbc, 4, "Address *", new JScrollPane(txtAddress));
        addRow(form, gbc, 5, "City", txtCity);
        addRow(form, gbc, 6, "Credit Limit (Rs.)", txtCreditLimit);
        addRow(form, gbc, 7, "Status", cmbStatus);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel right = new JPanel(new BorderLayout(0, 15));
        right.setBackground(Color.WHITE);

        right.add(createRecentOrdersPanel(), BorderLayout.CENTER);
        right.add(createActionsPanel(), BorderLayout.SOUTH);

        return right;
    }

    private JPanel createRecentOrdersPanel() {
        JPanel panel = cardPanel();
        panel.setLayout(new BorderLayout(0, 12));

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);

        head.add(sectionTitle("Recent Orders"), BorderLayout.WEST);
        head.add(whiteButton("View All", 90, 32), BorderLayout.EAST);

        String[] cols = { "Order ID", "Order Date", "Total (Rs.)", "Status" };
        Object[][] data = {
                { "ORD00124", "17-05-2026", "12,500.00", "Pending" },
                { "ORD00110", "15-05-2026", "8,750.00", "Assigned" },
                { "ORD00098", "12-05-2026", "15,100.00", "Delivered" },
                { "ORD00085", "09-05-2026", "9,600.00", "Pending" },
                { "ORD00072", "06-05-2026", "6,450.00", "Delivered" }
        };

        recentOrderTable = new JTable(new DefaultTableModel(data, cols));
        styleTable(recentOrderTable);
        recentOrderTable.setRowHeight(34);
        recentOrderTable.getColumnModel().getColumn(3).setCellRenderer(new StatusRenderer());

        JScrollPane scroll = new JScrollPane(recentOrderTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));

        panel.add(head, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActionsPanel() {
        JPanel panel = cardPanel();
        panel.setLayout(new BorderLayout(0, 12));
        panel.setPreferredSize(new Dimension(450, 120));

        panel.add(sectionTitle("Actions"), BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        buttons.setBackground(Color.WHITE);

        JButton updateBtn = greenButton("▣  Update Client", 150, 40);
        JButton deleteBtn = redButton("▣  Delete Client", 150, 40);
        JButton clearBtn = whiteButton("✕  Clear Fields", 140, 40);

        updateBtn.addActionListener(e -> updateClient());
        deleteBtn.addActionListener(e -> deleteClient());
        clearBtn.addActionListener(e -> clearFields());

        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(clearBtn);

        panel.add(buttons, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createClientListPanel() {
        JPanel panel = cardPanel();
        panel.setLayout(new BorderLayout(0, 12));

        panel.add(sectionTitle("Client List"), BorderLayout.NORTH);

        String[] cols = {
                "ID", "Client Name", "Phone", "Email", "Address",
                "Credit Limit (Rs.)", "Status", "Actions"
        };

        Object[][] data = {
                { "C001", "Eco Mart (Pvt) Ltd", "077 123 4567", "info@ecomart.lk", "123, Galle Road,\nColombo 03.",
                        "75,000.00", "Active", "Edit   Delete" },
                { "C002", "Green Store", "071 234 5678", "contact@greenstore.lk", "45, Kandy Road,\nKandy.",
                        "50,000.00", "Active", "Edit   Delete" },
                { "C003", "Nature Lanka", "070 345 6789", "sales@nature.lk", "78, Negombo Road,\nNegombo.", "60,000.00",
                        "Active", "Edit   Delete" },
                { "C004", "Organic Shop", "076 456 7890", "hello@organic.lk", "56, Dehiwala Road,\nDehiwala.",
                        "40,000.00", "Inactive", "Edit   Delete" },
                { "C005", "Eco Solutions (Pvt) Ltd", "071 456 7890", "info@ecosolutions.lk",
                        "45, Park Road,\nColombo 05.", "100,000.00", "Active", "Edit  Delete" }
        };

        clientTable = new JTable(new DefaultTableModel(data, cols));
        styleTable(clientTable);
        clientTable.setRowHeight(42);
        clientTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        clientTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        clientTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        clientTable.getColumnModel().getColumn(3).setPreferredWidth(180);
        clientTable.getColumnModel().getColumn(4).setPreferredWidth(220);
        clientTable.getColumnModel().getColumn(5).setPreferredWidth(130);
        clientTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        clientTable.getColumnModel().getColumn(7).setPreferredWidth(120);
        clientTable.getColumnModel().getColumn(6).setCellRenderer(new StatusRenderer());

        clientTable.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = clientTable.getSelectedRow();

        txtClientId.setText(clientTable.getValueAt(selectedRow, 0).toString());
        txtClientName.setText(clientTable.getValueAt(selectedRow, 1).toString());
        txtPhone.setText(clientTable.getValueAt(selectedRow, 2).toString());
        txtEmail.setText(clientTable.getValueAt(selectedRow, 3).toString());
        txtAddress.setText(clientTable.getValueAt(selectedRow, 4).toString());
        txtCreditLimit.setText(clientTable.getValueAt(selectedRow, 5).toString());
        cmbStatus.setSelectedItem(clientTable.getValueAt(selectedRow, 6).toString());
    }
});

        JScrollPane scroll = new JScrollPane(clientTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);

        JLabel showing = new JLabel("Showing 1 to 5 of 5 entries");
        showing.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JPanel pages = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pages.setBackground(Color.WHITE);
        pages.add(whiteButton("Previous", 90, 32));
        pages.add(greenButton("1", 45, 32));
        pages.add(whiteButton("Next", 70, 32));

        footer.add(showing, BorderLayout.WEST);
        footer.add(pages, BorderLayout.EAST);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }
     private void addClient() {
    try {
        String clientId = txtClientId.getText().trim();
        String clientName = txtClientName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String city = txtCity.getText().trim();
        String creditLimit = txtCreditLimit.getText().trim().replace(",", "");
        String status = cmbStatus.getSelectedItem().toString();

        if (clientId.isEmpty() || clientName.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields");
            return;
        }

        if (creditLimit.isEmpty()) {
            creditLimit = "0";
        }

        String jsonInputString = "{"
                + "\"clientId\":\"" + clientId + "\","
                + "\"clientName\":\"" + clientName + "\","
                + "\"phone\":\"" + phone + "\","
                + "\"email\":\"" + email + "\","
                + "\"address\":\"" + address.replace("\n", " ") + "\","
                + "\"city\":\"" + city + "\","
                + "\"creditLimit\":" + creditLimit + ","
                + "\"status\":\"" + status + "\""
                + "}";

        URL url = new URL("http://localhost:5000/api/clients");
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
            JOptionPane.showMessageDialog(this, "Client added successfully to MongoDB");
            loadClientsFromMongoDB();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add client. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error adding client: " + ex.getMessage());
        ex.printStackTrace();
    }
}

private void loadClientsFromMongoDB() {
    try {
        URL url = new URL("http://localhost:5000/api/clients");
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

            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{
                            "ID", "Client Name", "Phone", "Email", "Address",
                            "Credit Limit (Rs.)", "Status", "Actions"
                    },
                    0
            );

            String[] clients = json.split("\\{\"_id\"");

            for (int i = 1; i < clients.length; i++) {
                String item = clients[i];

                String clientId = extractValue(item, "clientId");
                String clientName = extractValue(item, "clientName");
                String phone = extractValue(item, "phone");
                String email = extractValue(item, "email");
                String address = extractValue(item, "address");
                String creditLimit = extractNumberValue(item, "creditLimit");
                String status = extractValue(item, "status");

                model.addRow(new Object[]{
                        clientId,
                        clientName,
                        phone,
                        email,
                        address,
                        creditLimit,
                        status,
                        "Edit   Delete"
                });
            }

            clientTable.setModel(model);
            styleTable(clientTable);
            clientTable.setRowHeight(42);
            clientTable.getColumnModel().getColumn(6).setCellRenderer(new StatusRenderer());

        } else {
            JOptionPane.showMessageDialog(this, "Failed to load clients. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error loading clients: " + ex.getMessage());
        ex.printStackTrace();
    }
}

private void updateClient() {
    try {
        int selectedRow = clientTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a client to update");
            return;
        }

        String clientIdFromTable = clientTable.getValueAt(selectedRow, 0).toString();

        String clientId = txtClientId.getText().trim();
        String clientName = txtClientName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String city = txtCity.getText().trim();
        String creditLimit = txtCreditLimit.getText().trim().replace(",", "");
        String status = cmbStatus.getSelectedItem().toString();

        if (clientId.isEmpty() || clientName.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields");
            return;
        }

        if (creditLimit.isEmpty()) {
            creditLimit = "0";
        }

        String jsonInputString = "{"
                + "\"clientId\":\"" + clientId + "\","
                + "\"clientName\":\"" + clientName + "\","
                + "\"phone\":\"" + phone + "\","
                + "\"email\":\"" + email + "\","
                + "\"address\":\"" + address.replace("\n", " ") + "\","
                + "\"city\":\"" + city + "\","
                + "\"creditLimit\":" + creditLimit + ","
                + "\"status\":\"" + status + "\""
                + "}";

        URL url = new URL("http://localhost:5000/api/clients/" + clientIdFromTable);
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
            JOptionPane.showMessageDialog(this, "Client updated successfully");
            loadClientsFromMongoDB();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update client. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error updating client: " + ex.getMessage());
        ex.printStackTrace();
    }
}

private void deleteClient() {
    try {
        int selectedRow = clientTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a client to delete");
            return;
        }

        String clientIdFromTable = clientTable.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this client?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        URL url = new URL("http://localhost:5000/api/clients/" + clientIdFromTable);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            JOptionPane.showMessageDialog(this, "Client deleted successfully");
            loadClientsFromMongoDB();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete client. Error code: " + responseCode);
        }

        conn.disconnect();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error deleting client: " + ex.getMessage());
        ex.printStackTrace();
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

    private void clearFields() {
        txtClientId.setText("");
        txtClientName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtCreditLimit.setText("");
        cmbStatus.setSelectedIndex(0);
    }

    private JPanel cardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        return panel;
    }

    private JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(GREEN);
        return label;
    }

    private JTextField field(String text) {
        JTextField f = new JTextField(text);
        f.setPreferredSize(new Dimension(230, 30));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return f;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, Component field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        field.setPreferredSize(new Dimension(260, 30));
        panel.add(field, gbc);
    }

    private JButton greenButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(BTN_GREEN);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        return btn;
    }

    private JButton redButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(RED);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(5, 10, 5, 10));
        return btn;
    }

    private JButton whiteButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        return btn;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(LIGHT_GREEN);
        table.getTableHeader().setForeground(GREEN);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);
    }

    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 11));

            if ("Active".equals(value) || "Delivered".equals(value)) {
                label.setBackground(new Color(230, 245, 232));
                label.setForeground(new Color(46, 125, 50));
            } else if ("Pending".equals(value)) {
                label.setBackground(new Color(255, 243, 224));
                label.setForeground(new Color(230, 124, 11));
            } else if ("Assigned".equals(value)) {
                label.setBackground(new Color(227, 242, 253));
                label.setForeground(new Color(21, 101, 192));
            } else {
                label.setBackground(new Color(240, 240, 240));
                label.setForeground(Color.DARK_GRAY);
            }

            label.setOpaque(true);
            return label;
        }
    }
}