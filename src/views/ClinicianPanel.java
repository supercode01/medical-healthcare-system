package views;

import controllers.HealthcareController;
import models.Clinician;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicianPanel extends JPanel {
    private HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ClinicianPanel(HealthcareController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Clinician Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Specialization", "Contact", "Email"};
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
        
        JButton btnAdd = createButton("Add Clinician", new Color(46, 204, 113));
        JButton btnEdit = createButton("Edit Clinician", new Color(52, 152, 219));
        JButton btnDelete = createButton("Delete Clinician", new Color(231, 76, 60));
        JButton btnRefresh = createButton("Refresh", new Color(149, 165, 166));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        btnAdd.addActionListener(e -> addClinician());
        btnEdit.addActionListener(e -> editClinician());
        btnDelete.addActionListener(e -> deleteClinician());
        btnRefresh.addActionListener(e -> refreshTable());
        
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Clinician> clinicians = controller.getAllClinicians();
        for (Clinician c : clinicians) {
            tableModel.addRow(new Object[]{
                c.getClinicianID(),
                c.getName(),
                c.getSpecialization(),
                c.getContactNumber(),
                c.getEmail()
            });
        }
    }
    
    private void addClinician() {
        JTextField txtName = new JTextField();
        JTextField txtSpecialization = new JTextField();
        JTextField txtContact = new JTextField();
        JTextField txtEmail = new JTextField();
        
        Object[] message = {
            "Name:", txtName,
            "Specialization:", txtSpecialization,
            "Contact:", txtContact,
            "Email:", txtEmail
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Clinician", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = controller.generateClinicianID();
            String name = txtName.getText().trim();
            String specialization = txtSpecialization.getText().trim();
            String contact = txtContact.getText().trim();
            String email = txtEmail.getText().trim();
            
            if (name.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Contact are required!");
                return;
            }
            
            Clinician clinician = new Clinician(id, name, specialization, contact, email);
            controller.addClinician(clinician);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Clinician added successfully!");
        }
    }
    
    private void editClinician() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a clinician to edit!");
            return;
        }
        
        Clinician oldClinician = controller.getAllClinicians().get(selectedRow);
        
        JTextField txtName = new JTextField(oldClinician.getName());
        JTextField txtSpecialization = new JTextField(oldClinician.getSpecialization());
        JTextField txtContact = new JTextField(oldClinician.getContactNumber());
        JTextField txtEmail = new JTextField(oldClinician.getEmail());
        
        Object[] message = {
            "Name:", txtName,
            "Specialization:", txtSpecialization,
            "Contact:", txtContact,
            "Email:", txtEmail
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Clinician", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Clinician updatedClinician = new Clinician(
                oldClinician.getClinicianID(),
                txtName.getText().trim(),
                txtSpecialization.getText().trim(),
                txtContact.getText().trim(),
                txtEmail.getText().trim()
            );
            
            controller.updateClinician(selectedRow, updatedClinician);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Clinician updated successfully!");
        }
    }
    
    private void deleteClinician() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a clinician to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this clinician?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteClinician(selectedRow);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Clinician deleted successfully!");
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

