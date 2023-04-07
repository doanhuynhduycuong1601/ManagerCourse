
package poly.com.utlis;

import java.awt.Color;
import java.awt.Component;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import poly.com.layout.FormInput;
import poly.com.layout.FormInput;

public class Msg {
    public static void alert(Component parent, Object message){
        JOptionPane.showMessageDialog(parent, message, "Hệ thống quản lý đào tạo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean confirm(Component parent, String message){
        int result = JOptionPane.showConfirmDialog(parent, message, "hệ thống quản lý đào tạo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    public static String prompt(Component parent, String msg){
        return JOptionPane.showInputDialog(parent, msg, "Hệ thống quản lý đào tạo",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void loi(FormInput form, String msg) {
        form.txt.setBackground(Color.YELLOW);
        form.lblError.setText(msg);
    }
    
    public static void print(JTable table,String header,String footer){
        MessageFormat h = new MessageFormat(header);
        MessageFormat f = new MessageFormat(footer);
        try {
            table.print(JTable.PrintMode.NORMAL, h, f);
          
        } catch (PrinterException e) {
            Msg.alert(null, table + " cann not print");
        }
    }
}
