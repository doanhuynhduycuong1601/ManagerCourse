/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import poly.com.DAO.ChuyenDeDAO;
import poly.com.DAO.CourseDAO;
import poly.com.DAO.HocVienDAO;
import poly.com.entity.ChuyenDe;
import poly.com.entity.Course;
import poly.com.layout.Buttons;
import poly.com.layout.FormInput;
import poly.com.utlis.Auth;
import poly.com.utlis.Msg;
import poly.com.utlis.Valid;
import poly.com.utlis.XDate;
import poly.com.utlis.XImage;

/**
 *
 * @author Duy Cuong
 */
public class ManagerCourse extends JDialog implements ActionListener {

    JTabbedPane tbp;
    final int cD = 0;
    final int openDate = 1;
    final int fee = 2;
    final int time = 3;
    final int creator = 4;
    final int createDate = 5;
    final int note = 6;
    final int add = 0;
    final int update = 1;
    final int delete = 2;
    final int neww = 3;
    final int first = 0;
    final int prev = 1;
    final int next = 2;
    final int last = 3;

    public ManagerCourse(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }

    JLabel lblTitle;
    JComboBox cbo;

    int width_Frame = 680;
    int height_Frame = 500;

    private void init() {

        setLayout(new BorderLayout(0, 10));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout(0, 5));
        lblTitle = new JLabel("Chuyên đề");
        lblTitle.setForeground(Color.red);
        pnl.add(lblTitle, BorderLayout.NORTH);
        cbo = new JComboBox();
        cbo.setBorder(new LineBorder(Color.BLACK, 1));
        cbo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseChuyenDe(1);
                fillTable();
            }
        });
        pnl.add(cbo, BorderLayout.CENTER);

        add(pnl, BorderLayout.NORTH);
        add(tabbedPane(), BorderLayout.CENTER);
        fillComboBoxChuyenDe();
        hashTMSX.get(delete).btn.setEnabled(false);
    }

    private JTabbedPane tabbedPane() {
        tbp = new JTabbedPane();
        tbp.add("Update", pnlUpdate());
        tbp.add("View", pnlView());
        return tbp;
    }

    JTable tblManager;

    private JPanel pnlView() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout());
        String[] x = {"Course ID", "Time", "Fee", "Opening Date", "Createor", "Create Date"};
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

    HashMap<Integer, FormInput> hashForm = new HashMap<>();

    private JPanel pnlUpdate() {
        JPanel pnl = new JPanel(new BorderLayout(0, 15));
        pnl.add(formNhap(), BorderLayout.CENTER);
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 0));
        pnlBtn.add(btnTMSX(), BorderLayout.WEST);
        pnlBtn.add(btnQL(), BorderLayout.EAST);
        pnl.add(pnlBtn, BorderLayout.SOUTH);
        return pnl;
    }

    private JPanel formNhap() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnl.setPreferredSize(new Dimension(90, 220));
        int width = width_Frame / 2 - 10 * 2;
        int height = 65;
        hashForm.put(cD, new FormInput(new JTextField(), "Name chuyên đề", new Object[]{width, height,}));
        hashForm.put(openDate, new FormInput(new JTextField(), "Opening Date", new Object[]{width, height}));
        hashForm.put(fee, new FormInput(new JTextField(), "Fee", new Object[]{width, height}));
        hashForm.put(time, new FormInput(new JTextField(), "Time", new Object[]{width, height}));
        hashForm.put(creator, new FormInput(new JTextField(), "Creator", new Object[]{width, height}));
        hashForm.put(createDate, new FormInput(new JTextField(), "Create date", new Object[]{width, height}));
        hashForm.put(note, new FormInput(new JTextArea(), "Note", new Object[]{width * 2 + 10, height + 20}));
        hashForm.forEach((key, nut) -> {
            nut.pnl.setPreferredSize(new Dimension((int) nut.getProperties()[0], (int) nut.getProperties()[1]));
            nut.setLabelError(9, Color.red);
            pnl.add(nut.pnl);
        });
        hashForm.get(cD).txt.setEditable(false);
        hashForm.get(time).txt.setEditable(false);
        hashForm.get(createDate).txt.setEditable(false);
        hashForm.get(creator).txt.setEditable(false);
        hashForm.get(fee).txt.setEditable(false);
        return pnl;
    }

    HashMap<Integer, Buttons> hashTMSX = new HashMap<>();

    private JPanel btnTMSX() {
        hashTMSX.put(add, new Buttons("Add"));
        hashTMSX.put(update, new Buttons("Update"));
        hashTMSX.put(delete, new Buttons("Delete"));
        hashTMSX.put(neww, new Buttons("New"));
        JPanel pnl = new JPanel(new GridLayout(1, 4, 10, 0));
        hashTMSX.forEach((key, btn) -> {
            btn.btn.setText((String) btn.getName());
            btn.btn.setName((String) btn.getName());
            btn.btn.setMargin(new Insets(10, 10, 10, 10));
            btn.setProperties(12, Color.BLACK, this);
            pnl.add(btn.btn);
        });
        return pnl;
    }

    HashMap<Integer, Buttons> hashQL = new HashMap<>();

    private JPanel btnQL() {
        hashQL.put(first, new Buttons("First", new Object[]{"/image/btnFirst.png"}));
        hashQL.put(prev, new Buttons("Prev", new Object[]{"/image/btnPrev.png"}));
        hashQL.put(next, new Buttons("Next", new Object[]{"/image/btnNext.png"}));
        hashQL.put(last, new Buttons("Last", new Object[]{"/image/btnLast.png"}));
        JPanel pnl = new JPanel(new GridLayout(1, 4, 10, 0));
        hashQL.forEach((key, btn) -> {
            btn.btn.setName((String) btn.getName());
            btn.btn.setIcon(XImage.getAppImagelcon((String) btn.getObj()[0]));
            btn.btn.setBackground(Color.YELLOW);
            btn.btn.setMargin(new Insets(5, 5, 5, 5));
            btn.setProperties(12, Color.BLACK, this);
            pnl.add(btn.btn);
        });
        return pnl;
    }

    ChuyenDeDAO cd = new ChuyenDeDAO();

    private void fillComboBoxChuyenDe() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo.getModel();
        model.removeAllElements();
        List<ChuyenDe> listCD = cd.selectAll();
        for (ChuyenDe chuyenDe : listCD) {
            model.addElement(chuyenDe);
        }
    }

    CourseDAO dao = new CourseDAO();
    int row = -1;
    List<Course> listCourse;

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblManager.getModel();
        model.setRowCount(0);
        try {
            ChuyenDe chuyenDe = (ChuyenDe) cbo.getSelectedItem();
            listCourse = dao.selectByChuyenDe(chuyenDe.getMaCD());
            for (Course course : listCourse) {
                model.addRow(row(course));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object[] row(Course c) {
        return new Object[]{c.getCourseID(), c.getTimes(), c.getFee(), c.getOpenDate(), c.getStaffID(), c.getCreateDate()};
    }

    private void chooseChuyenDe(int i) {
        ChuyenDe chuyenDe = (ChuyenDe) cbo.getSelectedItem();
        hashForm.get(fee).setText(String.valueOf(chuyenDe.getFee()));
        hashForm.get(time).setText(String.valueOf(chuyenDe.getTimes()));
        hashForm.get(cD).setText(chuyenDe.getNameCD());
        hashForm.get(note).setText(chuyenDe.getDescribe());
        row = -1;
        updateStatus();
        tbp.setSelectedIndex(i);
    }

    private boolean checkData(String insert) {
        Valid vl = new Valid();
        if (vl.multireMatch(hashForm.get(openDate), "Opening date", vl.reDate())) {
            Date date = new Date();
            if (XDate.toDate(hashForm.get(openDate).getText(), "yyyy-MM-dd").before(date)) {
                vl.loi(hashForm.get(openDate), "You cannot " + insert + " the opening date before the present date");
            }
        }
        vl.isEmpty(hashForm.get(note), "Note");
        return vl.getLoi().equals("");
    }

    private void insert() {
        if (!checkData("add")) {
            return;
        }

        Course c = getForm();
        try {
            dao.insert(c);
            this.clearForm();
            fillTable();
            Msg.alert(this, "Add success");
        } catch (Exception e) {
            Msg.alert(this, e);
        }

    }

    private void update() {
        if (!checkData("update")) {
            return;
        }

        Course c = getForm();
        c.setCourseID((Integer) tblManager.getValueAt(row, 0));

        HocVienDAO hv = new HocVienDAO();
        if (!hv.selectByCourse(c.getCourseID()).isEmpty()) {
            lineBorder(Color.yellow, "You can not update course " + c.getCourseID() + ". Because course already have students");
            return;
        }

        try {
            dao.update(c);
            this.clearForm();
            fillTable();
            Msg.alert(this, "Update");
        } catch (Exception e) {
            Msg.alert(this, e);
        }
    }

    private void delete() {
        int courseID = listCourse.get(row).getCourseID();
        if (Msg.confirm(this, "You really want to delete course " + courseID + "?")) {
            HocVienDAO hv = new HocVienDAO();
            if (!hv.selectByCourse(courseID).isEmpty()) {
                lineBorder(Color.yellow, "You cannot delete course. Because course already exists students");
                return;
            }
            try {
                dao.delete(courseID);
                this.fillTable();
                this.clearForm();
                Msg.alert(this, "Delete success");
            } catch (Exception e) {
                Msg.alert(this, "delete failure");
            }
        }
    }

    private Course getForm() {
        Course c = new Course(
                Auth.user.getIdNV(),
                ((ChuyenDe) cbo.getSelectedItem()).getMaCD(),
                Double.parseDouble(hashForm.get(fee).getText()),
                Integer.parseInt(hashForm.get(time).getText()),
                hashForm.get(openDate).getText(),
                hashForm.get(note).getText()
        );
        return c;
    }

    private void lineBorder(Color color, String msg) {
        hashForm.forEach((key, nut) -> {
            nut.lineBorder(color);
            nut.setTextLbl(msg);
        });
    }

    private void clearForm() {
        hashForm.get(openDate).backGround(Color.WHITE);
        hashForm.get(openDate).setText("");
        hashForm.get(note).backGround(Color.WHITE);
        hashForm.get(note).setText("");
        lineBorder(Color.WHITE, " ");
        chooseChuyenDe(0);
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblManager.getRowCount() - 1);
        boolean tbl = (this.row <= tblManager.getRowCount() - 1);
        //status form
        hashTMSX.get(add).btn.setEnabled(!edit);
        hashTMSX.get(update).btn.setEnabled(edit);
        if (Auth.isManager()) {
            hashTMSX.get(delete).btn.setEnabled(edit);
        }

        //status điều hướng
        hashQL.get(this.first).btn.setEnabled(edit && !first);
        hashQL.get(prev).btn.setEnabled(edit);
        hashQL.get(next).btn.setEnabled(tbl);
        hashQL.get(this.last).btn.setEnabled(tbl && !last);
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

    private void setForm(Course c) {
        hashForm.get(openDate).setText(c.getOpenDate());
        hashForm.get(fee).setText(String.valueOf(c.getFee()));
        hashForm.get(time).setText(String.valueOf(c.getTimes()));
        hashForm.get(note).setText(c.getNote());
        hashForm.get(creator).setText(c.getStaffID());
        hashForm.get(createDate).setText(c.getCreateDate());
    }

    public void edit() {
        Course c = listCourse.get(row);
        this.setForm(c);
        tbp.setSelectedIndex(0);
        this.updateStatus();
        hashForm.forEach((key, nut) -> {
            nut.backGround(Color.WHITE);
            nut.setTextLbl(" ");
        });
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

    public static void main(String[] args) {
        new ManagerCourse(null, true).setVisible(true);
    }

}
