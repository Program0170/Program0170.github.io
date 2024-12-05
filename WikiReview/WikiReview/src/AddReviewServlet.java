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

public class AddReviewServlet extends HttpServlet {
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
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized: Please log in to submit a review.");
            return;
        }

        // Get user_id from session
        int userId = (int) session.getAttribute("user_id");

        // Get form data
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        // Validate rating
        if (rating < 1 || rating > 5) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid rating. Please submit a rating between 1 and 5.");
            return;
        }

        try (Connection conn = dataSource.getConnection()) {
            // Check if the user has already reviewed the product
            String checkReviewQuery = "SELECT id FROM reviews WHERE product_id = ? AND user_id = ?";
            boolean isExistingReview = false;
            try (PreparedStatement stmt = conn.prepareStatement(checkReviewQuery)) {
                stmt.setInt(1, productId);
                stmt.setInt(2, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    isExistingReview = rs.next();
                }
            }

            if (isExistingReview) {
                // Update existing review
                String updateReviewQuery = "UPDATE reviews SET rating = ?, comment = ? WHERE product_id = ? AND user_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateReviewQuery)) {
                    stmt.setInt(1, rating);
                    stmt.setString(2, comment);
                    stmt.setInt(3, productId);
                    stmt.setInt(4, userId);
                    stmt.executeUpdate();
                }
            } else {
                // Insert new review
                String insertReviewQuery = "INSERT INTO reviews (product_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertReviewQuery)) {
                    stmt.setInt(1, productId);
                    stmt.setInt(2, userId);
                    stmt.setInt(3, rating);
                    stmt.setString(4, comment);
                    stmt.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error adding/updating review: " + e.getMessage());
            return;
        }

        // Redirect back to the product page
        response.sendRedirect("viewPage?id=" + productId);
    }
}

