package models;

public class Prescription {
    private String prescriptionID;
    private String patientID;
    private String clinicianID;
    private String medication;
    private String dosage;
    private String date;

    // Constructor
    public Prescription(String prescriptionID, String patientID, String clinicianID,
                        String medication, String dosage, String date) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.medication = medication;
        this.dosage = dosage;
        this.date = date;
    }

    // Getters and Setters
    public String getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(String prescriptionID) { this.prescriptionID = prescriptionID; }

    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public String getClinicianID() { return clinicianID; }
    public void setClinicianID(String clinicianID) { this.clinicianID = clinicianID; }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    // Convert to CSV format
    public String toCSV() {
        return prescriptionID + "," + patientID + "," + clinicianID + "," + 
               medication + "," + dosage + "," + date;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "ID='" + prescriptionID + '\'' +
                ", PatientID='" + patientID + '\'' +
                ", ClinicianID='" + clinicianID + '\'' +
                ", Medication='" + medication + '\'' +
                ", Dosage='" + dosage + '\'' +
                ", Date='" + date + '\'' +
                '}';
    }
}

