/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.com.SQL.jbdcHelper;
import poly.com.entity.HocVien;

public class HocVienDAO extends EdusysDAO<HocVien, Integer>{
    String insert_SQL = "INSERT INTO HOCVIEN (MANH, CourseID, Grade) VALUES (?, ?, ?)";
    String update_SQL = "UPDATE HOCVIEN SET Grade= ? WHERE MAHV = ?";
    String delete_SQL = "DELETE FROM HOCVIEN WHERE MAHV = ?";
    String select_ALL_SQL = "SELECT * FROM HOCVIEN";
    String select_BY_ID_SQL = "SELECT * FROM HOCVIEN WHERE MAHV = ?";
   
    @Override
    public void insert(HocVien entity) {
        jbdcHelper.update(insert_SQL,entity.getIdLearner(),entity.getCourseID(),entity.getGrade());
    }

    @Override
    public void update(HocVien entity) {
        jbdcHelper.update(update_SQL, entity.getGrade(), entity.getIdHV());
    }

    @Override
    public void delete(Integer id) {
        jbdcHelper.update(delete_SQL, id);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = this.selectBYSql(select_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBYSql(select_ALL_SQL);
    }

    public List<HocVien> selectByCourse(int maKH){
        String sql = "select * from HOCVIEN where CourseID = ?";
        return this.selectBYSql(sql, maKH);
    }
    
    public List<HocVien> selectByLearner(String maNH){
        String sql = "select * from HOCVIEN where MANH = ?";
        return this.selectBYSql(sql, maNH);
    }
    
    @Override
    protected List<HocVien> selectBYSql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = jbdcHelper.query(sql, args);
            while(rs.next()){
                list.add(new HocVien(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getFloat(4)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
