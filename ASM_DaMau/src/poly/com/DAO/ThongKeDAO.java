
package poly.com.DAO;

import java.util.ArrayList;
import java.util.List;
import poly.com.SQL.jbdcHelper;
import java.sql.ResultSet;

public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql, String[] cols, Object ...args){
        try{
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = jbdcHelper.query(sql, args);
            while(rs.next()){
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public List<Object[]> getBangDiem(Integer makh){
        String sql = "{CALL sp_GradeTable(?)}";
        String [] cols = {"MANH","Names","Grade"};
        return this.getListOfArray(sql, cols, makh);
    }
    
    public List<Object[]> getLuongNguoiHoc(){
        String sql = "{CALL SP_LuongNguoiHoc}";
        String [] cols = {"years","quantity","firstt","lastt"};
        return this.getListOfArray(sql, cols);
    }
    
    public List<Object[]> getDiemChuyenDe(){
        String sql = "{CALL sp_GradeCD}";
        String [] cols = {"CHUYENDE","SOHV","ThapNhat","CaoNhat","TrungBinh"};
        return this.getListOfArray(sql, cols);
    }
    
    public List<Object[]> getDoanhThu(int nam){
        String sql = "{CALL sp_Turnover (?)}";
        String [] cols = {"ChuyenDe","SoHV","Turnover","ThapNhat","CaoNhat","TrungBinh"};
        return this.getListOfArray(sql, cols, nam);
    }
    
}
