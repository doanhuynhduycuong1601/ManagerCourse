/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.com.SQL.jbdcHelper;
import poly.com.entity.ChuyenDe;

/**
 *
 * @author Duy Cuong
 */
public class ChuyenDeDAO extends EdusysDAO<ChuyenDe, String>{
    String insert_SQL = "INSERT INTO CHUYENDE (MACD, NAMECD, Fee,Times, Images,Describe) VALUES (?, ?, ?, ?, ?, ?)";
    String update_SQL = "UPDATE CHUYENDE SET  NAMECD= ?, Fee= ?, Times= ?, Images= ?, Describe=? WHERE MACD = ?";
    String delete_SQL = "DELETE FROM CHUYENDE WHERE MACD = ?";
    String select_ALL_SQL = "SELECT * FROM CHUYENDE";
    String select_BY_ID_SQL = "SELECT * FROM CHUYENDE WHERE MACD = ?";
    
    @Override
    public void insert(ChuyenDe entity) {
        jbdcHelper.update(insert_SQL, entity.getMaCD(), entity.getNameCD(), entity.getFee(), entity.getTimes(),entity.getImages(),entity.getDescribe());
    }

    @Override
    public void update(ChuyenDe entity) {
        jbdcHelper.update(update_SQL, entity.getNameCD(), entity.getFee(), entity.getTimes(),entity.getImages(),entity.getDescribe(), entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        jbdcHelper.update(delete_SQL, id);
    }

    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBYSql(select_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBYSql(select_ALL_SQL);
    }

    @Override
    protected List<ChuyenDe> selectBYSql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = jbdcHelper.query(sql, args);
            while(rs.next()){
                ChuyenDe cd = new ChuyenDe(rs.getString("MACD"),rs.getString("NAMECD"),rs.getDouble("Fee"),rs.getInt("Times"),rs.getString("Images"),rs.getString("Describe"));
                list.add(cd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
