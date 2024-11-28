import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT id, password_hash FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedHash = rs.getString("password_hash");
                        int userId = rs.getInt("id");

                        // Check if the entered password matches the stored hashed password
                        if (PasswordUtil.checkPassword(password, storedHash)) {
                            // Set user_id and username in session
                            HttpSession session = request.getSession();
                            session.setAttribute("user_id", userId);
                            session.setAttribute("username", username);

                            response.getWriter().println("Login successful! Welcome to WikiReview.");
                            return;
                        } else {
                            response.getWriter().println("Invalid username or password.");
                        }
                    } else {
                        response.getWriter().println("User not found.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error accessing the database.");
        }
    }
}
