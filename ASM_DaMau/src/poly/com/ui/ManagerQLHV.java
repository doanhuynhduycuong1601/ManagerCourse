/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import poly.com.DAO.ChuyenDeDAO;
import poly.com.DAO.CourseDAO;
import poly.com.DAO.HocVienDAO;
import poly.com.DAO.LearnerDAO;
import poly.com.entity.ChuyenDe;
import poly.com.entity.Course;
import poly.com.entity.HocVien;
import poly.com.entity.Learner;
import poly.com.layout.Buttons;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.XImage;

public class ManagerQLHV extends JDialog implements ActionListener {

    public ManagerQLHV(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }

    JPanel pnlHV, pnlNH;
    JTabbedPane tbp;
    int width_Frame = 600;
    int height_Frame = 500;

    private void init() {
        setLayout(new BorderLayout());
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        JPanel pnlTong = new JPanel(new BorderLayout(0, 10));
        JPanel pnl = new JPanel(new GridLayout(1, 2, 20, 0));
        cboCD = new JComboBox();
        pnl.add(pnlCBO("Chuyên đề", cboCD));

        cboCourse = new JComboBox();
        pnl.add(pnlCBO("Course", cboCourse));
        pnlTong.add(pnl, BorderLayout.NORTH);

        tbp = new JTabbedPane();
        pnlHV = new JPanel();
        tbp.add("Học viên", pnlHV);
        pnlHV();
        pnlNH = new JPanel();
        tbp.add("Người học", pnlNH);
        pnlNH();
        pnlTong.add(tbp, BorderLayout.CENTER);
        add(pnlTong);
        fillComboBoxChuyenDe();
    }

    JComboBox cboCD;
    JComboBox cboCourse;

    private JPanel pnlCBO(String name, JComboBox cbo) {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        JLabel lbl = new JLabel(name);
        cbo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.equalsIgnoreCase("Chuyên đề")) {
                    fillComboBoxCourse();
                } else {
                    fillTableHV();
                }
            }
        });
        pnl.add(lbl, BorderLayout.NORTH);
        pnl.add(cbo, BorderLayout.CENTER);
        return pnl;
    }

    JTable tblHV;
    private final int btnHvUpdate = 1;
    private final int btnHvDelete = 0;
    private final int btnHvPrint = 2;
    HashMap<Integer, Buttons> hashHV = new HashMap<>();

    private void pnlHV() {
        pnlHV.setLayout(new BorderLayout(0, 10));
        String[] x = {"TT", "ID HV", "ID NH", "Name", "Grade"};
        tblHV = new JTable();
        pnlHV.add(scrollPane(tblHV, x), BorderLayout.CENTER);

        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hashHV.put(btnHvDelete, new Buttons("Delete from the course", new Object[]{"/image/Close-2-icon.png"}));
        hashHV.put(btnHvUpdate, new Buttons("Update grade", new Object[]{"/image/btnUpdate1.png"}));
        hashHV.put(btnHvPrint, new Buttons("Print", new Object[]{"/image/print-icon.png"}));
        hashHV.forEach((key, btn) -> {
            btn.btn.setText(btn.getName());
            btn.btn.setIcon(XImage.getAppImagelcon((String) btn.getObj()[0]));
            btn.btn.addActionListener(this);
            pnl.add(btn.btn);
        });
        if (!Auth.isManager()) {
            hashHV.get(btnHvDelete).btn.setEnabled(false);
        }
        pnlHV.add(pnl, BorderLayout.SOUTH);
    }

    JTextField txtFind;
    JButton btnNHAdd;
    JTable tblNH, tblNHAo;
    JPanel pnlTblNH, pnlTblNHAo;
    JLayeredPane layer;
    Timer time;
    boolean tbl = true;
    int btnTrai = 0;
    int btnPhai = 1;
    JPanel pnlQuaLai;

    private void pnlNH() {
        time = new Timer(1000, null);
        time.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtFind.getText().trim().length() == 0) {
                    layer(pnlTblNH);
                    time.stop();
                    return;
                }
                fillTableLearnerAo();
                time.stop();
            }
        });
        pnlNH.setLayout(new BorderLayout(0, 10));
        JPanel pnl = new JPanel(new BorderLayout(0, 0));
        pnl.setPreferredSize(new Dimension(100, 40));
        JLabel lbl = new JLabel("Search");
        txtFind = new JTextField();
        txtFind.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        txtFind.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                time.restart();
            }
        });
        pnl.add(lbl, BorderLayout.NORTH);

        pnlQuaLai = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlQuaLai.setPreferredSize(new Dimension(100, 10));
        HashMap<Integer, Buttons> hashQuaLai = new HashMap<>();
        hashQuaLai.put(btnTrai, new Buttons("<"));
        hashQuaLai.put(btnPhai, new Buttons(">"));
        hashQuaLai.forEach((key, nut) -> {
            nut.btn.setText(nut.getName());
            nut.btn.setMargin(new Insets(0, 0, 0, 0));
            nut.btn.addActionListener(this);
            pnlQuaLai.add(nut.btn);
        });
        JPanel pnlAo = new JPanel(new BorderLayout(10, 0));
        pnlAo.add(txtFind, BorderLayout.CENTER);
        pnlAo.add(pnlQuaLai, BorderLayout.EAST);
        pnl.add(pnlAo, BorderLayout.CENTER);
        pnlNH.add(pnl, BorderLayout.NORTH);

        String[] x = {"Mã NH", "Name", "Gender", "Date born", "Phone", "Email"};
        layer = new JLayeredPane();
        layer.setLayout(new CardLayout());
        tblNH = new JTable();
        pnlTblNH = new JPanel(new BorderLayout());
        pnlTblNH.add(scrollPane(tblNH, x), BorderLayout.CENTER);
//        pnlTblNH.setSize(width_Frame,height_Frame-100);
        layer.add(pnlTblNH);

        tblNHAo = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component com = super.prepareRenderer(renderer, row, column);
                com.setBackground(Color.WHITE);
                if (row == rows) {
                    if (column == columns) {
                        com.setBackground(Color.YELLOW);
                    }
                } else {
                    for (int i = 0; i < tblNHAo.getSelectedRows().length; i++) {
                        if (row == tblNHAo.getSelectedRows()[i]) {
                            com.setBackground(Color.MAGENTA);
                        }
                    }
                }
                return com;
            }
        };
        pnlTblNHAo = new JPanel(new BorderLayout());
        pnlTblNHAo.add(scrollPane(tblNHAo, x), BorderLayout.CENTER);

        pnlNH.add(layer, BorderLayout.CENTER);

        pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNHAdd = new JButton("Add to the source");
        btnNHAdd.addActionListener(this);
        pnl.add(btnNHAdd);
        pnlNH.add(pnl, BorderLayout.SOUTH);
    }

    private JScrollPane scrollPane(JTable tbl, String[] column) {
        tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                }
            }
        });
        DefaultTableModel model = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (tbl == tblHV) {
                    if (column == 4) {
                        return true;
                    }
                }
                return false;
            }
        };
        tbl.setModel(model);
        JScrollPane scrool = new JScrollPane(tbl);
        return scrool;
    }

    ChuyenDeDAO cdDao = new ChuyenDeDAO();

    private void fillComboBoxChuyenDe() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCD.getModel();
        model.removeAllElements();
        List<ChuyenDe> list = cdDao.selectAll();
        list.forEach(cd -> {
            model.addElement(cd);
        });
    }

    CourseDAO courseDao = new CourseDAO();

    private void fillComboBoxCourse() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCourse.getModel();
        model.removeAllElements();
        ChuyenDe cd = (ChuyenDe) cboCD.getSelectedItem();
        if (cd != null) {
            List<Course> list = courseDao.selectByChuyenDe(cd.getMaCD());
            list.forEach(courses -> {
                model.addElement(courses);
            });
        }
    }

    HocVienDAO hvDao = new HocVienDAO();
    LearnerDAO lnDao = new LearnerDAO();

    private void fillTableHV() {
        DefaultTableModel model = (DefaultTableModel) tblHV.getModel();
        model.setRowCount(0);
        Course course = (Course) cboCourse.getSelectedItem();
        if (course != null) {
            List<HocVien> list = hvDao.selectByCourse(course.getCourseID());
            for (int i = 0; i < list.size(); i++) {
                HocVien hv = list.get(i);
                String name = lnDao.selectById(hv.getIdLearner()).getNames();
                Object[] obj = new Object[]{i + 1, hv.getIdHV(), hv.getIdLearner(), name, hv.getGrade()};
                model.addRow(obj);
            }
        }
        fillTableLearner();
    }

    List<Learner> listLn;

    private void fillTableLearner() {
        DefaultTableModel model = (DefaultTableModel) tblNH.getModel();
        model.setRowCount(0);
        Course c = (Course) cboCourse.getSelectedItem();
        if (c != null) {
            txtFind.setText("");
            String keyword = txtFind.getText();
            listLn = lnDao.selectNotlnCourse(c.getCourseID(), keyword);
            listLn.forEach(l -> {
                Object[] obj = new Object[]{l.getIdNH(), l.getNames(), l.isGender() ? "Male" : "Female",
                    l.getDateBorn(), l.getPhone(), l.getEmail()};
                model.addRow(obj);
            });
        }
        layer(pnlTblNH);
    }

    List<Integer[]> listAo = new ArrayList<>();

    private void fillTableLearnerAo() {
        listAo.clear();
        findInt = 0;
        DefaultTableModel model = (DefaultTableModel) tblNHAo.getModel();
        model.setRowCount(0);
        int count = -1;
        for (int i = 0; i < listLn.size(); i++) {
            Learner ln = listLn.get(i);
            if (ln.getNames().contains(txtFind.getText()) || ln.getEmail().contains(txtFind.getText()) || ln.getPhone().contains(txtFind.getText())) {
                count++;
                if (ln.getNames().contains(txtFind.getText())) {
                    listAo.add(new Integer[]{count, 1});
                }
                if (ln.getPhone().contains(txtFind.getText())) {
                    listAo.add(new Integer[]{count, 4});
                }
                if (ln.getEmail().contains(txtFind.getText())) {
                    listAo.add(new Integer[]{count, 5});
                }
                model.addRow(new Object[]{ln.getIdNH(), ln.getNames(), ln.isGender() ? "Male" : "Female",
                    ln.getDateBorn(), ln.getPhone(), ln.getEmail()});
            }
        }
        if(listAo.isEmpty()){
            return;
        }
        rowsColumns();
        layer(pnlTblNHAo);
    }

    private void layer(JPanel pnl) {
        tbl = pnl == pnlTblNH;
        layer.removeAll();
        layer.add(pnl);
        layer.repaint();
        layer.revalidate();
    }

    int findInt = 0;
    int rows = 0;
    int columns = 0;

    private void trai() {
        if (tbl) {
            return;
        }
        findInt--;
        if (findInt < 0) {
            findInt = listAo.size() - 1;
        }
        rowsColumns();
    }

    private void phai() {
        if (tbl) {
            return;
        }
        findInt++;
        if (findInt >= listAo.size()) {
            findInt = 0;
        }
        rowsColumns();
    }

    private void rowsColumns() {
        rows = listAo.get(findInt)[0];
        columns = listAo.get(findInt)[1];
        tblNHAo.setRowSelectionInterval(rows, rows);
        tblNHAo.setColumnSelectionInterval(columns, columns);
        tblNHAo.setValueAt(tblNHAo.getValueAt(rows, columns), rows, columns);
//        tblNHAo.setCellSelectionEnabled(true);
//        tblNHAo.setEditingRow(rows);
    }

    private void tbl(JTable tbl) {
        Course c = (Course) cboCourse.getSelectedItem();
        for (int row : tbl.getSelectedRows()) {
            hvDao.insert(new HocVien((String) tbl.getValueAt(row, 0), c.getCourseID(), 0));
        }
    }

    private void addHocVien() {
        tbl(tbl ? tblNH : tblNHAo);
        fillTableHV();
        tbp.setSelectedIndex(0);
    }

    private void removeHV() {
        if (Msg.confirm(this, "Do you want delete students selected")) {
            for (int row : tblHV.getSelectedRows()) {
                int idStudent = (Integer) tblHV.getValueAt(row, 1);
                hvDao.delete(idStudent);
            }
            fillTableHV();
        }
    }

    private void updateGrade() {
        for (int i : tblHV.getSelectedRows()) {
            hvDao.update(
                    new HocVien(
                            (Integer) tblHV.getValueAt(i, 1),
                            tblHV.getValueAt(i, 4) instanceof String ? Float.parseFloat((String) tblHV.getValueAt(i, 4)) : (Float) tblHV.getValueAt(i, 4)
                    )
            );

        }
        Msg.alert(this, "Update success");
    }

    private void print() {
        Msg.print(tblHV, "Table manager", "Fpt perfect");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String x = e.getActionCommand();
        switch (x) {
            case "Add to the source" ->
                addHocVien();
            case "Delete from the course" ->
                removeHV();
            case "Update grade" ->
                updateGrade();
            case "<" ->
                trai();
            case ">" ->
                phai();
            case "Print" ->
                print();
        }
    }
}
