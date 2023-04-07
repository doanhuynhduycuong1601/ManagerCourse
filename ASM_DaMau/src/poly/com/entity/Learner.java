
package poly.com.entity;

public class Learner {
    private String idNH;
    private String names;
    private boolean gender;
    private String dateBorn;
    private String email;
    private String phone;
    private String note;
    private String staffID;
    private String registerDate;

    public Learner() {
    }

    public Learner(String idNH, String names, boolean gender, String dateBorn, String email, String phone, String note, String staffID) {
        this.idNH = idNH;
        this.names = names;
        this.gender = gender;
        this.dateBorn = dateBorn;
        this.email = email;
        this.phone = phone;
        this.note = note;
        this.staffID = staffID;
    }

    public Learner(String idNH, String names, boolean gender, String dateBorn, String email, String phone, String note, String staffID, String registerDate) {
        this.idNH = idNH;
        this.names = names;
        this.gender = gender;
        this.dateBorn = dateBorn;
        this.email = email;
        this.phone = phone;
        this.note = note;
        this.staffID = staffID;
        this.registerDate = registerDate;
    }
    
    

    public String getIdNH() {
        return idNH;
    }

    public void setIdNH(String idNH) {
        this.idNH = idNH;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(String dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    
    
    
}
