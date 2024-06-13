
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
    String query="SELECT DISTINCT rh.award_id FROM Redemption_History rh WHERE rh.passid ="+pid+"";
    boolean hasResultSet = stmt.execute(query);
    ResultSet rs = stmt.getResultSet();
    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();
    Vector<Vector<Object>> data = new Vector<>();
    String res="";
    while (rs.next()) {
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            int Aid =((BigDecimal)rs.getObject(columnIndex)).intValue();
            res+=Aid;
            res+=",";
        }
    }    
    res=res.substring(0,res.length()-1);
    out.print(res);
    conn.close();
%>