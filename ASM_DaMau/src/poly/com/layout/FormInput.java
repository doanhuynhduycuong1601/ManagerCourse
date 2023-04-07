
package poly.com.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

public class FormInput {
    public JLabel lbl;
    public JLabel lblError = new JLabel(" ");
    public JPanel pnl;
    public JRadioButton[] rdo;
    private Object []nameRdo;
    public JTextComponent txt;
    private Object name;
    private Object properties[];

    public FormInput(JTextComponent txt, Object name) {
        this.txt = txt;
        this.name = name;
        borderLayoutTxt(this.txt, this.name);
    }

    public FormInput(JTextComponent txt, Object name, Object[] properties) {
        this.txt = txt;
        this.name = name;
        this.properties = properties;
        borderLayoutTxt(this.txt, this.name);
    }

    public Object[] getProperties() {
        return properties;
    }

    public void setProperties(Object[] properties) {
        this.properties = properties;
    }
    
    
    

    public FormInput(Object [] nameRdo, Object name) {
        this.nameRdo = nameRdo;
        this.name = name;
        borderLayoutRdo(this.nameRdo, this.name);
    }
    
    public FormInput(Object [] nameRdo, Object name, Object[] properties) {
        this.nameRdo = nameRdo;
        this.name = name;
        this.properties = properties;
        borderLayoutRdo(this.nameRdo, this.name);
    }
    
    public void setLabelError(int fontSize, Color color){
        lblError.setFont(new Font("Aria", Font.BOLD, fontSize));
        lblError.setForeground(color);
        lblError.setOpaque(true);
    }

    private JPanel labelName(String name) {
        JPanel pnl = new JPanel(new BorderLayout());
        lbl = new JLabel(name);
        pnl.add(lbl);
        return pnl;
    }
    
    private JPanel input(Component com) {
        JPanel pnl = new JPanel(new BorderLayout(0, 1));
        JScrollPane scroll = new JScrollPane(com);
        pnl.add(scroll, BorderLayout.CENTER);
        pnl.add(lblError, BorderLayout.SOUTH);
        return pnl;
    }
    
    private void borderLayoutTxt(JTextComponent txt, Object name){
        pnl = new JPanel(new BorderLayout(0, 4));
        pnl.add(labelName((String)name),BorderLayout.NORTH);
        pnl.add(input(txt),BorderLayout.CENTER);
    }
    
    private JPanel rdoButton(Object []name) {
        ButtonGroup group = new ButtonGroup();
        rdo = new JRadioButton[name.length];
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        for (int i = 0; i < name.length; i++) {
            rdo[i] = new JRadioButton((String) name[i]);
            group.add(rdo[i]);
            pnl.add(rdo[i]);
        }
        return pnl;
    }
    
    private void borderLayoutRdo(Object[] rdo, Object name){
        pnl = new JPanel(new BorderLayout(0, 5));
        pnl.add(labelName((String)name),BorderLayout.NORTH);
        pnl.add(rdoButton(rdo),BorderLayout.CENTER);
    }
    
    public void backGround(Color color) {
        txt.setBackground(color);
    }
    
    public void lineBorder(Color color){
        txt.setBorder(new LineBorder(color));
    }
    
    public String getText(){
        return txt.getText();
    }
    
    public void setText(Object obj){
        txt.setText(String.valueOf(obj));
    }
    
    public void setTextLbl(Object obj){
        lblError.setText(String.valueOf(obj));
    }
}
