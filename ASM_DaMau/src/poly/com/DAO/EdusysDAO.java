
package poly.com.DAO;

import java.util.List;

abstract public class EdusysDAO <EntityType,KeyType> {
    abstract public void insert(EntityType entity);
    abstract public void update(EntityType entity);
    abstract public void delete(KeyType id);
    abstract public EntityType selectById(KeyType id);
    abstract public List<EntityType> selectAll();
    abstract protected List<EntityType> selectBYSql(String sql,Object ... args);
            
}
