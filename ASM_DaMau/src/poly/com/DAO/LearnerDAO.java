/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.com.SQL.jbdcHelper;
import poly.com.entity.Learner;

public class LearnerDAO extends EdusysDAO<Learner, String>{
    String insert_SQL = "INSERT INTO NGUOIHOC (MANH, STaffID, Names, GENDER, DateBorn, Email, Phone, Note) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String update_SQL = "UPDATE NGUOIHOC SET  STaffID = ?, Names= ?, GENDER= ?, DateBorn= ?, Email= ?, Phone=?, Note = ? WHERE MANH= ?";
    String delete_SQL = "DELETE FROM NGUOIHOC WHERE MANH = ?";
    String select_ALL_SQL = "SELECT * FROM NGUOIHOC";
    String select_BY_ID_SQL = "SELECT * FROM NGUOIHOC WHERE MANH = ?";
    
    @Override
    public void insert(Learner entity) {
        jbdcHelper.update(insert_SQL,entity.getIdNH(),entity.getStaffID(),entity.getNames(),entity.isGender(),entity.getDateBorn(),entity.getEmail(),entity.getPhone(),entity.getNote());
    }

    @Override
    public void update(Learner entity) {
        jbdcHelper.update(update_SQL,entity.getStaffID(),entity.getNames(),entity.isGender(),entity.getDateBorn(),entity.getEmail(),entity.getPhone(),entity.getNote(),entity.getIdNH());
    }

    @Override
    public void delete(String id) {
        jbdcHelper.update(delete_SQL, id);
    }

    @Override
    public Learner selectById(String id) {
        List<Learner> list = this.selectBYSql(select_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Learner> selectAll() {
        return this.selectBYSql(select_ALL_SQL);
    }
    
    public List<Learner> selectNotlnCourse(int makh, String keyword){
        String sql = "select * from NGUOIHOC "
                +"where Names like ? and "
                +"MANH not in (select MANH from HOCVIEN where CourseID = ?)";
        return this.selectBYSql(sql, "%"+keyword+"%",makh);
    }

    @Override
    protected List<Learner> selectBYSql(String sql, Object... args) {
        List<Learner> list = new ArrayList<>();
        try {
            ResultSet rs = jbdcHelper.query(sql, args);
            while(rs.next()){
                list.add(new Learner(rs.getString(1), rs.getString(3), rs.getBoolean(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(2), rs.getString(9)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
