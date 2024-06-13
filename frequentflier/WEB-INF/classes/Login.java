import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

@WebServlet("/login")
public class Login extends HttpServlet {

    private String getPID(Connection conn, String user, String pass) {
        try (Statement stmt = conn.createStatement()) {
            String query = "select passid from Login where username=\'"+user+"\' and passwd=\'"+pass+"\'";
            boolean hasResultSet = stmt.execute(query);

            if (hasResultSet) {
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
                return (((BigDecimal)data.get(0).get(0)).intValue())+"";
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            String user = req.getParameter("user");
            String pass = req.getParameter("pass");
            PrintWriter out = res.getWriter();
            String connectionUrl = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
            Connection conn = DriverManager.getConnection(connectionUrl, "bchanumo", "ubyshard");
            String PID = getPID(conn, user, pass);
            if (!"".equals(PID)) {
                out.println("Yes:" + PID);
            } else {
                out.println("No");
            }
            conn.close();
        } catch (Exception e) {
        }
    }
}
