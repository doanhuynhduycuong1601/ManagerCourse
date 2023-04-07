
package poly.com.entity;

public class ChuyenDe {
    private String maCD;
    private String nameCD;
    private double fee;
    private int times;
    private String images;
    private String describe;

    public ChuyenDe() {
    }

    public ChuyenDe(String maCD, String nameCD, double fee, int times, String images, String describe) {
        this.maCD = maCD;
        this.nameCD = nameCD;
        this.fee = fee;
        this.times = times;
        this.images = images;
        this.describe = describe;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getNameCD() {
        return nameCD;
    }

    public void setNameCD(String nameCD) {
        this.nameCD = nameCD;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return nameCD;
    }

    @Override
    public boolean equals(Object obj) {
        ChuyenDe other = (ChuyenDe)obj;
        return other.getMaCD().equals(this.maCD);
    }
    
    
    
    
    
}
