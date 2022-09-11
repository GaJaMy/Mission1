<%@ page import="db.DBService" %>
<%@ page import="Info.MyLoccation" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: 서형동
  Date: 2022-09-04
  Time: 오후 3:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="CSS\history.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h1>위치 히스토리 목록</h1>
    <div class="index">
              <span>
                  <a href="index.jsp">홈</a>
              </span>
        <span>
                  <a href="showhistory.jsp">위치 히스토리 목록</a>
              </span>
        <span>
                  <a href="load-wifi.jsp">Open API 와이파이 가져오기</a>
              </span>
    </div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <tbody>
            <%
                DBService dbService = new DBService();
                dbService.DBConnect();
                String historyID = request.getParameter("historyID");
                if(historyID != null) {
                    dbService.deleteHistoryItem(Integer.parseInt(historyID));
                }

                List<MyLoccation> hisotrys = dbService.getHistoyItem();
                dbService.DBDisconnect();

                if(hisotrys.size() == 0) {
            %>
                <tr style="text-align: center">
                    <td colspan="5" style="background-color: white">히스토리가 없습니다.</td>
                </tr>
            <%
                }
                for (MyLoccation historyItem : hisotrys) {
            %>
            <tr>
                <td><%=historyItem.getHISTORY_ID()%></td>
                <td><%=historyItem.getLAT()%></td>
                <td><%=historyItem.getLNT()%></td>
                <td><%=historyItem.getINQUIRY_DATE()%></td>
                <td>
                    <form method="post" action="showhistory.jsp">
                        <input type="hidden" name="historyID" value="<%=historyItem.getHISTORY_ID()%>">
                        <input value="삭제" type="submit">
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
