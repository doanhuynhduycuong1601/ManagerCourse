
package poly.com.entity;

public class Staff {
    private String idNV;
    private String pass;
    private String name;
    private boolean role;

    public Staff(String idNV, String pass, String name, boolean role) {
        this.idNV = idNV;
        this.pass = pass;
        this.name = name;
        this.role = role;
    }

    public Staff() {
    }

    public String getIdNV() {
        return idNV;
    }

    public void setIdNV(String idNV) {
        this.idNV = idNV;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
    
    public String getPassAo(){
        String x = idNV.substring(2, idNV.length());
        int i = Integer.parseInt(x);
        if (i/2 == 0) {
            i = 3;
        }else{
            i = 4;
        }
        return this.pass.substring(0, 2)+ sao(pass.length()-2) + sao(i);
    }
    
    public String sao(int sao){
        String x = "";
        for (int i = 0; i < sao; i++) {
            x += '*';  
        }
        return x;
    }

    
    
}
