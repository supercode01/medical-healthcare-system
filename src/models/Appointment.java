package models;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String clinicianID;
    private String date;
    private String time;
    private String status;

    // Constructor
    public Appointment(String appointmentID, String patientID, String clinicianID,
                       String date, String time, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Getters and Setters
    public String getAppointmentID() { return appointmentID; }
    public void setAppointmentID(String appointmentID) { this.appointmentID = appointmentID; }

    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public String getClinicianID() { return clinicianID; }
    public void setClinicianID(String clinicianID) { this.clinicianID = clinicianID; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Convert to CSV format
    public String toCSV() {
        return appointmentID + "," + patientID + "," + clinicianID + "," + 
               date + "," + time + "," + status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "ID='" + appointmentID + '\'' +
                ", PatientID='" + patientID + '\'' +
                ", ClinicianID='" + clinicianID + '\'' +
                ", Date='" + date + '\'' +
                ", Time='" + time + '\'' +
                ", Status='" + status + '\'' +
                '}';
    }
}

