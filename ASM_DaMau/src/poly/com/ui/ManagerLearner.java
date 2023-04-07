package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import poly.com.DAO.HocVienDAO;
import poly.com.DAO.LearnerDAO;
import poly.com.entity.Learner;
import poly.com.layout.Buttons;
import poly.com.layout.FormInput;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.Valid;
import poly.com.utlis.XImage;

public class ManagerLearner extends JDialog implements ActionListener {

    final int idLearner = 0;
    final int name = 1;
    final int gender = 2;
    final int dateBorn = 3;
    final int phone = 4;
    final int email = 5;
    final int note = 6;
    final int btnAdd = 0;
    final int btnUpdate = 1;
    final int btnDelete = 2;
    final int btnNew = 3;
    final int btnFirst = 0;
    final int btnPrev = 1;
    final int btnNext = 2;
    final int btnLast = 3;
    final int formAo = 1;
    final int formAll = 2;

    public ManagerLearner(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }
    JLabel lblTitle;

    int width_Frame = 600;
    int height_Frame = 500;

    private void init() {

        setLayout(new BorderLayout(0, 10));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        lblTitle = new JLabel("Quản lý người học");
        lblTitle.setForeground(Color.blue);
        add(lblTitle, BorderLayout.NORTH);

        tabbedPane();
        pnlView();
        pnlUpdate();
        hashTMSX.get(btnDelete).btn.setEnabled(false);
        updateStatus();
        fillTable();
    }

    JTabbedPane tbp;
    JPanel pnlView, pnlUpdate;

    private void tabbedPane() {
        tbp = new JTabbedPane();
        pnlView = new JPanel();
        pnlUpdate = new JPanel();
        tbp.add("Update", pnlUpdate);
        tbp.add("View", pnlView);
        add(tbp, BorderLayout.CENTER);
    }

    JPanel pnlFind;
    JTextField txtFind;
    JButton btnFind;
    JTable tblManager, tblAo;
    JPanel pnlAll, pnlAo;
    JLayeredPane layer;
    JCheckBox chk;

    private void pnlView() {
        pnlView.setLayout(new BorderLayout(5, 15));

        chk = new JCheckBox();
        pnlFind = new JPanel();
        pnlFind.setLayout(new BorderLayout(20, 10));
        txtFind = new JTextField();
        txtFind.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        txtFind.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtFind.add(chk);
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtFind.remove(chk);
            }
        });
        chk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chk();
            }
        });

        btnFind = new JButton("Search");
        btnFind.setName("Search");
        btnFind.addActionListener(this);
        pnlFind.add(txtFind, BorderLayout.CENTER);
        pnlFind.add(btnFind, BorderLayout.EAST);
        pnlView.add(pnlFind, BorderLayout.NORTH);

        String[] column = {"ID Learner", "Full name", "Gender", "Ngày sinh", "Phone", "Email", "Mã nv", "Ngày tạo"};
        layer = new JLayeredPane();
        layer.setLayout(new CardLayout(0, 0));
        tblManager = new JTable();
        pnlAll = new JPanel(new BorderLayout());
        pnlAll.add(scrollPane(tblManager, column), BorderLayout.CENTER);
        pnlAll.setSize(width_Frame - 20, 400);
        layer.add(pnlAll);

        tblAo = new JTable();
        pnlAo = new JPanel(new BorderLayout());
        pnlAo.add(scrollPane(tblAo, column), BorderLayout.NORTH);
        pnlAo.setSize(width_Frame - 20, 400);
        layer.add(pnlAo);

        pnlView.add(layer, BorderLayout.CENTER);
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton btn = new JButton(XImage.getAppImagelcon("/image/print-icon.png"));
        btn.setName("print");
        btn.addActionListener(this);
        pnl.add(btn);
        pnlView.add(pnl, BorderLayout.SOUTH);

    }

    private JScrollPane scrollPane(JTable tbl, Object[] column) {
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    row = tbl.getSelectedRow();
                    edit(tbl);
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

    private void pnlUpdate() {
        pnlUpdate.setLayout(new BorderLayout(0, 15));
        formNhap();
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(btnTMSX(), BorderLayout.WEST);
        pnl.add(btnQL(), BorderLayout.EAST);
        pnlUpdate.add(pnl, BorderLayout.SOUTH);

    }
    public HashMap<Integer, FormInput> hashForm = new HashMap<>();

    private void formNhap() {
        int hgap = 10;
        int width = width_Frame / 2 - 35;
        int maxWidth = width * 2 + 5;
        int height = 60;
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER, hgap, 7));
        hashForm.put(idLearner, new FormInput(new JTextField(), "ID learner", new Object[]{maxWidth, height}));
        hashForm.put(name, new FormInput(new JTextField(), "Name", new Object[]{maxWidth, height}));
        hashForm.put(gender, new FormInput(new Object[]{"Male", "Female"}, "Gender", new Object[]{width, height}));
        hashForm.put(dateBorn, new FormInput(new JTextField(), "Ngày sinh", new Object[]{width, height}));
        hashForm.put(phone, new FormInput(new JTextField(), "Phone", new Object[]{width, height}));
        hashForm.put(email, new FormInput(new JTextField(), "Email Address", new Object[]{width, height}));
        hashForm.put(note, new FormInput(new JTextArea(), "Note", new Object[]{maxWidth, height + 20}));
        hashForm.forEach((key, nut) -> {
            nut.setLabelError(9, Color.red);
            nut.pnl.setPreferredSize(new Dimension((int) nut.getProperties()[0], (int) nut.getProperties()[1]));
            pnl.add(nut.pnl);
        });
        pnlUpdate.add(pnl, BorderLayout.CENTER);
        hashForm.get(gender).rdo[1].setSelected(true);
    }

    HashMap<Integer, Buttons> hashTMSX = new HashMap<>();

    private JPanel btnTMSX() {
        hashTMSX.put(btnAdd, new Buttons("Add"));
        hashTMSX.put(btnUpdate, new Buttons("Update"));
        hashTMSX.put(btnDelete, new Buttons("Delete"));
        hashTMSX.put(btnNew, new Buttons("New"));
        JPanel pnl = new JPanel(new GridLayout(1, 4, 10, 0));
        hashTMSX.forEach((key, btn) -> {
            btn.btn.setText(btn.getName());
            btn.btn.setName(btn.getName());
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
            btn.btn.setName(btn.getName());
            btn.btn.setIcon(XImage.getAppImagelcon((String) btn.getObj()[0]));
            btn.btn.setBackground(Color.YELLOW);
            btn.btn.setMargin(new Insets(5, 5, 5, 5));
            btn.setProperties(12, Color.BLACK, this);
            pnl.add(btn.btn);
        });
        return pnl;
    }

    private Learner duyetID() {
        String x = hashForm.get(idLearner).getText().toLowerCase().trim();
        for (Learner ln : list) {
            if (ln.getIdNH().toLowerCase().equals(x)) {
                return ln;
            }
        }
        return null;
    }

    private final String reIDNH = "^N[hH]\\d{4}$";

    public boolean checkData(String action) {
        Valid vl = new Valid();
        if (vl.multireMatch(hashForm.get(idLearner), "ID learner", reIDNH)) {
            if (action.equals("insert")) {
                if (duyetID() != null) {
                    vl.loi(hashForm.get(idLearner), "Learner ID had exists");
                }
            }
        }

        vl.multireMatchLower(hashForm.get(name), "Name", vl.reName());
        vl.multireMatch(hashForm.get(dateBorn), "Dateborn", vl.reDate());
        vl.multireMatch(hashForm.get(phone), "Phone inappropriate", vl.rePhone());
        vl.multireMatch(hashForm.get(email), "Email inappropriate", vl.reEmail());
        vl.isEmpty(hashForm.get(note), "Note is empty");
        return vl.getLoi().equals("");
    }

    public Learner getForm() {
        Learner ln = new Learner(
                hashForm.get(idLearner).getText(),
                hashForm.get(name).getText(),
                hashForm.get(gender).rdo[0].isSelected(),
                hashForm.get(dateBorn).getText(),
                hashForm.get(email).getText(),
                hashForm.get(phone).getText(),
                hashForm.get(note).getText(),
                Auth.user.getIdNV()
        );
        return ln;
    }

    LearnerDAO dao = new LearnerDAO();
    int row = -1;

    public void insert() {
        if (!checkData("insert")) {
            return;
        }
        Learner ln = getForm();

        try {
            dao.insert(ln);
            this.fillTable();
            this.clearForm();
            Msg.alert(this, "Add success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }

    }

    public void update() {
        if (!checkData("update")) {
            return;
        }
        Learner nv = getForm();
        try {
            dao.update(nv);
            this.fillTable();
            this.clearForm();
            Msg.alert(this, "update success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }
        txtFind.setText("");
        layer(pnlAll);
    }

    public void delete() {

        String manh = hashForm.get(idLearner).getText();
        if (Msg.confirm(this, "You really want to delete learner " + manh + "?")) {
            HocVienDAO hv = new HocVienDAO();
            if (!hv.selectByLearner(manh).isEmpty()) {
                Msg.loi(hashForm.get(idLearner), "you can't delete learner " + manh + ". because they have studied");
                return;
            }
            try {
                dao.delete(manh);
                this.fillTable();
                this.clearForm();
                Msg.alert(this, "Delete success");
            } catch (Exception e) {
                Msg.alert(this, "delete failure");
            }
            txtFind.setText("");
            layer(pnlAll);

        }
    }

    public void clearForm() {
        hashForm.forEach((key, nut) -> {
            if (key.compareTo(gender) == 0) {
            } else {
                nut.backGround(Color.WHITE);
                nut.setTextLbl(" ");
                nut.setText("");
            }
        });
        this.row = -1;
        this.updateStatus();
    }

    public void edit(JTable tbl) {
        String manv = (String) tbl.getValueAt(this.row, 0);
        Learner nv = dao.selectById(manv);
        this.setForm(nv);
        tbp.setSelectedIndex(0);
        this.updateStatus();
        hashForm.forEach((key, nut) -> {
            if (key.compareTo(gender) == 0) {
            } else {
                nut.backGround(Color.WHITE);
                nut.setTextLbl(" ");
            }
        });
    }

    public void first() {
        this.row = 0;
        this.edit(tblManager);
    }

    public void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit(tblManager);
        }
    }

    public void next() {
        if (this.row < tblManager.getRowCount() - 1) {
            this.row++;
            this.edit(tblManager);
        }
    }

    public void last() {
        this.row = tblManager.getRowCount() - 1;
        this.edit(tblManager);
    }

    List<Learner> list = new ArrayList<>();

    public void fillTable() {
        DefaultTableModel modelManager = (DefaultTableModel) tblManager.getModel();
        modelManager.setRowCount(0);
        try {
            list = dao.selectAll();
            for (Learner ln : list) {
                addRow(ln, modelManager);
            }
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    private void addRow(Learner ln, DefaultTableModel model) {
        Object[] row = {ln.getIdNH(), ln.getNames(), ln.isGender(), ln.getDateBorn(), ln.getPhone(), ln.getEmail(), ln.getStaffID(), ln.getRegisterDate()};
        model.addRow(row);
    }

    public void setForm(Learner ln) {
        hashForm.get(idLearner).setText(ln.getIdNH());
        hashForm.get(name).setText(ln.getNames());
        hashForm.get(gender).rdo[1].setSelected(!ln.isGender());
        hashForm.get(gender).rdo[0].setSelected(ln.isGender());
        hashForm.get(dateBorn).setText(ln.getDateBorn());
        hashForm.get(phone).setText(ln.getPhone());
        hashForm.get(email).setText(ln.getEmail());
        hashForm.get(note).setText(ln.getNote());
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblManager.getRowCount() - 1);
        boolean tbl = (this.row <= tblManager.getRowCount() - 1);
        //status form
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
            case "Search" ->
                find();
            case "print" ->
                print();
        }
    }

    List<Learner> listAo = new ArrayList<>();

    private List SearchName() {
        listAo.clear();
        DefaultTableModel modelAo = (DefaultTableModel) tblAo.getModel();
        modelAo.setRowCount(0);
        for (Learner ln : list) {
            if (ln.getNames().trim().contains(txtFind.getText())) {
                listAo.add(ln);
                addRow(ln, modelAo);
            }
        }
        return listAo;
    }

    private void find() {
        if (SearchName().size() <= 0) {
            Msg.alert(this, "k tìm thấy");
            return;
        }
        clearForm();
        updateStatus();
        layer(pnlAo);
    }

    private void layer(JPanel pnl) {
        layer.removeAll();
        layer.add(pnl);
        layer.repaint();
        layer.revalidate();
    }

    public void chk() {
        chk.setSelected(false);
        layer(pnlAll);
        txtFind.remove(chk);
        txtFind.setText("");
    }
}
