package views;

import controllers.HealthcareController;
import models.Appointment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentPanel extends JPanel {
    private HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public AppointmentPanel(HealthcareController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Appointment Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Date", "Time", "Status"};
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
        
        JButton btnAdd = createButton("Add Appointment", new Color(46, 204, 113));
        JButton btnEdit = createButton("Edit Appointment", new Color(52, 152, 219));
        JButton btnDelete = createButton("Delete Appointment", new Color(231, 76, 60));
        JButton btnRefresh = createButton("Refresh", new Color(149, 165, 166));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        btnAdd.addActionListener(e -> addAppointment());
        btnEdit.addActionListener(e -> editAppointment());
        btnDelete.addActionListener(e -> deleteAppointment());
        btnRefresh.addActionListener(e -> refreshTable());
        
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = controller.getAllAppointments();
        for (Appointment a : appointments) {
            tableModel.addRow(new Object[]{
                a.getAppointmentID(),
                a.getPatientID(),
                a.getClinicianID(),
                a.getDate(),
                a.getTime(),
                a.getStatus()
            });
        }
    }
    
    private void addAppointment() {
        JTextField txtPatientID = new JTextField();
        JTextField txtClinicianID = new JTextField();
        JTextField txtDate = new JTextField();
        JTextField txtTime = new JTextField();
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Scheduled", "Completed", "Cancelled"});
        
        Object[] message = {
            "Patient ID:", txtPatientID,
            "Clinician ID:", txtClinicianID,
            "Date (YYYY-MM-DD):", txtDate,
            "Time (HH:MM):", txtTime,
            "Status:", cmbStatus
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Appointment", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = controller.generateAppointmentID();
            String patientID = txtPatientID.getText().trim();
            String clinicianID = txtClinicianID.getText().trim();
            String date = txtDate.getText().trim();
            String time = txtTime.getText().trim();
            String status = (String) cmbStatus.getSelectedItem();
            
            if (patientID.isEmpty() || clinicianID.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
            
            Appointment appointment = new Appointment(id, patientID, clinicianID, date, time, status);
            controller.addAppointment(appointment);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment added successfully!");
        }
    }
    
    private void editAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to edit!");
            return;
        }
        
        Appointment oldAppointment = controller.getAllAppointments().get(selectedRow);
        
        JTextField txtPatientID = new JTextField(oldAppointment.getPatientID());
        JTextField txtClinicianID = new JTextField(oldAppointment.getClinicianID());
        JTextField txtDate = new JTextField(oldAppointment.getDate());
        JTextField txtTime = new JTextField(oldAppointment.getTime());
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Scheduled", "Completed", "Cancelled"});
        cmbStatus.setSelectedItem(oldAppointment.getStatus());
        
        Object[] message = {
            "Patient ID:", txtPatientID,
            "Clinician ID:", txtClinicianID,
            "Date (YYYY-MM-DD):", txtDate,
            "Time (HH:MM):", txtTime,
            "Status:", cmbStatus
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Appointment", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Appointment updatedAppointment = new Appointment(
                oldAppointment.getAppointmentID(),
                txtPatientID.getText().trim(),
                txtClinicianID.getText().trim(),
                txtDate.getText().trim(),
                txtTime.getText().trim(),
                (String) cmbStatus.getSelectedItem()
            );
            
            controller.updateAppointment(selectedRow, updatedAppointment);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
        }
    }
    
    private void deleteAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this appointment?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteAppointment(selectedRow);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment deleted successfully!");
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

