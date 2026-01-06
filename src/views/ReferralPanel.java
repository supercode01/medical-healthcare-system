package views;

import controllers.HealthcareController;
import models.Referral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReferralPanel extends JPanel {
    private HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ReferralPanel(HealthcareController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Referral Management (Singleton Pattern)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Referral ID", "Patient ID", "Patient Name", "From", "To", "Timestamp"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnCreate = createButton("➕ Create Referral", new Color(46, 204, 113));
        JButton btnRefresh = createButton("🔄 Refresh", new Color(149, 165, 166));
        JButton btnViewEmail = createButton("📧 View Email", new Color(52, 152, 219));
        
        buttonPanel.add(btnCreate);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnViewEmail);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        btnCreate.addActionListener(e -> createReferral());
        btnRefresh.addActionListener(e -> refreshTable());
        btnViewEmail.addActionListener(e -> viewEmailContent());
        
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Referral.ReferralData> referrals = controller.getAllReferrals();
        for (Referral.ReferralData r : referrals) {
            tableModel.addRow(new Object[]{
                r.referralID,
                r.patientID,
                r.patientName,
                r.fromClinicianID,
                r.toClinicianID,
                r.timestamp.toString()
            });
        }
    }
    
    private void createReferral() {
        JTextField txtPatientID = new JTextField();
        JTextField txtPatientName = new JTextField();
        JTextField txtFromClinician = new JTextField();
        JTextField txtToClinician = new JTextField();
        JTextArea txtReason = new JTextArea(5, 20);
        txtReason.setLineWrap(true);
        txtReason.setWrapStyleWord(true);
        JScrollPane scrollReason = new JScrollPane(txtReason);
        
        Object[] message = {
            "Patient ID:", txtPatientID,
            "Patient Name:", txtPatientName,
            "From Clinician ID:", txtFromClinician,
            "To Clinician ID:", txtToClinician,
            "Referral Reason:", scrollReason
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Create New Referral", 
                                                   JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            String patientID = txtPatientID.getText().trim();
            String patientName = txtPatientName.getText().trim();
            String fromClinician = txtFromClinician.getText().trim();
            String toClinician = txtToClinician.getText().trim();
            String reason = txtReason.getText().trim();
            
            if (patientID.isEmpty() || patientName.isEmpty() || 
                fromClinician.isEmpty() || toClinician.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
            
            controller.createReferral(patientID, patientName, fromClinician, 
                                     toClinician, reason);
            refreshTable();
            
            JOptionPane.showMessageDialog(this, 
                "Referral created successfully!\nEmail file generated in 'output' folder.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void viewEmailContent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral to view!");
            return;
        }
        
        List<Referral.ReferralData> referrals = controller.getAllReferrals();
        Referral.ReferralData selected = referrals.get(selectedRow);
        
        // Generate email content preview
        String emailPreview = generateEmailPreview(selected);
        
        JTextArea textArea = new JTextArea(emailPreview);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setCaretPosition(0);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Referral Email - " + selected.referralID, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String generateEmailPreview(Referral.ReferralData referral) {
        StringBuilder email = new StringBuilder();
        email.append("===============================================\n");
        email.append("         MEDICAL REFERRAL NOTIFICATION\n");
        email.append("===============================================\n\n");
        email.append("Referral ID: ").append(referral.referralID).append("\n");
        email.append("Date & Time: ").append(referral.timestamp).append("\n\n");
        email.append("TO: Clinician ").append(referral.toClinicianID).append("\n");
        email.append("FROM: Clinician ").append(referral.fromClinicianID).append("\n\n");
        email.append("PATIENT INFORMATION:\n");
        email.append("- Patient ID: ").append(referral.patientID).append("\n");
        email.append("- Patient Name: ").append(referral.patientName).append("\n\n");
        email.append("REFERRAL REASON:\n");
        email.append(referral.reason).append("\n\n");
        email.append("Please review this referral and schedule an appointment\n");
        email.append("at your earliest convenience.\n\n");
        email.append("===============================================\n");
        return email.toString();
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}