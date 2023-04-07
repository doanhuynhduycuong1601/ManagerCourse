package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import poly.com.DAO.ChuyenDeDAO;
import poly.com.DAO.CourseDAO;
import poly.com.entity.ChuyenDe;
import poly.com.layout.Buttons;
import poly.com.layout.FormInput;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.Valid;
import poly.com.utlis.XImage;

public class Manager extends JDialog implements ActionListener {

    JTabbedPane tbp;
    JPanel pnlUpdate, pnlView;

    public Manager(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }
    final int idCD = 0;
    final int name = 1;
    final int time = 2;
    final int fee = 3;
    final int decription = 4;
    final int add = 0;
    final int update = 1;
    final int delete = 2;
    final int neww = 3;
    final int first = 0;
    final int prev = 1;
    final int next = 2;
    final int last = 3;

    private void init() {
        int width_Frame = 600;
        int height_Frame = 500;
        setLayout(new BorderLayout(0, 10));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);
        JLabel lblQLCD = new JLabel("Quản lý chuyên đề");
        add(lblQLCD, BorderLayout.NORTH);
        TabbedPane();
        pnlView();
        pnlUpdate();
        hashTMXS.get(delete).btn.setEnabled(false);
        updateStatus();
        fillTable();
    }

    private void TabbedPane() {
        tbp = new JTabbedPane();
        pnlView = new JPanel();
        pnlUpdate = new JPanel();
        tbp.add("Update", pnlUpdate);
        tbp.add("View", pnlView);
        add(tbp, BorderLayout.CENTER);
    }

    JTable tblManager;
    private void pnlView() {
        pnlView.setLayout(new BorderLayout());
        String[] x = {"Mã CĐ", "Tên CĐ", "Học phí", "Thời lượng", "Hình"};
        tblManager = new JTable();
        pnlView.add(scrollPane(tblManager, x), BorderLayout.CENTER);
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

    JLabel lblImgLogo;
    private void pnlUpdate() {
        pnlUpdate.setLayout(new BorderLayout(0, 10));
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout(20, 10));
        pnl.add(pnlImgCD(), BorderLayout.WEST);
        pnl.add(pnlInputCD(), BorderLayout.CENTER);
        pnlUpdate.add(pnl, BorderLayout.NORTH);

        pnl = new JPanel(new BorderLayout(20, 0));
        pnl.add(pnlTMSX(), BorderLayout.WEST);
        pnl.add(pnlQuaLaiCD(), BorderLayout.EAST);
        pnlUpdate.add(pnl, BorderLayout.SOUTH);
    }

    private JPanel pnlImgCD() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout(0, 2));
        JLabel lblName = new JLabel("Image Logo");
        lblImgLogo = new JLabel();
        int width = 200;
        int height = 240;
        lblImgLogo.setSize(new Dimension(width, height));
        lblImgLogo.setPreferredSize(new Dimension(width, height));
        pnl.add(lblName, BorderLayout.NORTH);
        pnl.add(lblImgLogo, BorderLayout.CENTER);
        return pnl;
    }

    HashMap<Integer, FormInput> hashForm = new HashMap<>();
    private JPanel pnlInputCD() {
        JPanel pnl = new JPanel(new GridLayout(4, 1, 0, 3));
        hashForm.put(idCD, new FormInput(new JTextField(), "Id CD"));
        hashForm.put(name, new FormInput(new JTextField(), "Name CD"));
        hashForm.put(time, new FormInput(new JTextField(), "Time"));
        hashForm.put(fee, new FormInput(new JTextField(), "Fee"));
        hashForm.forEach((key, nut) -> {
            nut.setLabelError(9, Color.RED);
            pnl.add(nut.pnl);
        });
        hashForm.put(decription, new FormInput(new JTextArea(), "Description"));
        hashForm.get(decription).setLabelError(9, Color.RED);
        pnlUpdate.add(hashForm.get(decription).pnl);
        return pnl;
    }

    HashMap<Integer, Buttons> hashTMXS = new HashMap<>();

    private JPanel pnlTMSX() {
        JPanel pnl = new JPanel(new GridLayout(1, 4, 5, 0));
        hashTMXS.put(add, new Buttons("Add"));
        hashTMXS.put(update, new Buttons("Update"));
        hashTMXS.put(delete, new Buttons("Delete"));
        hashTMXS.put(neww, new Buttons("New"));
        hashTMXS.forEach((key, nut) -> {
            nut.btn.setText(nut.getName());
            nut.btn.setName(nut.getName());
            nut.btn.setMargin(new Insets(5, 5, 5, 5));
            nut.setProperties(12, Color.BLACK, this);
            pnl.add(nut.btn);
        });
        return pnl;
    }

    HashMap<Integer, Buttons> hashQL = new HashMap<>();

    private JPanel pnlQuaLaiCD() {
        JPanel pnl = new JPanel(new GridLayout(1, 4, 3, 0));
        hashQL.put(first, new Buttons("First", new Object[]{"/image/btnFirst.png"}));
        hashQL.put(prev, new Buttons("Prev", new Object[]{"/image/btnPrev.png"}));
        hashQL.put(next, new Buttons("Next", new Object[]{"/image/btnNext.png"}));
        hashQL.put(last, new Buttons("Last", new Object[]{"/image/btnLast.png"}));
        hashQL.forEach((key, nut) -> {
            nut.btn.setIcon(XImage.getAppImagelcon((String) nut.getObj()[0]));
            nut.btn.setMargin(new Insets(5, 5, 5, 5));
            nut.btn.setName(nut.getName());
            nut.setProperties(12, Color.BLACK, this);
            pnl.add(nut.btn);
        });
        return pnl;
    }

    private int duyetID() {
        String x = hashForm.get(idCD).getText().toLowerCase().trim();
        for (int i = 0; i < list.size(); i++) {
            ChuyenDe cd = list.get(i);
            if (cd.getMaCD().toLowerCase().equals(x)) {
                return i;
            }
        }
        return -1;
    }
    private final String reIDCD = "^C[Dd]\\d{3}$";

    public boolean checkData() {
        Valid vl = new Valid();
        vl.multireMatch(hashForm.get(idCD), "ID CD", reIDCD);
        vl.multireMatchLower(hashForm.get(name), "Name", vl.reName());
        if (vl.multiBigger(hashForm.get(fee), "Fee", 999.99)) {
            vl.lowerEqual(hashForm.get(fee), "Fee", 2000.0);
        }
        vl.multiBigger(hashForm.get(time), "Time", 9.9);
        vl.isEmpty(hashForm.get(decription), "Describe");
        return vl.getLoi().equals("");
    }

    public void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.reSizeImgae2(XImage.read(file.getName()), lblImgLogo);
            lblImgLogo.setIcon(icon);
            lblImgLogo.setToolTipText(file.getName());
        }
    }

    public ChuyenDe getForm() {
        ChuyenDe cd = new ChuyenDe(
                hashForm.get(idCD).getText(),
                hashForm.get(name).getText(),
                Double.parseDouble(hashForm.get(fee).getText()),
                Integer.parseInt(hashForm.get(time).getText()),
                lblImgLogo.getToolTipText(),
                hashForm.get(decription).getText()
        );
        return cd;
    }

    ChuyenDeDAO dao = new ChuyenDeDAO();
    int row = -1;
    public void insert() {
        if (!checkData()) {
            return;
        }

        if (duyetID() != -1) {
            Msg.loi(hashForm.get(idCD), "ID CD had exists");
            return;
        }
        chonAnh();
        ChuyenDe cd = getForm();
        try {
            dao.insert(cd);
            this.fillTable();
            this.clearForm();
            Msg.alert(this, "Add success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    public void update() {
        if (!checkData()) {
            return;
        }

        if (Msg.confirm(this, "Do you want to change image?")) {
            chonAnh();
        }
        ChuyenDe cd = getForm();
        DefaultTableModel model = (DefaultTableModel)tblManager.getModel();
        model.removeRow(row);
        model.insertRow(row, addRow(cd));
        list.set(row, cd);
        try {
            dao.update(cd);
            this.clearForm();
            Msg.alert(this, "update success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    public void delete() {
        String macd = hashForm.get(idCD).getText().trim();
        if (Msg.confirm(this, "You really want to delete this Chuyen De?")) {
            CourseDAO c = new CourseDAO();
            if (!c.selectByChuyenDe(macd).isEmpty()) {
                Msg.loi(hashForm.get(idCD), "You cannot delete Chuyên đề. Because Chuyên đề already exists course");
                return;
            }
            list.remove(row);
            DefaultTableModel model = (DefaultTableModel)tblManager.getModel();
            model.removeRow(row);
            try {
                dao.delete(macd);
                this.clearForm();
                Msg.alert(this, "Delete success");
            } catch (Exception e) {
                Msg.alert(this, "delete failure");
            }
        }
    }

    public void clearForm() {
        hashForm.forEach((key, nut) -> {
            nut.backGround(Color.WHITE);
            nut.setTextLbl(" ");
            nut.setText("");
        });
        lblImgLogo.setIcon(new ImageIcon(""));
        lblImgLogo.setToolTipText("");
        this.row = -1;
        this.updateStatus();
    }

    public void edit() {
        ChuyenDe cd = list.get(row);
        this.setForm(cd);
        tbp.setSelectedIndex(0);
        this.updateStatus();
        hashForm.forEach((key, nut) -> {
            nut.backGround(Color.WHITE);
            nut.setTextLbl(" ");
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

    List<ChuyenDe> list = new ArrayList<>();
    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblManager.getModel();
        model.setRowCount(0);
        try {
            list = dao.selectAll();
            for (ChuyenDe cd : list) {
                model.addRow(addRow(cd));
            }
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    private Object[] addRow(ChuyenDe cd) {
        return new Object[]{cd.getMaCD(), cd.getNameCD(), cd.getFee(), cd.getTimes(), cd.getImages()};
    }

    public void setForm(ChuyenDe cd) {
        hashForm.get(idCD).setText(cd.getMaCD());
        hashForm.get(name).setText(cd.getNameCD());
        hashForm.get(fee).setText(cd.getFee());
        hashForm.get(time).setText(cd.getTimes());
        hashForm.get(decription).setText(cd.getDescribe());
        if (cd.getImages() != null) {
            lblImgLogo.setToolTipText(cd.getImages());
            lblImgLogo.setIcon(XImage.reSizeImgae2(XImage.read(cd.getImages()), lblImgLogo));
        }
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblManager.getRowCount() - 1);
        boolean tbl = (this.row <= tblManager.getRowCount() - 1);
        //status form
        hashTMXS.get(add).btn.setEnabled(!edit);
        hashTMXS.get(update).btn.setEnabled(edit);
        if (Auth.isManager()) {
            hashTMXS.get(delete).btn.setEnabled(edit);
        }

        //status điều hướng
        hashQL.get(this.first).btn.setEnabled(edit && !first);
        hashQL.get(prev).btn.setEnabled(edit);
        hashQL.get(next).btn.setEnabled(tbl);
        hashQL.get(this.last).btn.setEnabled(tbl && !last);
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
