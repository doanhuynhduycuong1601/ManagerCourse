package poly.com.layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class Chk {

    JCheckBox chk;

    public Chk() {
        chk = new JCheckBox();
    }

    public JCheckBox chkActionPass(JPasswordField txt) {
        chk.setBackground(txt.getBackground());
        chk.setFocusable(false);
        chk.setFocusPainted(false);
        chk.setBorder(null);
        chk.setOpaque(false);
        char x = ((JPasswordField) txt).getEchoChar();
        chk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chk.isSelected()) {
                    ((JPasswordField) txt).setEchoChar((char) 0);
                } else {
                    ((JPasswordField) txt).setEchoChar(x);
                }
            }
        });
        return chk;
    }
}
