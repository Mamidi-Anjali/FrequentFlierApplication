<%@page import="java.sql.*" %>
<%@page import="jakarta.servlet.*" %>
<%@page import="jakarta.servlet.http.*" %>
<%@page import="jakarta.servlet.annotation.*" %>
<%@page import="java.io.*" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.Vector" %>

<%
    String flightid = request.getParameter("flightid");
    String connectionUrl = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(connectionUrl, "bchanumo", "ubyshard");
    Statement stmt = conn.createStatement();
    String query="SELECT f.dept_datetime, f.arrival_datetime, f.flight_miles, t.trip_id, t.trip_miles FROM Flights f JOIN Flights_Trips ft ON f.flight_id = ft.flight_id JOIN Trips t ON ft.trip_id = t.trip_id WHERE f.flight_id = '"+flightid+"'";
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
            if(i==1 || i==2 || i==4){
                res+=rs.getObject(columnIndex).toString();
            }
            else if(i==3 || i==5){
                res+=((BigDecimal)rs.getObject(columnIndex)).intValue();
            }
            res+=",";
        }
        res+="#";
    }
    out.print(res);
    conn.close();
%>