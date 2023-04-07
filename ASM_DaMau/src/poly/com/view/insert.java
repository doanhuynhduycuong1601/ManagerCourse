
package poly.com.view;

import javax.swing.JOptionPane;
import poly.com.DAO.CourseDAO;
import poly.com.DAO.LearnerDAO;
import poly.com.DAO.StaffDAO;
import poly.com.entity.Course;
import poly.com.entity.Learner;
import poly.com.entity.Staff;

public class insert {
    public static void main(String[] args) {
//        ChuyenDeDAO dao = new ChuyenDeDAO();
//        StaffDAO dao  = new StaffDAO();
//        LearnerDAO dao = new LearnerDAO();
        CourseDAO dao = new CourseDAO();
        for(int i=0 ;i <= 10 ;i ++){
//            dao.insert(new ChuyenDe("Cd"+i, "Cd"+i, 1201, 12, "diacua.jpg", "Cd"+i));
//            dao.update(new ChuyenDe("Cd"+i, "Cd"+i, 1201, 12, "1.jpg", "Cd"+i));
//            dao.insert(new Staff("Nv"+i, "1234", "Nv"+i, ((i%2) == 0)));
//            dao.delete("Nv"+i);
//            System.out.println((i%2) == 0);
//                dao.insert(new Learner("Nh"+i, "Nh"+i, i%2==0, "2003-01-16", "@gmail.com", "0987654321", "Nh"+i, "Nv1"));
//                dao.update(new Learner("Nh"+i, "Nh"+i, i%2==0, "2003-01-16", "Nh"+i+"@gmail.com", "0987654321", "Nh"+i, "Nv1"));
                dao.insert(new Course("Nv1", "Cd158", 1691, 15, "2024-10-30", "Cd158"));
        }
    }
}
