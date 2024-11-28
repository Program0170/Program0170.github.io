import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewPageServlet extends HttpServlet {
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/UserDB");
        } catch (NamingException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageId = request.getParameter("id");

        try (Connection conn = dataSource.getConnection()) {
            // Updated SQL to match the pages table structure
            String sql = "SELECT name, upc, category, description, image_path FROM pages WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, pageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("name", rs.getString("name"));
                        request.setAttribute("upc", rs.getString("upc"));
			request.setAttribute("category", rs.getString("category"));
                        request.setAttribute("description", rs.getString("description"));
                        request.setAttribute("imagePath", rs.getString("image_path"));

                        // Forward the request to the JSP
                        request.getRequestDispatcher("viewPage.jsp").forward(request, response);
                    } else {
                        response.getWriter().println("Page not found. <a href=\"index.jsp\">Back to Home</a>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error loading page. Please try again later.");
        }
    }
}

