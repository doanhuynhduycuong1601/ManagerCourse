
package poly.com.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.com.SQL.jbdcHelper;
import poly.com.entity.Course;

public class CourseDAO extends EdusysDAO<Course, Integer>{

    String insert_SQL = "INSERT INTO Course (STaffID, MACD, Fee, Times, OpenDate, Note) VALUES (?, ?, ?, ?, ?, ?)";
    String update_SQL = "UPDATE Course SET OpenDate= ?, Note= ? WHERE CourseID= ?";
    String delete_SQL = "DELETE FROM Course WHERE CourseID= ?";
    String select_ALL_SQL = "SELECT * FROM Course";
    String select_BY_ID_SQL = "SELECT * FROM Course WHERE CourseID= ?";
    
    @Override
    public void insert(Course entity) {
        jbdcHelper.update(insert_SQL, entity.getStaffID(), entity.getMaCD(), entity.getFee(), entity.getTimes(), entity.getOpenDate(), entity.getNote());
    }

    @Override
    public void update(Course entity) {
        jbdcHelper.update(update_SQL, entity.getOpenDate(), entity.getNote(), entity.getCourseID());
    }

    @Override
    public void delete(Integer id) {
        jbdcHelper.update(delete_SQL, id);
    }

    @Override
    public Course selectById(Integer id) {
        List<Course> list = this.selectBYSql(select_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Course> selectAll() {
        return this.selectBYSql(select_ALL_SQL);
    }

    @Override
    protected List<Course> selectBYSql(String sql, Object... args) {
        List<Course> list = new ArrayList<>();
        try {
            ResultSet rs = jbdcHelper.query(sql, args);
            while(rs.next()){
                list.add(new Course(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Course> selectByChuyenDe(String idCD){
        String sql = "Select * from Course where MACD=?";
        return this.selectBYSql(sql, idCD);
    }
    
    
    public List<Integer> selectYear(){
        String sql = "SELECT DISTINCT YEAR(OpenDate) FROM Course ORDER BY YEAR(OpenDate) desc";
        List<Integer> list = new ArrayList<>();
        try{
            ResultSet rs = jbdcHelper.query(sql);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            return list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}