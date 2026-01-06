package utils;

import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle CSV file operations
 * Reads and writes data to CSV files
 */
public class CSVHandler {
    
    // Load Patients from CSV
    public static List<Patient> loadPatients(String filepath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    Patient patient = new Patient(
                        data[0].trim(), // PatientID
                        data[1].trim(), // Name
                        Integer.parseInt(data[2].trim()), // Age
                        data[3].trim(), // Gender
                        data[4].trim(), // ContactNumber
                        data[5].trim(), // Address
                        data[6].trim()  // MedicalHistory
                    );
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }
        return patients;
    }
    
    // Save Patients to CSV
    public static void savePatients(String filepath, List<Patient> patients) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("PatientID,Name,Age,Gender,ContactNumber,Address,MedicalHistory\n");
            for (Patient p : patients) {
                writer.write(p.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }
    
    // Load Clinicians from CSV
    public static List<Clinician> loadClinicians(String filepath) {
        List<Clinician> clinicians = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Clinician clinician = new Clinician(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim()
                    );
                    clinicians.add(clinician);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading clinicians: " + e.getMessage());
        }
        return clinicians;
    }
    
    // Save Clinicians to CSV
    public static void saveClinicians(String filepath, List<Clinician> clinicians) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("ClinicianID,Name,Specialization,ContactNumber,Email\n");
            for (Clinician c : clinicians) {
                writer.write(c.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving clinicians: " + e.getMessage());
        }
    }
    
    // Load Appointments from CSV
    public static List<Appointment> loadAppointments(String filepath) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    Appointment appointment = new Appointment(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim()
                    );
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
        return appointments;
    }
    
    // Save Appointments to CSV
    public static void saveAppointments(String filepath, List<Appointment> appointments) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("AppointmentID,PatientID,ClinicianID,Date,Time,Status\n");
            for (Appointment a : appointments) {
                writer.write(a.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }
    
    // Load Prescriptions from CSV
    public static List<Prescription> loadPrescriptions(String filepath) {
        List<Prescription> prescriptions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    Prescription prescription = new Prescription(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim()
                    );
                    prescriptions.add(prescription);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
        }
        return prescriptions;
    }
    
    // Save Prescriptions to CSV
    public static void savePrescriptions(String filepath, List<Prescription> prescriptions) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("PrescriptionID,PatientID,ClinicianID,Medication,Dosage,Date\n");
            for (Prescription p : prescriptions) {
                writer.write(p.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving prescriptions: " + e.getMessage());
        }
    }
}