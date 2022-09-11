package Info;

import java.util.ArrayList;

public class WifiInfo {
    private int list_total_count;
    private Result RESULT;
    private ArrayList<WifiItem> row;

    public int getList_total_count() {
        return list_total_count;
    }

    public void setList_total_count(int list_total_count) {
        this.list_total_count = list_total_count;
    }

    public Result getRESULT() {
        return RESULT;
    }

    public void setRESULT(Result RESULT) {
        this.RESULT = RESULT;
    }

    public ArrayList<WifiItem> getRow() {
        return row;
    }

    public void setRow(ArrayList<WifiItem> row) {
        this.row = row;
    }
}
