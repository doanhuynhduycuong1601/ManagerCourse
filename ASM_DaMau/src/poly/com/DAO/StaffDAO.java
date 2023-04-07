package poly.com.DAO;

import java.util.ArrayList;
import java.util.List;
import poly.com.SQL.jbdcHelper;
import poly.com.entity.Staff;
import java.sql.ResultSet;

public class StaffDAO extends EdusysDAO<Staff, String> {

    String insert_SQL = "INSERT INTO NHANVIEN (STaffID, PassWords, NAMES, ROLES) VALUES (?, ?, ?, ?)";
    String update_SQL = "UPDATE NHANVIEN SET PassWords = ?, NAMES = ?, ROLES = ? WHERE STaffID = ?";
    String delete_SQL = "DELETE FROM NHANVIEN WHERE STaffID = ?";
    String select_ALL_SQL = "SELECT * FROM NHANVIEN";
    String select_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE STaffID = ?";

    @Override
    public void insert(Staff entity) {
        jbdcHelper.update(insert_SQL, entity.getIdNV(), entity.getPass(), entity.getName(), entity.isRole());
    }

    @Override
    public void update(Staff entity) {
        jbdcHelper.update(update_SQL, entity.getPass(), entity.getName(), entity.isRole(), entity.getIdNV());
    }

    @Override
    public void delete(String id) {
        jbdcHelper.update(delete_SQL, id);
    }

    @Override
    public Staff selectById(String id) {
        List<Staff> list = this.selectBYSql(select_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Staff> selectAll() {
        return this.selectBYSql(select_ALL_SQL);
    }

    @Override
    protected List<Staff> selectBYSql(String sql, Object... args) {
        List<Staff> list = new ArrayList<>();
        try {
            ResultSet rs = jbdcHelper.query(sql, args);
            while(rs.next()){
                Staff nv = new Staff(rs.getString("STaffID"), rs.getString("PassWords"), rs.getString("NAMES"), rs.getBoolean("ROLES"));
                list.add(nv);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public Staff selectOneStaff(String sql,Object ... args) {
        List<Staff> list = this.selectBYSql(sql,args);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

}
