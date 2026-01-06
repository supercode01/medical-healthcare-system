package models;

public class Patient {
    private String patientID;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String address;
    private String medicalHistory;

    // Constructor
    public Patient(String patientID, String name, int age, String gender, 
                   String contactNumber, String address, String medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.address = address;
        this.medicalHistory = medicalHistory;
    }

    // Getters and Setters
    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    // Convert to CSV format
    public String toCSV() {
        return patientID + "," + name + "," + age + "," + gender + "," + 
               contactNumber + "," + address + "," + medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "ID='" + patientID + '\'' +
                ", Name='" + name + '\'' +
                ", Age=" + age +
                ", Gender='" + gender + '\'' +
                ", Contact='" + contactNumber + '\'' +
                '}';
    }
}