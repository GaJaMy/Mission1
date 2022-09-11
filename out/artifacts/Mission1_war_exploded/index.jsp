<%@ page import="db.DBService" %>
<%@ page import="java.util.List" %>
<%@ page import="Info.WifiItem" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="Info.MyLoccation" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: 서형동
  Date: 2022-08-26
  Time: 오전 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>와이파이 정보 구하기</title>
    <link href="CSS\index.css" rel="stylesheet" type="text/css">
  </head>
  <body>

      <script>
          function getMyLocation() {
              navigator.geolocation.getCurrentPosition(
                  function (position) {
                      var lnt = position.coords.latitude;
                      var lat = position.coords.longitude;

                      document.getElementById("loc-x").value = lat;
                      document.getElementById("loc-y").value = lnt;
                  }
              )
          }
      </script>
      <h1>와이파이 정보 구하기</h1>
      <div class="index">
          <span>
              <a href="">홈</a>
          </span>
          <span>
              <a href="showhistory.jsp">위치 히스토리 목록</a>
          </span>
          <span>
              <a href="load-wifi.jsp">Open API 와이파이 가져오기</a>
          </span>
      </div>
      <div class="input-loc">
          <%
              boolean checker = false;
              String LAT = request.getParameter("LAT");
              String LNT = request.getParameter("LNT");
              if(LAT == null) {
                  LAT = "0.0";
              }

              if(LNT == null) {
                  LNT = "0.0";
              }

              if(!LAT.equals("0.0") && !LNT.equals("0.0")) {
                  checker = true;
              }
          %>
          <form action="index.jsp" method="get">
              <span>LAT:</span>
              <input class="input-loc-x" id="loc-x" name="LAT" type="text" value="<%=LAT%>">
              <span>LNT:</span>
              <input class="input-loc-y" id="loc-y" name="LNT" type="text" value="<%=LNT%>" >
              <input type="button" value="내 위치 가져오기" onclick="getMyLocation()">
              <input type="submit" value="근처 WIPI 정보 보기">
          </form>
      </div>
      <div class="list-area">
          <table>
              <thead>
              <tr>
                    <th>거리(Km)</th>
                    <th>관리번호</th>
                    <th>자치구</th>
                    <th>와이파이명</th>
                    <th>도로명주소</th>
                    <th>상세주소</th>
                    <th>설치위치(중)</th>
                    <th>설치유형</th>
                    <th>설치기관</th>
                    <th>서비스구분</th>
                    <th>망종류</th>
                    <th>설치년도</th>
                    <th>실내외구분</th>
                    <th>WIFI접속환경</th>
                    <th>X좌표</th>
                    <th>Y좌표</th>
                    <th>작업일자</th>
              </tr>
              </thead>
              <tbody>
              <%
                    if(checker) {
                        DBService dbService = new DBService();
                        dbService.DBConnect();
                        int historyID = dbService.getNumOfData("history");
                        Date nowDate = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        String time = simpleDateFormat.format(nowDate);

                        MyLoccation myLoccation = new MyLoccation(historyID+1,time,Double.parseDouble(LAT),Double.parseDouble(LNT));
                        dbService.dbInsertMyLoc(myLoccation);
                        dbService.dbInsertHistory(myLoccation);

                        List<WifiItem> items = dbService.getNearWifiInfo();
                        dbService.DBDisconnect();

                        List<WifiItem> filteredItems = new ArrayList<>();
                        for (WifiItem item : items) {
                            Double lat1 = Double.parseDouble(LAT);
                            Double lon1 = Double.parseDouble(LNT);
                            Double lat2 = item.getLAT();
                            Double lon2 = item.getLNT();


                            double theta = lon1 - lon2;
                            double dist = Math.sin(lat1 * Math.PI / 180) * Math.sin(lat2 * Math.PI / 180) +
                                    Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.cos(theta * Math.PI / 180);
                            dist = Math.acos(dist);
                            dist = dist * 180 / Math.PI;
                            dist = dist * 60 * 1.1515;
                            dist = dist * 1.609344;
                            if (dist <= 6) {
                                item.setDist(dist);
                                filteredItems.add(item);
                            }
                        }
                        Collections.sort(filteredItems);

                        for (WifiItem item:filteredItems) {
              %>
              <tr>
                  <td><%=String.format("%.4f",item.getDist())%></td>
                  <td><%=item.getX_SWIFI_MGR_NO()%></td>
                  <td><%=item.getX_SWIFI_WRDOFC()%></td>
                  <td><%=item.getX_SWIFI_MAIN_NM()%></td>
                  <td><%=item.getX_SWIFI_ADRES1()%></td>
                  <td><%=item.getX_SWIFI_ADRES2()%></td>
                  <td><%=item.getX_SWIFI_INSTL_FLOOR()%></td>
                  <td><%=item.getX_SWIFI_INSTL_TY()%></td>
                  <td><%=item.getX_SWIFI_INSTL_MBY()%></td>
                  <td><%=item.getX_SWIFI_SVC_SE()%></td>
                  <td><%=item.getX_SWIFI_CMCWR()%></td>
                  <td><%=item.getX_SWIFI_CNSTC_YEAR()%></td>
                  <td><%=item.getX_SWIFI_INOUT_DOOR()%></td>
                  <td><%=item.getX_SWIFI_REMARS3()%></td>
                  <td><%=item.getLAT()%></td>
                  <td><%=item.getLNT()%></td>
                  <td><%=item.getWORK_DTTM()%></td>
              </tr>
              <%
                      }
                    } else {
              %>
              <tr style="text-align: center">
                  <td colspan="17" style="background-color: white">위치 정보를 입력한 후에 조회해 주세요</td>
              </tr>
              <%
                  }
              %>
              </tbody>
          </table>
      </div>
  </body>
</html>
