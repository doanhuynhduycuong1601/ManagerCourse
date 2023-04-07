package poly.com.ui;

import Action.ActionMenuBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import poly.com.layout.Buttons;
import poly.com.layout.MenuItems;
import poly.com.utlis.Auth;
import poly.com.utlis.XDate;
import poly.com.utlis.XImage;

public class General extends JFrame {

    public General(String title) throws HeadlessException {
        super(title);
        new Hello(this, true).setVisible(true);
        new Login(this, true).setVisible(true);
        if (Auth.isLogin()) {
            init();
        } else {
            System.exit(0);
        }
    }

    private void init() {
        int width_Frame = 750;
        int height_Frame = 550;
        setLayout(new BorderLayout(0, 10));
        setSize(width_Frame, height_Frame);
        setLocationRelativeTo(null);
        this.setIconImage(XImage.getApplcon("/image/iconFPT.png"));
//        setResizable(false);
        JPanel pnl = new JPanel(new BorderLayout(0, 5));
        pnl.add(jMenuBar(), BorderLayout.NORTH);
        pnl.add(jToolBar(), BorderLayout.CENTER);
        add(pnl, BorderLayout.NORTH);
        add(body(), BorderLayout.CENTER);
        add(last(), BorderLayout.SOUTH);
    }
    java.util.List<MenuItems> listMenu = new ArrayList<>();
    ActionMenuBar amb = new ActionMenuBar(this);

    private JMenuBar jMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuSystem());
        menuBar.add(menuManager());
        menuBar.add(menuThongKe());
        menuBar.add(menuSupport());
        return menuBar;
    }

    private JMenu menuSystem() {
        listMenu.clear();
        listMenu.add(new MenuItems("Change Account", new Object[]{"/image/key.png", KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK)}));
        listMenu.add(new MenuItems("Change Pass", new Object[]{"/image/btnUpdate.png", KeyStroke.getKeyStroke("")}));
        listMenu.add(new MenuItems("Stop", new Object[]{"/image/stop.png", KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.ALT_GRAPH_DOWN_MASK)}));
        JMenu menu = new JMenu("System");
        listMenu.forEach(mn -> {
            mn.menuItem.setIcon(XImage.getAppImagelcon((String) mn.getProperties()[0]));
            mn.menuItem.setAccelerator((KeyStroke) mn.getProperties()[1]);
            mn.menuItem.addActionListener(amb);
            menu.add(mn.menuItem);
        });
        return menu;
    }

    private JMenu menuManager() {
        JMenu menu = new JMenu("Manager");
        listMenu.clear();
        listMenu.add(new MenuItems("Chuyên đề", new Object[]{"/image/Lists.png", KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_DOWN_MASK)}));
        listMenu.add(new MenuItems("Khóa học", new Object[]{"/image/Certificate.png", KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK)}));
        listMenu.add(new MenuItems("Người học", new Object[]{"/image/Conference.png", KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.CTRL_DOWN_MASK)}));
        listMenu.add(new MenuItems("Học viên", new Object[]{"/image/User.png", KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK)}));
        listMenu.add(new MenuItems("Nhân viên", new Object[]{"/image/User group.png", KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_DOWN_MASK)}));
        listMenu.forEach(mn -> {
            mn.menuItem.setIcon(XImage.getAppImagelcon((String) mn.getProperties()[0]));
            mn.menuItem.setAccelerator((KeyStroke) mn.getProperties()[1]);
            mn.menuItem.addActionListener(amb);
            menu.add(mn.menuItem);
        });
        return menu;
    }

    private JMenu menuThongKe() {
        JMenu menu = new JMenu("Thống kê");
        listMenu.clear();
        listMenu.add(new MenuItems("Table Grade", new Object[]{"/image/Card file.png", KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.SHIFT_DOWN_MASK)}));
        listMenu.add(new MenuItems("Quantity Learner", new Object[]{"/image/Clien list.png", KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.SHIFT_DOWN_MASK)}));
        listMenu.add(new MenuItems("Grade CD", new Object[]{"/image/Bar chart.png", KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.SHIFT_DOWN_MASK)}));
        if(Auth.isManager()){
            listMenu.add(new MenuItems("Turnover", new Object[]{"/image/Dollar.png", KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.SHIFT_DOWN_MASK)}));
        }
        listMenu.forEach(mn -> {
            mn.menuItem.setIcon(XImage.getAppImagelcon((String) mn.getProperties()[0]));
            mn.menuItem.setAccelerator((KeyStroke) mn.getProperties()[1]);
            mn.menuItem.addActionListener(amb);
            menu.add(mn.menuItem);
        });
        return menu;
    }

    private JMenu menuSupport() {
        listMenu.clear();
        listMenu.add(new MenuItems("Guideline", new Object[]{"/image/Globe.png"}));
        listMenu.add(new MenuItems("Introduce", new Object[]{"/image/Brick house.png"}));
        listMenu.add(new MenuItems("Call me", new Object[]{"/image/call.png"}));
        JMenu menu = new JMenu("Support");
        listMenu.forEach(mn -> {
            mn.menuItem.setIcon(XImage.getAppImagelcon((String) mn.getProperties()[0]));
            mn.menuItem.addActionListener(amb);
            menu.add(mn.menuItem);
        });
        return menu;
    }

    List<Buttons> listTBar = new ArrayList<>();

    private JToolBar jToolBar() {
        listTBar.add(new Buttons("Log out", new Object[]{"/image/openDoor.png", true}));
        listTBar.add(new Buttons("Stop", new Object[]{"/image/stop.png", false}));
        listTBar.add(new Buttons("Chuyên đề", new Object[]{"/image/Lists.png", true}));
        listTBar.add(new Buttons("Người học", new Object[]{"/image/Conference.png", true}));
        listTBar.add(new Buttons("Khóa học", new Object[]{"/image/Certificate.png", true}));
        listTBar.add(new Buttons("Học viên", new Object[]{"/image/User.png", false}));
        listTBar.add(new Buttons("Hướng dẫn", new Object[]{"/image/Globe.png", true}));
        JToolBar toolBar = new JToolBar();
        listTBar.forEach(nut -> {
            nut.btn = new JButton((String) nut.getName(), XImage.getAppImagelcon((String) nut.getObj()[0]));
            nut.btn.addActionListener(amb);
            toolBar.add(nut.btn);
            if (!(boolean) nut.getObj()[1]) {
                toolBar.add(new JToolBar.Separator());
            }
        });
        return toolBar;
    }

    private JLabel body() {
        int width = 750;
        int height = 450;
        JLabel lbl = new JLabel();
        lbl.setSize(width, height);
        lbl.setBackground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setPreferredSize(new Dimension(width, height));
        lbl.setIcon(XImage.reSizeImgae("/image/ongPoly.png", lbl));
        return lbl;
    }

    JLabel lblTime;

    private JPanel last() {
        JPanel pnl = new JPanel();
        pnl.setBackground(Color.red);
        pnl.setOpaque(true);

        lblTime = new JLabel(XImage.getAppImagelcon("/image/clock.png"));
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date now = new Date();
                lblTime.setText(XDate.toString(now,"hh:mm:ss a"));
            }
        }).start();

        JLabel lbl = new JLabel(XImage.getAppImagelcon("/image/i.png"));
        lbl.setText("Hệ thống quản lý đào tạo");

        pnl.setLayout(new BorderLayout());
        pnl.add(lbl, BorderLayout.WEST);
        pnl.add(lblTime, BorderLayout.EAST);
        return pnl;
    }

}
