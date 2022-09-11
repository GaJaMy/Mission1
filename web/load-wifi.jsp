<%@ page import="Info.TbPublicWifiInfo" %>
<%@ page import="db.DBService" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="Info.WifiInfo" %>
<%@ page import="Info.WifiItem" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %><%--
  Created by IntelliJ IDEA.
  User: 서형동
  Date: 2022-09-01
  Time: 오후 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <%
        try {
            TbPublicWifiInfo tbPublicWifiInfo;

            DBService dbService = new DBService();
            dbService.DBConnect();
            int count = dbService.getNumOfData("wifi_info");
            if (count == 0) {
                while (true) {
                    String start = Integer.toString(count + 1);
                    String end = Integer.toString(count + 1000);

                    StringBuilder urlBuilder;
                    urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
                    urlBuilder.append("/" + URLEncoder.encode("50796d56656d616e33375949465442", "UTF-8"));
                    urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
                    urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8"));
                    urlBuilder.append("/" + URLEncoder.encode(start, "UTF-8"));
                    urlBuilder.append("/" + URLEncoder.encode(end, "UTF-8") + "/");

                    URL url = new URL(urlBuilder.toString());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-type", "application/json");

                    BufferedReader rd;
                    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                        rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    } else {
                        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                    }

                    String line;
                    if ((line = rd.readLine()) != null) {
                        Gson gson = new Gson();
                        tbPublicWifiInfo = gson.fromJson(line, TbPublicWifiInfo.class);

                        if (tbPublicWifiInfo != null) {
                            WifiInfo wifiInfo = tbPublicWifiInfo.getTbPublicWifiInfo();

                            if (wifiInfo == null) {
                                break;
                            }

                            for (WifiItem item : wifiInfo.getRow()) {
                                dbService.dbinsert(item);
                                count++;
                            }
                        }
                    }

                    rd.close();
                    conn.disconnect();
                }
            }

            dbService.DBDisconnect();

            out.write("<h1 style = \"text-align:center\">" + count + "개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>");
            out.write("<div style=\"position:static;text-align:center;width:100%;height:100%;top:0;left:0;\"> <a href=\"javascript:history.back();\">홈 으로</a></div>");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    %>
</body>
</html>
