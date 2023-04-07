package poly.com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import poly.com.DAO.CourseDAO;
import poly.com.DAO.ThongKeDAO;
import poly.com.entity.Course;
import poly.com.utlis.Auth;

public class ManagerTK extends JDialog {

    JPanel pnlTableGrade, pnlLearner, pnlGradeCD, pnlTurnover;
    JTabbedPane tbp = new JTabbedPane();

    public ManagerTK(Frame owner, boolean modal) {
        super(owner, modal);
        int width_Frame = 600;
        int height_Frame = 500;
        setLayout(new BorderLayout(0, 10));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel("Chuyên đề");
        lblTitle.setFont(new Font("Aria", Font.BOLD, 20));
        lblTitle.setForeground(Color.blue);
        add(lblTitle, BorderLayout.NORTH);
        pnlTableGrade = new JPanel();
        pnlLearner = new JPanel();
        pnlGradeCD = new JPanel();
        pnlTurnover = new JPanel();
        tbp.add("Table Grade", pnlTableGrade);
        tbp.add("Learner", pnlLearner);
        tbp.add("Grade CD", pnlGradeCD);
        add(tbp);

        pnlTableGrade();
        pnlLearner();
        pnlGradeCD();
        if (Auth.isManager()) {
            pnlTurnover();
            tbp.add("Turnover", pnlTurnover);
            fillComboBoxYear();
        }
        
        
        fillComboBoxCourse();
        fillTableQuantityLearner();
        fillTableGradeChuyenDe();
    }
    JComboBox cboCourse;
    JTable tblTableGrade;
    private void pnlTableGrade() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout(10, 0));
        JLabel lbl = new JLabel("Khóa học :");
        cboCourse = new JComboBox();
        cboCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTableGrade();
            }
        });
        pnl.add(lbl, BorderLayout.WEST);
        pnl.add(cboCourse, BorderLayout.CENTER);
        pnlTableGrade.setLayout(new BorderLayout(0, 10));
        pnlTableGrade.add(pnl, BorderLayout.NORTH);
        String[] y = {"ID Learner", "Full name", "Grade", "Xếp loại"};
        tblTableGrade = new JTable();
        pnlTableGrade.add(scrollPane(tblTableGrade, y), BorderLayout.CENTER);
    }

    JTable tblLearner;
    private void pnlLearner() {
        pnlLearner.setLayout(new BorderLayout(0, 0));
        String[] y = {"Year", "Quantity Learner", "Register earliest", "Register latest"};
        tblLearner = new JTable();
        pnlLearner.add(scrollPane(tblLearner, y), BorderLayout.CENTER);
    }

    JTable tblGradeCD;
    private void pnlGradeCD() {
        pnlGradeCD.setLayout(new BorderLayout(0, 0));
        String[] y = {"Chuyên đề", "Quantity HV", "Grade min", "Grade max", "Grade average"};
        tblGradeCD = new JTable();
        pnlGradeCD.add(scrollPane(tblGradeCD, y), BorderLayout.CENTER);
    }

    JTable tblTurnover;
    JComboBox cboTurnover;
    private void pnlTurnover() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout(10, 0));
        JLabel lbl = new JLabel("Year :");
        cboTurnover = new JComboBox();
        cboTurnover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTableTurnover();
            }
        });
        pnl.add(lbl, BorderLayout.WEST);
        pnl.add(cboTurnover, BorderLayout.CENTER);
        pnlTurnover.setLayout(new BorderLayout(0, 10));
        pnlTurnover.add(pnl, BorderLayout.NORTH);
        String[] y = {"Chuyên đề","Số HV", "Turnover", "Fee min", "Fee max", "Fee average"};
        tblTurnover = new JTable();
        pnlTurnover.add(scrollPane(tblTurnover, y), BorderLayout.CENTER);
    }

    public void selectTab(int index) {
        tbp.setSelectedIndex(index);
    }
    
    CourseDAO cDao = new CourseDAO();
    private void fillComboBoxCourse(){
        DefaultComboBoxModel model = (DefaultComboBoxModel)cboCourse.getModel();
        model.removeAllElements();
        List<Course> list = cDao.selectAll();
        for (Course course : list) {
            model.addElement(course);
        }
        fillTableGrade();
    }
    
    ThongKeDAO tkDao = new ThongKeDAO();
    private void fillTableGrade(){
        DefaultTableModel model = (DefaultTableModel)tblTableGrade.getModel();
        model.setRowCount(0);
        Course c = (Course)cboCourse.getSelectedItem();
        List<Object[]> list = tkDao.getBangDiem(c.getCourseID());
        for (Object[] row : list) {
            double diem = (double)row[2];
            model.addRow(new Object[]{row[0],row[1],diem,getXepLoai(diem)});
        }
        
    }
    
    private String getXepLoai(double diem){
        if(diem<5){
            return "Chưa đạt";
        }else if(diem<6.5){
            return "Trung bình";
        }
        return diem < 9 ? "Giỏi" : "Xuất sắc";
    }
    
    private void fillTableQuantityLearner(){
        DefaultTableModel model = (DefaultTableModel)tblLearner.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkDao.getLuongNguoiHoc();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }
    
    private void fillTableGradeChuyenDe(){
        DefaultTableModel model = (DefaultTableModel)tblGradeCD.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkDao.getDiemChuyenDe();
        for (Object[] objects : list) {
            model.addRow(objects);
        }
    }
    
    
    private void fillComboBoxYear(){
        DefaultComboBoxModel model = (DefaultComboBoxModel)cboTurnover.getModel();
        model.removeAllElements();
        List<Integer> list = cDao.selectYear();
        for (Integer year : list) {
            model.addElement(year);
        }
        fillTableTurnover();
    }
    
    private void fillTableTurnover(){
        DefaultTableModel model = (DefaultTableModel)tblTurnover.getModel();
        model.setRowCount(0);
        int year = (Integer)cboTurnover.getSelectedItem();
        List<Object[]> list = tkDao.getDoanhThu(year);
        for (Object[] row : list) {
            model.addRow(row);
        }
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
                return false;
            }
        };
        tbl.setModel(model);
        JScrollPane scrool = new JScrollPane(tbl);
        return scrool;
    }
    

}
