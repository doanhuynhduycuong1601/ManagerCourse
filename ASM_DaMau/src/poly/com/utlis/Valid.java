package poly.com.utlis;

import java.awt.Color;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.text.JTextComponent;
import poly.com.layout.FormInput;

public class Valid {

    private String loi;
    private final String isEmpty = " cann't be empty";
    private final String reMatch = " malformed";
    private final String number = " has to be number";
    private final String bigger = " has to bigger than ";
    private final String lower = " has to lower than ";

    public Valid() {
        this.loi = "";
    }

    public String getLoi() {
        return loi;
    }

    public void setLoi(String loi) {
        this.loi = loi;
    }

    public void loi(FormInput form, String msg) {
        form.txt.setBackground(Color.YELLOW);
        form.lblError.setText(msg);
        loi += msg + "\n";
    }

    public void dung(FormInput form) {
        form.txt.setBackground(Color.WHITE);
        form.lblError.setText(" ");
    }

    public boolean isEmpty(FormInput form, String name) {
        String x = ((JTextComponent) form.txt).getText().trim();
        if (x.equals("")) {
            loi(form, name + isEmpty);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean compare(FormInput form, String msg, String value) {
        if (!form.getText().trim().equalsIgnoreCase(value)) {
            loi(form, msg);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean reMatch(FormInput form, String name, String reString) {
        String x = form.getText().trim();
        if (!x.matches(reString)) {
            loi(form, name + reMatch);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean reMatchLower(FormInput form, String name, String reString) {
        String x = Normalizer.normalize(form.getText().trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase().replaceAll("đ", "d");
        if (!x.matches(reString)) {
            loi(form, name + reMatch);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean number(FormInput form, String name) {
        try {
            Double.parseDouble(form.getText());
        } catch (NumberFormatException num) {
            loi(form, name + number);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean lower(FormInput form, String name, Double so) {
        double x = Double.parseDouble(form.getText().trim());
        if (x >= so) {
            loi(form, name + lower + so);
            return false;
        }

        dung(form);
        return true;
    }

    public boolean lowerEqual(FormInput form, String name, Double so) {
        double x = Double.parseDouble(form.getText().trim());
        if (x > so) {
            loi(form, name + lower + so);
            return false;
        }

        dung(form);
        return true;
    }

    public boolean bigger(FormInput form, String name, Double so) {
        double x = Double.parseDouble(form.getText().trim());
        if (x <= so) {
            loi(form, name + bigger + so);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean biggerEqual(FormInput form, String name, Double so) {
        double x = Double.parseDouble(form.getText().trim());
        if (x < so) {
            loi(form, name + bigger + so);
            return false;
        }
        dung(form);
        return true;
    }

    public boolean parse(FormInput form, String pattern, String msg) {
        try {
            SimpleDateFormat formater = new SimpleDateFormat();
            formater.applyPattern(pattern);
            Date ngay = formater.parse(form.getText());
            dung(form);
            return true;
        } catch (Exception e) {
            loi(form, msg);
        }
        return false;
    }

    public boolean multireMatch(FormInput form, String name, String reString) {
        if (isEmpty(form, name)) {
            return reMatch(form, name, reString);
        }
        return false;
    }

    public boolean multireMatchLower(FormInput form, String name, String reString) {
        if (isEmpty(form, name)) {
            return reMatchLower(form, name, reString);
        }
        return false;
    }
    
    public boolean multiNumber(FormInput form, String name){
        if(isEmpty(form, name)){
            return number(form, name);
        }
        return false;
    }
    
    public boolean multiBigger(FormInput form, String name, Double so){
        if(multiNumber(form, name)){
            return bigger(form, name, so);
        }
        return false;
    }
    
    public boolean multiLower(FormInput form, String name, Double so){
        if(multiNumber(form, name)){
            return lower(form, name, so);
        }
        return false;
    }

    public String reDate() {
        return "^([0-9]{4}[-/]?((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00)[-/]?02[-/]?29)$";
    }

    public String rePhone() {
        return "^0[1-9]\\d{8}$";
    }

    public String reEmail() {
        return "^\\w{3,}+@\\w{3,}+(\\.\\w{2,}+){1,2}$";
    }

    public String reName() {
        return "^[0-9a-zA-Z ]{3,40}$";
    }
}
