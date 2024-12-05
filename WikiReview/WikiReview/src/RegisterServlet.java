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
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            // Look up the DataSource in the JNDI environment
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/UserDB");
        } catch (NamingException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Hash the password using PasswordUtil
        String hashedPassword = PasswordUtil.hashPassword(password);

        try (Connection conn = dataSource.getConnection()) {
            // Prepare the SQL statement for inserting the user
            String insertUser = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertUser)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                stmt.executeUpdate();

                // Send a success response
                response.getWriter().println("Registration successful! <a href=\"login.jsp\">Login here</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error during registration. Try again.");
        }
    }
}
