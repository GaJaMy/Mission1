package Info;

import java.util.Date;

public class MyLoccation {
    private int HISTORY_ID;
    private String INQUIRY_DATE;
    private double LAT;
    private double LNT;

    public MyLoccation() {}
    public MyLoccation(int HISTORY_ID, String INQUIRY_DATE, double LAT, double LNT) {
        this.HISTORY_ID = HISTORY_ID;
        this.INQUIRY_DATE = INQUIRY_DATE;
        this.LAT = LAT;
        this.LNT = LNT;
    }

    public int getHISTORY_ID() {
        return HISTORY_ID;
    }

    public void setHISTORY_ID(int HISTORY_ID) {
        this.HISTORY_ID = HISTORY_ID;
    }

    public String getINQUIRY_DATE() {
        return INQUIRY_DATE;
    }

    public void setINQUIRY_DATE(String INQUIRY_DATE) {
        this.INQUIRY_DATE = INQUIRY_DATE;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNT() {
        return LNT;
    }

    public void setLNT(double LNT) {
        this.LNT = LNT;
    }
}
