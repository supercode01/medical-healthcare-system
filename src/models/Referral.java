package models;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern Implementation for Referral Management
 * Ensures only one instance handles all referrals
 */
public class Referral {
    // Singleton instance
    private static Referral instance;
    
    // Referral queue
    private List<ReferralData> referralQueue;
    
    // Private constructor (Singleton pattern)
    private Referral() {
        referralQueue = new ArrayList<>();
    }
    
    // Get singleton instance
    public static Referral getInstance() {
        if (instance == null) {
            synchronized (Referral.class) {
                if (instance == null) {
                    instance = new Referral();
                }
            }
        }
        return instance;
    }
    
    // Add referral to queue
    public void addReferral(String patientID, String patientName, 
                           String fromClinicianID, String toClinicianID,
                           String reason) {
        ReferralData referral = new ReferralData(
            generateReferralID(),
            patientID,
            patientName,
            fromClinicianID,
            toClinicianID,
            reason,
            LocalDateTime.now()
        );
        referralQueue.add(referral);
    }
    
    // Generate email file
    public boolean generateEmailFile(ReferralData referral) {
        String filename = "output/referral_email_" + referral.referralID + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(generateEmailContent(referral));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Generate email content
    private String generateEmailContent(ReferralData referral) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder email = new StringBuilder();
        
        email.append("===============================================\n");
        email.append("         MEDICAL REFERRAL NOTIFICATION\n");
        email.append("===============================================\n\n");
        
        email.append("Referral ID: ").append(referral.referralID).append("\n");
        email.append("Date & Time: ").append(referral.timestamp.format(formatter)).append("\n\n");
        
        email.append("TO: Clinician ").append(referral.toClinicianID).append("\n");
        email.append("FROM: Clinician ").append(referral.fromClinicianID).append("\n\n");
        
        email.append("PATIENT INFORMATION:\n");
        email.append("- Patient ID: ").append(referral.patientID).append("\n");
        email.append("- Patient Name: ").append(referral.patientName).append("\n\n");
        
        email.append("REFERRAL REASON:\n");
        email.append(referral.reason).append("\n\n");
        
        email.append("Please review this referral and schedule an appointment at your earliest convenience.\n\n");
        email.append("===============================================\n");
        
        return email.toString();
    }
    
    // Generate unique referral ID
    private String generateReferralID() {
        return "REF" + String.format("%04d", referralQueue.size() + 1);
    }
    
    // Get all referrals
    public List<ReferralData> getAllReferrals() {
        return new ArrayList<>(referralQueue);
    }
    
    // Inner class for referral data
    public static class ReferralData {
        public String referralID;
        public String patientID;
        public String patientName;
        public String fromClinicianID;
        public String toClinicianID;
        public String reason;
        public LocalDateTime timestamp;
        
        public ReferralData(String referralID, String patientID, String patientName,
                           String fromClinicianID, String toClinicianID, 
                           String reason, LocalDateTime timestamp) {
            this.referralID = referralID;
            this.patientID = patientID;
            this.patientName = patientName;
            this.fromClinicianID = fromClinicianID;
            this.toClinicianID = toClinicianID;
            this.reason = reason;
            this.timestamp = timestamp;
        }
        
        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return referralID + " | Patient: " + patientName + " | " + 
                   fromClinicianID + " → " + toClinicianID + " | " + 
                   timestamp.format(formatter);
        }
    }
}