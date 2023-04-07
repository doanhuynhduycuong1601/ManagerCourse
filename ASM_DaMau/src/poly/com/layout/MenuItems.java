
package poly.com.layout;

import javax.swing.JMenuItem;

public class MenuItems {
    public JMenuItem menuItem;
    private String name;
    private Object []properties;

    public MenuItems(String name, Object[] properties) {
        this.name = name;
        this.properties = properties;
        menuItem = new JMenuItem((String)name);
    }

    public Object[] getProperties() {
        return properties;
    }

    public void setProperties(Object[] properties) {
        this.properties = properties;
    }
    
    
}
