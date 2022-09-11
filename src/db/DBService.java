package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Info.MyLoccation;
import Info.WifiInfo;
import Info.WifiItem;

public class DBService {
    private Connection connection = null;
    private boolean isConnect = false;

    public void DBConnect() {
        String url = "jdbc:mariadb://localhost:3306/wifidb";
        String dbUserId = "wifiuser";
        String dbPassword = "1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void DBDisconnect() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumOfData(String dbName) {
        PreparedStatement preparedStatement = null;

        String sql = "select * " +
                " from " + dbName +" ";
        ResultSet rs = null;
        int numOfData = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);
            if(rs.last()) {
                numOfData = rs.getRow();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return numOfData;
    }
    public boolean dbinsert(WifiItem item) {
        PreparedStatement preparedStatement = null;

        String sql = " insert ignore into wifi_info " +
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,item.getX_SWIFI_MGR_NO());
            preparedStatement.setString(2,item.getX_SWIFI_WRDOFC());
            preparedStatement.setString(3,item.getX_SWIFI_MAIN_NM());
            preparedStatement.setString(4,item.getX_SWIFI_ADRES1());
            preparedStatement.setString(5,item.getX_SWIFI_ADRES2());
            preparedStatement.setString(6,item.getX_SWIFI_INSTL_FLOOR());
            preparedStatement.setString(7,item.getX_SWIFI_INSTL_TY());
            preparedStatement.setString(8,item.getX_SWIFI_INSTL_MBY());
            preparedStatement.setString(9,item.getX_SWIFI_SVC_SE());
            preparedStatement.setString(10,item.getX_SWIFI_CMCWR());
            preparedStatement.setString(11,item.getX_SWIFI_CNSTC_YEAR());
            preparedStatement.setString(12,item.getX_SWIFI_INOUT_DOOR());
            preparedStatement.setString(13,item.getX_SWIFI_REMARS3());
            preparedStatement.setDouble(14,item.getLAT());
            preparedStatement.setDouble(15,item.getLNT());
            preparedStatement.setString(16,item.getWORK_DTTM());

            int affected = preparedStatement.executeUpdate();

            if(affected <= 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }
    public List<WifiItem> getNearWifiInfo() {
        List<WifiItem> resultList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        String sql = " select * " +
                " from wifi_info; ";

        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                WifiItem item = new WifiItem();
                item.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                item.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                item.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                item.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                item.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                item.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                item.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                item.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                item.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                item.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                item.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                item.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                item.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                item.setLAT(rs.getDouble("LAT"));
                item.setLNT(rs.getDouble("LNT"));
                item.setWORK_DTTM(rs.getString("WORK_DTTM"));
                resultList.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return resultList;
    }
    public List<MyLoccation> getHistoyItem() {
        List<MyLoccation> resultList = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        String sql = " select * " +
                " from history; ";

        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                MyLoccation item = new MyLoccation();
                item.setHISTORY_ID(rs.getInt("HISTORY_ID"));
                item.setINQUIRY_DATE(rs.getString("INQUIRY_DATE"));
                item.setLAT(rs.getDouble("LAT"));
                item.setLNT(rs.getDouble("LNT"));
                resultList.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return resultList;
    }

    public boolean deleteHistoryItem(int historyID) {
        PreparedStatement preparedStatement = null;

        String sql = " delete from history " +
                " where HISTORY_ID = ?; ";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,historyID);

            int affected = preparedStatement.executeUpdate();

            if(affected <= 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return deleteMyLoc(historyID);
    }
    public boolean deleteMyLoc(int ID) {
        PreparedStatement preparedStatement = null;

        String sql = " delete from my_loc " +
                " where ID = ?; ";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,ID);

            int affected = preparedStatement.executeUpdate();

            if(affected <= 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }
    public boolean dbInsertHistory(MyLoccation loc) {
        PreparedStatement preparedStatement = null;

        String sql = " insert into history " +
                " values (?,?,?,?,?); ";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,loc.getHISTORY_ID());
            preparedStatement.setString(2,loc.getINQUIRY_DATE());
            preparedStatement.setDouble(3,loc.getLAT());
            preparedStatement.setDouble(4,loc.getLNT());
            preparedStatement.setInt(5,loc.getHISTORY_ID());

            int affected = preparedStatement.executeUpdate();

            if(affected <= 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    public boolean dbInsertMyLoc(MyLoccation loc) {
        PreparedStatement preparedStatement = null;

        String sql = " insert into my_loc " +
                " values (?,?,?,?); ";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,loc.getHISTORY_ID());
            preparedStatement.setString(2,loc.getINQUIRY_DATE());
            preparedStatement.setDouble(3,loc.getLAT());
            preparedStatement.setDouble(4,loc.getLNT());

            int affected = preparedStatement.executeUpdate();

            if(affected <= 0) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }
}
