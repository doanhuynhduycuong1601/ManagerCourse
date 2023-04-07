
package poly.com.entity;

public class Course {
    private int courseID;
    private String staffID;
    private String maCD;
    private double fee;
    private int times;
    private String openDate;
    private String note;
    private String createDate;

    public Course() {
    }

    public Course(int courseID, String staffID, String maCD, double fee, int times, String openDate, String note) {
        this.courseID = courseID;
        this.staffID = staffID;
        this.maCD = maCD;
        this.fee = fee;
        this.times = times;
        this.openDate = openDate;
        this.note = note;
    }

    public Course(String staffID, String maCD, double fee, int times, String openDate, String note) {
        this.staffID = staffID;
        this.maCD = maCD;
        this.fee = fee;
        this.times = times;
        this.openDate = openDate;
        this.note = note;
    }
    
    

    public Course(int courseID, String staffID, String maCD, double fee, int times, String openDate, String note, String createDate) {
        this.courseID = courseID;
        this.staffID = staffID;
        this.maCD = maCD;
        this.fee = fee;
        this.times = times;
        this.openDate = openDate;
        this.note = note;
        this.createDate = createDate;
    }

    
    
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return String.valueOf(courseID);
    }
}
