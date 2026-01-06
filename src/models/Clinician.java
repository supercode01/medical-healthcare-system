package models;

public class Clinician {
    private String clinicianID;
    private String name;
    private String specialization;
    private String contactNumber;
    private String email;

    // Constructor
    public Clinician(String clinicianID, String name, String specialization, 
                     String contactNumber, String email) {
        this.clinicianID = clinicianID;
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getClinicianID() { return clinicianID; }
    public void setClinicianID(String clinicianID) { this.clinicianID = clinicianID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Convert to CSV format
    public String toCSV() {
        return clinicianID + "," + name + "," + specialization + "," + 
               contactNumber + "," + email;
    }

    @Override
    public String toString() {
        return "Clinician{" +
                "ID='" + clinicianID + '\'' +
                ", Name='" + name + '\'' +
                ", Specialization='" + specialization + '\'' +
                ", Contact='" + contactNumber + '\'' +
                '}';
    }
}

