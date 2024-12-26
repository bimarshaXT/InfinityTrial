package com.infinitytrailapp.model;

/**
 *
 * @author Bimarsha
 */
public class CandidateModel {
    private int CandidateNo;
    private String Name;
    private String Contact;
    private String Category;
    private String Type;
    private String CitizenshipNo;
    private String DateOfBirth;

    // Default Constructor
    public CandidateModel() {
    }

    // Parameterized Constructor
    // Updated Parameterized Constructor
   public CandidateModel(int CandidateNo, String Name, String Contact, String Category, String Type, String CitizenshipNo, String DateOfBirth) {
    this.CandidateNo = CandidateNo;
    this.Name = Name;
    this.Contact = Contact;
    this.Category = Category;
    this.Type = Type;
    this.CitizenshipNo = CitizenshipNo; // Added
    this.DateOfBirth = DateOfBirth;     // Added
}

    // Getter and Setter for CandidateNo
    public int getCandidateNo() {
        return CandidateNo;
    }

    public void setCandidateNo(int CandidateNo) {
        this.CandidateNo = CandidateNo;
    }

    // Getter and Setter for Name
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    // Getter and Setter for Contact
    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    // Getter and Setter for Category
    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    // Getter and Setter for Type
    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getCitizenshipNo() {
        return CitizenshipNo;
    }

    public void setCitizenshipNo(String CitizenshipNo) {
        this.CitizenshipNo = CitizenshipNo;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }
}


