package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import poly.com.DAO.StaffDAO;
import poly.com.entity.Staff;
import poly.com.layout.Chk;
import poly.com.layout.FormInput;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.Valid;
import poly.com.utlis.XImage;

public class Login extends JDialog implements ActionListener {

    JButton btnLogin, btnEnd;
    final int user = 0;
    final int pass = 1;
    public Login(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }

    int width_Frame = 400;
    int height_Frame = 230;

    private void init() {
        setLayout(new BorderLayout(5, 0));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);
        this.setIconImage(XImage.getApplcon("/image/iconFPT.png"));

        lblImageLogin();
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.add(panelLogin(), BorderLayout.CENTER);
        pnl.add(panelButton(), BorderLayout.SOUTH);
        add(pnl, BorderLayout.CENTER);

        if (Auth.user != null) {
            hashForm.get(0).setText(Auth.user.getIdNV());
            hashForm.get(1).setText(Auth.user.getPass());
            Auth.clear();
        }
        Chk chk = new Chk();
        hashForm.get(1).txt.add(chk.chkActionPass((JPasswordField)hashForm.get(1).txt));

    }

    private void lblImageLogin() {
        JLabel lblImage = new JLabel();
        int width = 150;
        int height = height_Frame;
        lblImage.setPreferredSize(new Dimension(width, height));
        lblImage.setSize(new Dimension(width, height));
        lblImage.setIcon(XImage.reSizeImgae("/image/ongPoly.png", lblImage));
        add(lblImage, BorderLayout.WEST);
    }

    HashMap<Integer, FormInput> hashForm = new HashMap<>();

    private JPanel panelLogin() {
        hashForm.put(user,new FormInput(new JTextField(), "User"));
        hashForm.put(pass,new FormInput(new JPasswordField(), "pass"));
        JPanel pnl = new JPanel(new GridLayout(2, 1, 0, 15));
        hashForm.forEach((key,nut) -> {
            nut.setLabelError(9, Color.red);
            pnl.add(nut.pnl);
        });
        return pnl;
    }

    private JPanel panelButton() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnLogin = new JButton("Login", XImage.getAppImagelcon("/image/key.png"));
        btnLogin.addActionListener(this);
        btnEnd = new JButton("End", XImage.getAppImagelcon("/image/openDoor.png"));
        btnEnd.addActionListener(this);
        pnl.add(btnLogin);
        pnl.add(btnEnd);
        return pnl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String x = e.getActionCommand();
        switch (x) {
            case "Login" ->
                login();
            case "End" ->
                end();
        }
    }

    public void end() {
        dispose();
    }

    void login() {
        if (!checkData()) {
            return;
        }
        StaffDAO dao = new StaffDAO();
        String idNV = hashForm.get(user).getText();
        String pass1 = hashForm.get(this.pass).getText();
        String select_BY_UserPass_SQL = "SELECT * FROM NHANVIEN WHERE STaffID = ? and PassWords = ?";
        Staff nv = dao.selectOneStaff(select_BY_UserPass_SQL,idNV, pass1);
        if (nv == null) {
            Msg.loi(hashForm.get(user), "User or pass wrong");
            Msg.loi(hashForm.get(this.pass), "User or pass wrong");
        } else {
            Auth.user = nv;
            this.dispose();
        }
    }

    public boolean checkData() {
        Valid vl = new Valid();
        vl.isEmpty(hashForm.get(user), "User");
        vl.isEmpty(hashForm.get(pass), "pass");
        return vl.getLoi().equals("");
    }
}
