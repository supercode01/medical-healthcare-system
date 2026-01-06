package views;

import controllers.HealthcareController;
import javax.swing.*;
import java.awt.*;

/**
 * Main View - MVC Pattern
 * Main GUI window with navigation
 */
public class MainView extends JFrame {
    private HealthcareController controller;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public MainView() {
        controller = new HealthcareController();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Medical Healthcare Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Top Panel - Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        titlePanel.setPreferredSize(new Dimension(1000, 80));
        
        JLabel titleLabel = new JLabel("🏥 Medical Healthcare System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Left Panel - Navigation
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(7, 1, 10, 10));
        navPanel.setBackground(new Color(52, 73, 94));
        navPanel.setPreferredSize(new Dimension(200, 600));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Navigation Buttons
        JButton btnPatients = createNavButton("👤 Patients");
        JButton btnClinicians = createNavButton("👨‍⚕️ Clinicians");
        JButton btnAppointments = createNavButton("📅 Appointments");
        JButton btnPrescriptions = createNavButton("💊 Prescriptions");
        JButton btnReferrals = createNavButton("📋 Referrals");
        JButton btnAbout = createNavButton("ℹ️ About");
        JButton btnExit = createNavButton("🚪 Exit");
        
        navPanel.add(btnPatients);
        navPanel.add(btnClinicians);
        navPanel.add(btnAppointments);
        navPanel.add(btnPrescriptions);
        navPanel.add(btnReferrals);
        navPanel.add(btnAbout);
        navPanel.add(btnExit);
        
        add(navPanel, BorderLayout.WEST);
        
        // Center Panel - CardLayout for different views
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Add different panels
        mainPanel.add(new DashboardPanel(), "dashboard");
        mainPanel.add(new PatientPanel(controller), "patients");
        mainPanel.add(new ClinicianPanel(controller), "clinicians");
        mainPanel.add(new AppointmentPanel(controller), "appointments");
        mainPanel.add(new PrescriptionPanel(controller), "prescriptions");
        mainPanel.add(new ReferralPanel(controller), "referrals");
        mainPanel.add(new AboutPanel(), "about");
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button Actions
        btnPatients.addActionListener(e -> cardLayout.show(mainPanel, "patients"));
        btnClinicians.addActionListener(e -> cardLayout.show(mainPanel, "clinicians"));
        btnAppointments.addActionListener(e -> cardLayout.show(mainPanel, "appointments"));
        btnPrescriptions.addActionListener(e -> cardLayout.show(mainPanel, "prescriptions"));
        btnReferrals.addActionListener(e -> cardLayout.show(mainPanel, "referrals"));
        btnAbout.addActionListener(e -> cardLayout.show(mainPanel, "about"));
        btnExit.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to exit?", 
                "Exit Confirmation", 
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        // Show dashboard by default
        cardLayout.show(mainPanel, "dashboard");
    }
    
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 152, 219));
            }
        });
        
        return btn;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainView().setVisible(true);
        });
    }
}

// Dashboard Panel
class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>" +
            "<h1>Welcome to Healthcare Management System</h1>" +
            "<p style='font-size: 16px; margin-top: 20px;'>Select an option from the menu to get started</p>" +
            "</div></html>", SwingConstants.CENTER);
        
        add(welcomeLabel, BorderLayout.CENTER);
    }
}

// About Panel
class AboutPanel extends JPanel {
    public AboutPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JTextArea aboutText = new JTextArea();
        aboutText.setEditable(false);
        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setText("\n\n    Medical Healthcare Management System\n\n" +
                         "    Version: 1.0\n" +
                         "    Developed using: Java Swing\n" +
                         "    Architecture: MVC Pattern\n" +
                         "    Design Pattern: Singleton (Referral Management)\n\n" +
                         "    Features:\n" +
                         "    • Patient Management (CRUD)\n" +
                         "    • Clinician Management (CRUD)\n" +
                         "    • Appointment Management (CRUD)\n" +
                         "    • Prescription Management (CRUD)\n" +
                         "    • Referral System with Email Generation\n\n" +
                         "    Data is persisted in CSV files.\n");
        
        add(new JScrollPane(aboutText), BorderLayout.CENTER);
    }
}