<%@page import="java.sql.*" %>
<%@page import="jakarta.servlet.*" %>
<%@page import="jakarta.servlet.http.*" %>
<%@page import="jakarta.servlet.annotation.*" %>
<%@page import="java.io.*" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.Vector" %>

<%
    String spid = request.getParameter("spid");
    String dpid = request.getParameter("dpid");
    String npoints = request.getParameter("npoints");
    String connectionUrl = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(connectionUrl, "bchanumo", "ubyshard");
    Statement stmt = conn.createStatement();
    String query="UPDATE Point_Accounts SET total_points = total_points-"+ npoints+" WHERE passid ="+spid;
    stmt.execute(query);
    query="UPDATE Point_Accounts SET total_points = total_points+"+ npoints+" WHERE passid ="+dpid;
    stmt.execute(query);
    out.print("success");
    conn.close();
%>