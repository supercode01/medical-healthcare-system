package views;

import controllers.HealthcareController;
import models.Prescription;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PrescriptionPanel extends JPanel {
    private HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PrescriptionPanel(HealthcareController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Prescription Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Medication", "Dosage", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnAdd = createButton("Add Prescription", new Color(46, 204, 113));
        JButton btnEdit = createButton("Edit Prescription", new Color(52, 152, 219));
        JButton btnDelete = createButton("Delete Prescription", new Color(231, 76, 60));
        JButton btnRefresh = createButton("Refresh", new Color(149, 165, 166));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        btnAdd.addActionListener(e -> addPrescription());
        btnEdit.addActionListener(e -> editPrescription());
        btnDelete.addActionListener(e -> deletePrescription());
        btnRefresh.addActionListener(e -> refreshTable());
        
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Prescription> prescriptions = controller.getAllPrescriptions();
        for (Prescription p : prescriptions) {
            tableModel.addRow(new Object[]{
                p.getPrescriptionID(),
                p.getPatientID(),
                p.getClinicianID(),
                p.getMedication(),
                p.getDosage(),
                p.getDate()
            });
        }
    }
    
    private void addPrescription() {
        JTextField txtPatientID = new JTextField();
        JTextField txtClinicianID = new JTextField();
        JTextField txtMedication = new JTextField();
        JTextField txtDosage = new JTextField();
        JTextField txtDate = new JTextField();
        
        Object[] message = {
            "Patient ID:", txtPatientID,
            "Clinician ID:", txtClinicianID,
            "Medication:", txtMedication,
            "Dosage:", txtDosage,
            "Date (YYYY-MM-DD):", txtDate
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Prescription", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = controller.generatePrescriptionID();
            String patientID = txtPatientID.getText().trim();
            String clinicianID = txtClinicianID.getText().trim();
            String medication = txtMedication.getText().trim();
            String dosage = txtDosage.getText().trim();
            String date = txtDate.getText().trim();
            
            if (patientID.isEmpty() || clinicianID.isEmpty() || medication.isEmpty() || dosage.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
            
            Prescription prescription = new Prescription(id, patientID, clinicianID, medication, dosage, date);
            controller.addPrescription(prescription);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Prescription added successfully!");
        }
    }
    
    private void editPrescription() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription to edit!");
            return;
        }
        
        Prescription oldPrescription = controller.getAllPrescriptions().get(selectedRow);
        
        JTextField txtPatientID = new JTextField(oldPrescription.getPatientID());
        JTextField txtClinicianID = new JTextField(oldPrescription.getClinicianID());
        JTextField txtMedication = new JTextField(oldPrescription.getMedication());
        JTextField txtDosage = new JTextField(oldPrescription.getDosage());
        JTextField txtDate = new JTextField(oldPrescription.getDate());
        
        Object[] message = {
            "Patient ID:", txtPatientID,
            "Clinician ID:", txtClinicianID,
            "Medication:", txtMedication,
            "Dosage:", txtDosage,
            "Date (YYYY-MM-DD):", txtDate
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Prescription", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Prescription updatedPrescription = new Prescription(
                oldPrescription.getPrescriptionID(),
                txtPatientID.getText().trim(),
                txtClinicianID.getText().trim(),
                txtMedication.getText().trim(),
                txtDosage.getText().trim(),
                txtDate.getText().trim()
            );
            
            controller.updatePrescription(selectedRow, updatedPrescription);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Prescription updated successfully!");
        }
    }
    
    private void deletePrescription() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this prescription?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePrescription(selectedRow);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Prescription deleted successfully!");
        }
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

