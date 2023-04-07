package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import poly.com.DAO.StaffDAO;
import poly.com.layout.Buttons;
import poly.com.layout.Chk;
import poly.com.layout.FormInput;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.Valid;

public class ChangePass extends JDialog implements ActionListener {

    public ChangePass(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }

    final int idNV = 0;
    final int pass = 1;
    final int passNew = 2;
    final int conFirm = 3;

    private void init() {
        int width_Frame = 450;
        int height_Frame = 270;
        setLayout(new BorderLayout(10, 20));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("Change Pass");
        lblTitle.setForeground(Color.GREEN);
        lblTitle.setFont(new Font("Aria", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);
        add(pnlForm(), BorderLayout.CENTER);
        add(pnlBtn(), BorderLayout.SOUTH);
        listForm.get(idNV).txt.setEditable(false);
        listForm.get(idNV).setText(Auth.user.getIdNV());
        listForm.get(pass).setText(Auth.user.getPass());
    }

    java.util.List<FormInput> listForm = new ArrayList<>();

    private JPanel pnlForm() {
        JPanel pnl = new JPanel(new GridLayout(2, 2, 10, 10));
        listForm.add(idNV, new FormInput(new JTextField(), "User"));
        listForm.add(pass, new FormInput(new JPasswordField(), "Pass currently"));
        listForm.add(passNew, new FormInput(new JPasswordField() , "Pass new"));
        listForm.add(conFirm, new FormInput(new JPasswordField(), "Confirm pass new"));
        for (int i = 0; i < listForm.size(); i++) {
            if(i != idNV){
                Chk chk = new Chk();
                
                listForm.get(i).txt.setLayout(new FlowLayout(FlowLayout.RIGHT,5 ,2));
                listForm.get(i).txt.add(chk.chkActionPass((JPasswordField)listForm.get(i).txt));
            }
            listForm.get(i).setLabelError(9, Color.red);
            pnl.add(listForm.get(i).pnl);
            
        }
        listForm.get(idNV).txt.setEditable(false);
        
        return pnl;
    }

    java.util.List<Buttons> listBtn = new ArrayList<>();

    private JPanel pnlBtn() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        listBtn.add(new Buttons("Đồng ý"));
        listBtn.add(new Buttons("Hủy bỏ"));
        listBtn.forEach(btn -> {
            btn.btn.setText(btn.getName());
            btn.setProperties(14, Color.BLACK, this);
            btn.btn.setMargin(new Insets(5, 5, 5, 5));
            pnl.add(btn.btn);
        });
        return pnl;
    }

    public boolean checkData() {
        Valid vl = new Valid();
        if (vl.isEmpty(listForm.get(pass), "Pass")) {
            vl.compare(listForm.get(pass), "Pass old wrong", Auth.user.getPass());
        }
        if (vl.isEmpty(listForm.get(passNew), "Pass new")) {
            vl.compare(listForm.get(passNew), "Pass new not equal Confirm", listForm.get(conFirm).getText());
        }
        if (vl.isEmpty(listForm.get(conFirm), "Comfirm")) {
            vl.compare(listForm.get(conFirm), "Pass new not equal Confirm", listForm.get(passNew).getText());
        }
        return vl.getLoi().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String a = e.getActionCommand();
        switch (a) {
            case "Đồng ý" ->
                ok();
            case "Hủy bỏ" ->
                dispose();
        }
    }

    public void ok() {
        if (!checkData()) {
            return;
        }
        StaffDAO dao = new StaffDAO();
        Auth.user.setPass(listForm.get(passNew).getText());
        dao.update(Auth.user);
        Msg.alert(this, "Change pass success");
    }
}
