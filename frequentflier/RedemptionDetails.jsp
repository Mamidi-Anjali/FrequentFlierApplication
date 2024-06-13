<%@page import="java.sql.*" %>
<%@page import="jakarta.servlet.*" %>
<%@page import="jakarta.servlet.http.*" %>
<%@page import="jakarta.servlet.annotation.*" %>
<%@page import="java.io.*" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.Vector" %>

<%
    String awardid = request.getParameter("awardid");
    String pid = request.getParameter("pid");
    String connectionUrl = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(connectionUrl, "bchanumo", "ubyshard");
    Statement stmt = conn.createStatement();
    String query="SELECT a.a_description, a.points_required, rh.redemption_date, ec.center_name FROM Redemption_History rh JOIN Awards a ON rh.award_id = a.award_id JOIN ExchgCenters ec ON rh.center_id = ec.center_id WHERE rh.passid ="+pid+" AND rh.award_id ="+awardid;
    boolean hasResultSet = stmt.execute(query);
    ResultSet rs = stmt.getResultSet();
    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();
    String res="";
    Vector<Vector<Object>> data = new Vector<>();
    while (rs.next()) {
        Vector<Object> row = new Vector<>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            int i=columnIndex;
            if(i==1 || i==3 || i==4){
                res+=rs.getObject(columnIndex).toString();
            }
            else if(i==2){
                res+=((BigDecimal)rs.getObject(columnIndex)).intValue();
            }
            res+=",";
        }
       res+="#";
    }
    out.print(res);
    conn.close();
%>