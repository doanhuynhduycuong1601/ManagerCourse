
package poly.com.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import poly.com.utlis.XImage;

public class Hello extends JDialog{

    JLabel lblImage;
    JProgressBar pgbLoading;

    public Hello(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }
    
    
    private void init(){
        int width_Frame = 400;
        int height_Frame = 230;
        setLayout(null);
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        lblImage = new JLabel();
        lblImage.setBounds(0, 0, width_Frame, (int)(height_Frame*0.7));
        lblImage.setIcon(XImage.reSizeImgae("/image/iconFPT.png", lblImage));
        
        pgbLoading = new JProgressBar();
        pgbLoading.setMinimum(0);
        pgbLoading.setMaximum(100);
        pgbLoading.setBounds(0, lblImage.getY()+lblImage.getHeight(), width_Frame, height_Frame-lblImage.getHeight()-lblImage.getY());
        pgbLoading.setValue(0);
        pgbLoading.setStringPainted(true);
        
        
        add(pgbLoading);
        add(lblImage);
        new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rd = new Random();
                int value = pgbLoading.getValue();
                if(value >= pgbLoading.getMaximum()){
                    dispose();
                }
                pgbLoading.setValue(value + rd.nextInt(5));
            }
        }).start();
    }
}
