package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import poly.com.DAO.StaffDAO;
import poly.com.entity.Staff;
import poly.com.layout.Buttons;
import poly.com.layout.Chk;
import poly.com.layout.FormInput;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.Valid;
import poly.com.utlis.XImage;

public class ManagerNV extends JDialog implements ActionListener {

    public ManagerNV(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }
    final int idNV = 0;
    final int pass = 1;
    final int confirmPass = 2;
    final int name = 3;
    final int rdo = 4;
    final int btnAdd = 0;
    final int btnUpdate = 1;
    final int btnDelete = 2;
    final int btnNew = 3;
    final int btnFirst = 0;
    final int btnPrev = 1;
    final int btnNext = 2;
    final int btnLast = 3;
    List<Staff> list = new ArrayList<>();
    int width_Frame = 600;
    int height_Frame = 500;

    private void init() {
        setLayout(new BorderLayout(0, 10));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("Quản lý nhân viên quản trị");
        add(lblTitle, BorderLayout.NORTH);

        add(tabbedPane(), BorderLayout.CENTER);
        fillTable();
        if (Auth.isManager()) {
            Chk chk = new Chk();
            hashForm.get(pass).txt.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
            hashForm.get(pass).txt.add(chk.chkActionPass((JPasswordField)hashForm.get(pass).txt));
        }
        Chk chk = new Chk();
        hashForm.get(confirmPass).txt.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        hashForm.get(confirmPass).txt.add(chk.chkActionPass((JPasswordField)hashForm.get(confirmPass).txt));
        hashTMSX.get(btnDelete).btn.setEnabled(false);
        updateStatus();
    }

    JTabbedPane tbp;

    private JTabbedPane tabbedPane() {
        tbp = new JTabbedPane();
        tbp.add("Update", pnlUpdate());
        tbp.add("View", pnlView());
        return tbp;
    }
    JTable tblManager;

    private JPanel pnlView() {
        JPanel pnl = new JPanel(new BorderLayout(0, 0));
        String[] x = {"Mã NV", "Password", "Name", "Role"};
        tblManager = new JTable();
        pnl.add(scrollPane(tblManager, x), BorderLayout.CENTER);

        JPanel pnlPrint = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton btn = new JButton(XImage.getAppImagelcon("/image/print-icon.png"));
        btn.setName("print");
        btn.addActionListener(this);
        pnlPrint.add(btn);
        pnl.add(pnlPrint, BorderLayout.SOUTH);
        return pnl;
    }

    private JScrollPane scrollPane(JTable tbl, Object[] column) {
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    row = tbl.getSelectedRow();
                    edit();
                }
            }
        });
        DefaultTableModel model = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl.setModel(model);
        JScrollPane scrool = new JScrollPane(tbl);
        return scrool;
    }

    private JPanel pnlUpdate() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.add(formNhap(), BorderLayout.CENTER);
        JPanel pnlBtn = new JPanel();
        pnlBtn.setLayout(new BorderLayout(20, 0));
        pnlBtn.add(btnTMSX(), BorderLayout.WEST);
        pnlBtn.add(btnQL(), BorderLayout.EAST);
        pnl.add(pnlBtn, BorderLayout.SOUTH);
        return pnl;
    }
    HashMap<Integer, FormInput> hashForm = new HashMap<>();

    private JPanel formNhap() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new GridLayout(5, 1, 0, 10));
        pnl.setSize(400, 600);
        hashForm.put(idNV, new FormInput(new JTextField(), "Mã NV"));
        hashForm.put(pass, new FormInput(new JPasswordField(), "Password"));
        hashForm.put(confirmPass, new FormInput(new JPasswordField(), "COnfirm Pass"));
        hashForm.put(name, new FormInput(new JTextField(), "Name"));
        hashForm.put(rdo, new FormInput(new Object[]{"Nhân viên", "Trưởng phòng"}, "Gender"));
        hashForm.forEach((key, nut) -> {
            nut.setLabelError(9, Color.RED);
            pnl.add(nut.pnl);
        });
        hashForm.get(rdo).rdo[0].setSelected(true);
        return pnl;
    }

    HashMap<Integer, Buttons> hashTMSX = new HashMap<>();

    private JPanel btnTMSX() {
        hashTMSX.put(btnAdd, new Buttons("Add"));
        hashTMSX.put(btnUpdate, new Buttons("Update"));
        hashTMSX.put(btnDelete, new Buttons("Delete"));
        hashTMSX.put(btnNew, new Buttons("New"));
        JPanel pnl = new JPanel(new GridLayout(1, 4, 10, 0));
        hashTMSX.forEach((key, btn) -> {
            btn.btn.setName(btn.getName());
            btn.btn.setText(btn.getName());
            btn.btn.setBackground(Color.YELLOW);
            btn.btn.setMargin(new Insets(5, 5, 5, 5));
            btn.setProperties(12, Color.BLACK, this);
            pnl.add(btn.btn);
        });
        return pnl;
    }

    HashMap<Integer, Buttons> hashQL = new HashMap<>();

    private JPanel btnQL() {
        hashQL.put(btnFirst, new Buttons("First", new Object[]{"/image/btnFirst.png"}));
        hashQL.put(btnPrev, new Buttons("Prev", new Object[]{"/image/btnPrev.png"}));
        hashQL.put(btnNext, new Buttons("Next", new Object[]{"/image/btnNext.png"}));
        hashQL.put(btnLast, new Buttons("Last", new Object[]{"/image/btnLast.png"}));
        JPanel pnl = new JPanel(new GridLayout(1, 4, 10, 0));
        hashQL.forEach((key, btn) -> {
            btn.btn.setIcon(XImage.getAppImagelcon((String) btn.getObj()[0]));
            btn.btn.setName(btn.getName());
            btn.btn.setBackground(Color.YELLOW);
            btn.btn.setMargin(new Insets(5, 5, 5, 5));
            btn.setProperties(12, Color.BLACK, this);
            pnl.add(btn.btn);
        });
        return pnl;
    }

    final String reID = "^[a-zA-Z]{2}\\d{4}$";

    public boolean checkData(String insert) {
        Valid vl = new Valid();
        if (vl.multireMatch(hashForm.get(idNV), "id employee", reID)) {
            if (insert.equals("insert")) {
                if (duyetMang(hashForm.get(idNV).getText()) != null) {
                    vl.loi(hashForm.get(idNV), "Staff id had exists");
                }
            }
        }

        if (vl.isEmpty(hashForm.get(pass), "Password") && vl.isEmpty(hashForm.get(confirmPass), "Confirm")) {
            if (!vl.compare(hashForm.get(pass), "Pass and confirm not equal", hashForm.get(confirmPass).getText())) {
                vl.loi(hashForm.get(confirmPass), "Pass and confirm not equal");
            }
        }
        vl.multireMatchLower(hashForm.get(name), "name", vl.reName());

        return vl.getLoi().equals("");
    }

    StaffDAO dao = new StaffDAO();
    int row = -1;

    public Staff duyetMang(String x) {
        String a = x.toLowerCase();
        for (Staff nv : list) {
            if (nv.getIdNV().toLowerCase().equals(a)) {
                return nv;
            }
        }
        return null;
    }

    public void insert() {
        if (!chucVu()) {
            Msg.alert(this, "you don't have ability add manager, Because you don't have authority");
            return;
        }
        if (!checkData("insert")) {
            return;
        }

        Staff nv = getForm();
        try {
            dao.insert(nv);
            this.fillTable();
            this.clearForm();
            Msg.alert(this, "Add success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    private boolean chucVu() {
        if (!Auth.isManager()) {
            if (hashForm.get(rdo).rdo[1].isSelected()) {
                return false;
            }
        }

        return true;
    }

    public void update() {
        if (!chucVu()) {
            Msg.alert(this, "you haven't ability update from one staff promote to manager");
            return;
        }
        if (!checkData("update")) {
            return;
        }
        Staff nv = getForm();
        try {
            dao.update(nv);
            this.fillTable();
            this.clearForm();
            Msg.alert(this, "update success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    public void delete() {
        String manv = hashForm.get(idNV).getText();
        if (manv.equals(Auth.user.getIdNV())) {
            Msg.alert(this, "You cann't delete yourself");
        } else if (Msg.confirm(this, "You really want to delete this employee?")) {
            try {
                dao.delete(manv);
                this.fillTable();
                this.clearForm();
                Msg.alert(this, "Delete success");
            } catch (Exception e) {
                Msg.alert(this, "delete failure");
            }

        }
    }

    public void clearForm() {
        hashForm.forEach((key, nut) -> {
            if (key.compareTo(rdo) == 0) {
            } else {
                nut.backGround(Color.WHITE);
                nut.setTextLbl(" ");
                nut.setText("");
            }
        });
        this.row = -1;
        this.updateStatus();
    }

    public void edit() {
        String manv = (String) tblManager.getValueAt(this.row, 0);
        Staff nv = dao.selectById(manv);
        this.setFom(nv);
        tbp.setSelectedIndex(0);
        this.updateStatus();
        hashForm.forEach((key, nut) -> {
            if (key.compareTo(rdo) == 0) {
            } else {
                nut.backGround(Color.WHITE);
                nut.setTextLbl(" ");
            }
        });
    }

    public void first() {
        this.row = 0;
        this.edit();
    }

    public void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    public void next() {
        if (this.row < tblManager.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    public void last() {
        this.row = tblManager.getRowCount() - 1;
        this.edit();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblManager.getModel();
        model.setRowCount(0);
        try {
            list = dao.selectAll();
            for (Staff nv : list) {
                Object[] row = {nv.getIdNV(), Auth.isManager() ? nv.getPass() : nv.getPassAo(), nv.getName(), nv.isRole() ? "Manager" : "Staff"};
                model.addRow(row);
            }
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    public void setFom(Staff nv) {
        hashForm.get(idNV).setText(nv.getIdNV());
        hashForm.get(name).setText(nv.getName());
        hashForm.get(pass).setText(nv.getPass());
        hashForm.get(rdo).rdo[0].setSelected(!nv.isRole());
        hashForm.get(rdo).rdo[1].setSelected(nv.isRole());
    }

    public Staff getForm() {
        Staff nv = new Staff();
        nv.setIdNV(hashForm.get(idNV).txt.getText());
        nv.setPass(hashForm.get(pass).getText());
        nv.setName(hashForm.get(name).txt.getText());
        nv.setRole(hashForm.get(rdo).rdo[1].isSelected());
        return nv;
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblManager.getRowCount() - 1);
        boolean tbl = (this.row <= tblManager.getRowCount() - 1);
        //status form
        hashForm.get(idNV).txt.setEditable(!edit);
        hashTMSX.get(btnAdd).btn.setEnabled(!edit);
        hashTMSX.get(btnUpdate).btn.setEnabled(edit);
        if (Auth.isManager()) {
            hashTMSX.get(btnDelete).btn.setEnabled(edit);
        }
        //status điều hướng
        hashQL.get(btnFirst).btn.setEnabled(edit && !first);
        hashQL.get(btnPrev).btn.setEnabled(edit);
        hashQL.get(btnNext).btn.setEnabled(tbl);
        hashQL.get(btnLast).btn.setEnabled(tbl && !last);

    }

    private void print() {
        Msg.print(tblManager, "Table manager", "Fpt perfect");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String a = ((JButton) e.getSource()).getName();
        switch (a) {
            case "Add" ->
                insert();
            case "Delete" ->
                delete();
            case "Update" ->
                update();
            case "New" ->
                clearForm();
            case "First" ->
                first();
            case "Prev" ->
                prev();
            case "Next" ->
                next();
            case "Last" ->
                last();
            case "print" ->
                print();

        }
    }
}
