<%@page import="java.sql.*" %>
<%@page import="jakarta.servlet.*" %>
<%@page import="jakarta.servlet.http.*" %>
<%@page import="jakarta.servlet.annotation.*" %>
<%@page import="java.io.*" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.Vector" %>

<%
    String pid = request.getParameter("pid");
    String connectionUrl = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(connectionUrl, "bchanumo", "ubyshard");
    Statement stmt = conn.createStatement();
    String query="SELECT p.pname, pa.total_points FROM Passengers p JOIN Point_Accounts pa ON p.passid = pa.passid WHERE p.passid="+pid;
    boolean hasResultSet = stmt.execute(query);
    ResultSet rs = stmt.getResultSet();
    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();
    Vector<Vector<Object>> data = new Vector<>();
    while (rs.next()) {
        Vector<Object> row = new Vector<>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            row.add(rs.getObject(columnIndex));
        }
        data.add(row);
    }
    int points=((BigDecimal)data.get(0).get(1)).intValue();
    String name = data.get(0).get(0).toString();
    out.print(name+","+points);
    conn.close();
%>