
package poly.com.entity;

public class HocVien {
    private int idHV;
    private String idLearner;
    private int courseID;
    private float grade;

    public HocVien() {
    }

    public HocVien(int idHV, float grade) {
        this.idHV = idHV;
        this.grade = grade;
    }

    
    public HocVien(String idLearner, int courseID, float grade) {
        this.idLearner = idLearner;
        this.courseID = courseID;
        this.grade = grade;
    }

    
    public HocVien(int idHV, String idLearner, int courseID, float grade) {
        this.idHV = idHV;
        this.idLearner = idLearner;
        this.courseID = courseID;
        this.grade = grade;
    }

    public int getIdHV() {
        return idHV;
    }

    public void setIdHV(int idHV) {
        this.idHV = idHV;
    }

    public String getIdLearner() {
        return idLearner;
    }

    public void setIdLearner(String idLearner) {
        this.idLearner = idLearner;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
    
    
}
