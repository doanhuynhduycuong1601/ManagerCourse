
package poly.com.utlis;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class XImage {
    public static ImageIcon reSizeImgae(String path, JLabel lbl) {
        ImageIcon myImage = XImage.getAppImagelcon(path);
        Image img = myImage.getImage();
        Image newimg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newimg);
        return image;
    }
    
    public static ImageIcon reSizeImgae2(String path, JLabel lbl) {
        ImageIcon myImage = new ImageIcon(path);
        Image img = myImage.getImage();
        Image newimg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newimg);
        return image;
    }
    
    public static Image getApplcon(String path){
        URL url = XImage.class.getResource(path);
        return new ImageIcon(url).getImage();
    }
    
    public static ImageIcon getAppImagelcon(String path) {
        URL url = XImage.class.getResource(path);
        return new ImageIcon(url);
    }
    
    public static String read(String fileName){
        File path = new File("logos", fileName);
        return path.getAbsolutePath();
    }
    
    
    public static void save(File src){
        File dst = new File("logos", src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        
        try{
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
