/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.layout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Duy Cuong
 */
public class Buttons {
    private String name;
    private Object []obj;
    public JButton btn = new JButton();
    public Buttons(String name) {
        this.name = name;
    }

    public Buttons(String name, Object[] obj) {
        this.name = name;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getObj() {
        return obj;
    }

    public void setObj(Object[] obj) {
        this.obj = obj;
    }
    
    public void setProperties(int fontSize, Color color, ActionListener act){
        btn.setFont(new Font("Aria",Font.BOLD,fontSize));
        btn.setForeground(color);
        btn.addActionListener(act);
    }
}
