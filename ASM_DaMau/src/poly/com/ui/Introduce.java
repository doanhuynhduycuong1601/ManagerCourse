package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import poly.com.utlis.XImage;

public class Introduce extends JDialog {

    public Introduce(Frame owner, boolean modal) {
        super(owner, modal);
        init();
        setUndecorated(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
            
        });
    }

    

    private void init() {
        int width_Frame = 380;
        int height_Frame = 250;
        setLayout(null);
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        int y = 5;
        int x = 5;
        int width_frame_thuc = width_Frame;
        int height_frame_thuc = height_Frame;

        JLabel lbl = new JLabel();
        lbl.setBounds(x, y, (int) (width_frame_thuc * 0.4), height_frame_thuc);
        lbl.setIcon(XImage.reSizeImgae("/image/ongPoly.png", lbl));
        add(lbl);

        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setBounds(x + (int) (width_frame_thuc * 0.4), y, (int) (width_frame_thuc * 0.6), height_frame_thuc);
        lbl = new JLabel("Thông tin chi tiết");
        lbl.setForeground(Color.red);
        lbl.setFont(new Font("Aria", Font.BOLD, 20));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        pnl.add(lbl, BorderLayout.NORTH);
//        "Full Name : Đoàn Huỳnh Duy Cương\n"
//        "Class : IT17320\n"
//        "Teacher : Lê Anh Tú\n"
//        "Fpt School is supper vipro"
        List<String> listText = new ArrayList<>();
        listText.add("Full Name : Đoàn Huỳnh Duy Cương");
        listText.add("Class : IT17320");
        listText.add("Teacher : Lê Anh Tú");
        listText.add("Fpt School is supper vipro");
        JPanel pnlTT = new JPanel(new GridLayout(4, 0, 0, 10));
        listText.forEach(n -> {
            JLabel lbls = new JLabel(n);
            pnlTT.add(lbls);
        });
        pnl.add(pnlTT, BorderLayout.CENTER);
        add(pnl);
    }
}
