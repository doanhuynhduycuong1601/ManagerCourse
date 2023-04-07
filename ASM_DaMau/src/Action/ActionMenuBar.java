package Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import poly.com.ui.ChangePass;
import poly.com.ui.General;
import poly.com.ui.Introduce;
import poly.com.ui.Manager;
import poly.com.ui.ManagerCourse;
import poly.com.ui.ManagerLearner;
import poly.com.ui.ManagerNV;
import poly.com.ui.ManagerQLHV;
import poly.com.ui.ManagerTK;
import poly.com.ui.QR;
import poly.com.utlis.Msg;

public class ActionMenuBar implements ActionListener {

    General general;

    public ActionMenuBar(General general) {
        this.general = general;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String a = e.getActionCommand();
        switch (a) {
            case "Change Account" ->
                changeAccount();
            case "Change Pass" ->
                changePass();
            case "Stop" ->
                stop();
            case "Chuyên đề" ->
                chuyende();
            case "Khóa học" ->
                course();
            case "Người học" ->
                learner();
            case "Học viên" ->
                hocVien();
            case "Nhân viên" ->
                nv();
            case "Table Grade" ->
                openTK(0);
            case "Quantity Learner" ->
                openTK(1);
            case "Grade CD" ->
                openTK(2);
            case "Turnover" ->
                openTK(3);
            case "Introduce" ->
                introduce();
            case "Call me" -> call();
        }
    }

    private void changeAccount() {
        general.dispose();
        new General("Hệ thống quản lý đào tạo").setVisible(true);
    }
    
    private void changePass(){
        new ChangePass(general, true).setVisible(true);
    }

    private void stop() {
        if (Msg.confirm(general, "Bạn muốn thoát không?")) {
            general.dispose();
        }
    }

    private void openTK(int index) {
        ManagerTK tk = new ManagerTK(general, true);
        tk.selectTab(index);
        tk.setVisible(true);
    }
    
    private void introduce(){
        new Introduce(general, true).setVisible(true);
    }
    
    private void chuyende(){
        new Manager(general, true).setVisible(true);
    }
    
    private void course(){
        new ManagerCourse(general, true).setVisible(true);
    }
    
    private void learner(){
        new ManagerLearner(general, true).setVisible(true);
    }
    
    private void nv(){
        new ManagerNV(general, true).setVisible(true);
    }
    
    private void hocVien(){
        new ManagerQLHV(general, true).setVisible(true);
    }
    
    private void call(){
        new QR(general, true).setVisible(true);
    }
    

}
