package views;

import controllers.HealthcareController;
import models.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientPanel extends JPanel {
    private HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PatientPanel(HealthcareController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Patient Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Age", "Gender", "Contact", "Address", "Medical History"};
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
        
        JButton btnAdd = createButton("➕ Add Patient", new Color(46, 204, 113));
        JButton btnEdit = createButton("✏️ Edit Patient", new Color(52, 152, 219));
        JButton btnDelete = createButton("🗑️ Delete Patient", new Color(231, 76, 60));
        JButton btnRefresh = createButton("🔄 Refresh", new Color(149, 165, 166));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        btnAdd.addActionListener(e -> addPatient());
        btnEdit.addActionListener(e -> editPatient());
        btnDelete.addActionListener(e -> deletePatient());
        btnRefresh.addActionListener(e -> refreshTable());
        
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Patient> patients = controller.getAllPatients();
        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                p.getPatientID(),
                p.getName(),
                p.getAge(),
                p.getGender(),
                p.getContactNumber(),
                p.getAddress(),
                p.getMedicalHistory()
            });
        }
    }
    
    private void addPatient() {
        JTextField txtName = new JTextField();
        JTextField txtAge = new JTextField();
        JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField txtContact = new JTextField();
        JTextField txtAddress = new JTextField();
        JTextField txtHistory = new JTextField();
        
        Object[] message = {
            "Name:", txtName,
            "Age:", txtAge,
            "Gender:", cmbGender,
            "Contact:", txtContact,
            "Address:", txtAddress,
            "Medical History:", txtHistory
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Patient", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = controller.generatePatientID();
                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());
                String gender = (String) cmbGender.getSelectedItem();
                String contact = txtContact.getText().trim();
                String address = txtAddress.getText().trim();
                String history = txtHistory.getText().trim();
                
                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and Contact are required!");
                    return;
                }
                
                Patient patient = new Patient(id, name, age, gender, contact, address, history);
                controller.addPatient(patient);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid age format!");
            }
        }
    }
    
    private void editPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit!");
            return;
        }
        
        Patient oldPatient = controller.getAllPatients().get(selectedRow);
        
        JTextField txtName = new JTextField(oldPatient.getName());
        JTextField txtAge = new JTextField(String.valueOf(oldPatient.getAge()));
        JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        cmbGender.setSelectedItem(oldPatient.getGender());
        JTextField txtContact = new JTextField(oldPatient.getContactNumber());
        JTextField txtAddress = new JTextField(oldPatient.getAddress());
        JTextField txtHistory = new JTextField(oldPatient.getMedicalHistory());
        
        Object[] message = {
            "Name:", txtName,
            "Age:", txtAge,
            "Gender:", cmbGender,
            "Contact:", txtContact,
            "Address:", txtAddress,
            "Medical History:", txtHistory
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Patient", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Patient updatedPatient = new Patient(
                    oldPatient.getPatientID(),
                    txtName.getText().trim(),
                    Integer.parseInt(txtAge.getText().trim()),
                    (String) cmbGender.getSelectedItem(),
                    txtContact.getText().trim(),
                    txtAddress.getText().trim(),
                    txtHistory.getText().trim()
                );
                
                controller.updatePatient(selectedRow, updatedPatient);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Patient updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid age format!");
            }
        }
    }
    
    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this patient?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePatient(selectedRow);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
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