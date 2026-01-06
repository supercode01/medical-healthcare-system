package controllers;

import models.*;
import utils.CSVHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class - MVC Pattern
 * Manages business logic and data operations
 */
public class HealthcareController {
    private List<Patient> patients;
    private List<Clinician> clinicians;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    
    // File paths
    private static final String PATIENTS_FILE = "data/patients.csv";
    private static final String CLINICIANS_FILE = "data/clinicians.csv";
    private static final String APPOINTMENTS_FILE = "data/appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "data/prescriptions.csv";
    
    // Constructor
    public HealthcareController() {
        loadAllData();
    }
    
    // Load all data from CSV files
    private void loadAllData() {
        patients = CSVHandler.loadPatients(PATIENTS_FILE);
        clinicians = CSVHandler.loadClinicians(CLINICIANS_FILE);
        appointments = CSVHandler.loadAppointments(APPOINTMENTS_FILE);
        prescriptions = CSVHandler.loadPrescriptions(PRESCRIPTIONS_FILE);
    }
    
    // ========== PATIENT OPERATIONS ==========
    
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }
    
    public void addPatient(Patient patient) {
        patients.add(patient);
        CSVHandler.savePatients(PATIENTS_FILE, patients);
    }
    
    public void updatePatient(int index, Patient patient) {
        if (index >= 0 && index < patients.size()) {
            patients.set(index, patient);
            CSVHandler.savePatients(PATIENTS_FILE, patients);
        }
    }
    
    public void deletePatient(int index) {
        if (index >= 0 && index < patients.size()) {
            patients.remove(index);
            CSVHandler.savePatients(PATIENTS_FILE, patients);
        }
    }
    
    public String generatePatientID() {
        int maxNum = 0;
        for (Patient p : patients) {
            String id = p.getPatientID().substring(1);
            int num = Integer.parseInt(id);
            if (num > maxNum) maxNum = num;
        }
        return String.format("P%03d", maxNum + 1);
    }
    
    // ========== CLINICIAN OPERATIONS ==========
    
    public List<Clinician> getAllClinicians() {
        return new ArrayList<>(clinicians);
    }
    
    public void addClinician(Clinician clinician) {
        clinicians.add(clinician);
        CSVHandler.saveClinicians(CLINICIANS_FILE, clinicians);
    }
    
    public void updateClinician(int index, Clinician clinician) {
        if (index >= 0 && index < clinicians.size()) {
            clinicians.set(index, clinician);
            CSVHandler.saveClinicians(CLINICIANS_FILE, clinicians);
        }
    }
    
    public void deleteClinician(int index) {
        if (index >= 0 && index < clinicians.size()) {
            clinicians.remove(index);
            CSVHandler.saveClinicians(CLINICIANS_FILE, clinicians);
        }
    }
    
    public String generateClinicianID() {
        int maxNum = 0;
        for (Clinician c : clinicians) {
            String id = c.getClinicianID().substring(1);
            int num = Integer.parseInt(id);
            if (num > maxNum) maxNum = num;
        }
        return String.format("C%03d", maxNum + 1);
    }
    
    // ========== APPOINTMENT OPERATIONS ==========
    
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
    
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        CSVHandler.saveAppointments(APPOINTMENTS_FILE, appointments);
    }
    
    public void updateAppointment(int index, Appointment appointment) {
        if (index >= 0 && index < appointments.size()) {
            appointments.set(index, appointment);
            CSVHandler.saveAppointments(APPOINTMENTS_FILE, appointments);
        }
    }
    
    public void deleteAppointment(int index) {
        if (index >= 0 && index < appointments.size()) {
            appointments.remove(index);
            CSVHandler.saveAppointments(APPOINTMENTS_FILE, appointments);
        }
    }
    
    public String generateAppointmentID() {
        int maxNum = 0;
        for (Appointment a : appointments) {
            String id = a.getAppointmentID().substring(1);
            int num = Integer.parseInt(id);
            if (num > maxNum) maxNum = num;
        }
        return String.format("A%03d", maxNum + 1);
    }
    
    // ========== PRESCRIPTION OPERATIONS ==========
    
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions);
    }
    
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        CSVHandler.savePrescriptions(PRESCRIPTIONS_FILE, prescriptions);
    }
    
    public void updatePrescription(int index, Prescription prescription) {
        if (index >= 0 && index < prescriptions.size()) {
            prescriptions.set(index, prescription);
            CSVHandler.savePrescriptions(PRESCRIPTIONS_FILE, prescriptions);
        }
    }
    
    public void deletePrescription(int index) {
        if (index >= 0 && index < prescriptions.size()) {
            prescriptions.remove(index);
            CSVHandler.savePrescriptions(PRESCRIPTIONS_FILE, prescriptions);
        }
    }
    
    public String generatePrescriptionID() {
        int maxNum = 0;
        for (Prescription p : prescriptions) {
            String id = p.getPrescriptionID().substring(2);
            int num = Integer.parseInt(id);
            if (num > maxNum) maxNum = num;
        }
        return String.format("PR%03d", maxNum + 1);
    }
    
    // ========== REFERRAL OPERATIONS (Using Singleton) ==========
    
    public void createReferral(String patientID, String patientName,
        String fromClinicianID, String toClinicianID, 
        String reason) {
        Referral referralManager = Referral.getInstance();
        referralManager.addReferral(patientID, patientName, fromClinicianID, 
        toClinicianID, reason);
        
        // Generate email file for the latest referral
        List<Referral.ReferralData> referrals = referralManager.getAllReferrals();
        if (!referrals.isEmpty()) {
            referralManager.generateEmailFile(referrals.get(referrals.size() - 1));
        }
    }
    
    public List<Referral.ReferralData> getAllReferrals() {
    return Referral.getInstance().getAllReferrals();
    }
}