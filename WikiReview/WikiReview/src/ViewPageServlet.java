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
import java.util.ArrayList;
import java.util.List;

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
        if (pageId == null) {
            response.getWriter().println("Invalid product ID.");
            return;
        }

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("user_id") : null;

        try (Connection conn = dataSource.getConnection()) {
            // Fetch product details
            String productQuery = "SELECT name, upc, category, description, image_path FROM pages WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(productQuery)) {
                stmt.setString(1, pageId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("name", rs.getString("name"));
                        request.setAttribute("upc", rs.getString("upc"));
                        request.setAttribute("category", rs.getString("category"));
                        request.setAttribute("description", rs.getString("description"));
                        request.setAttribute("imagePath", rs.getString("image_path"));
                    } else {
                        response.getWriter().println("Product not found.");
                        return;
                    }
                }
            }

            // Fetch the logged-in user's review, if any
            if (userId != null) {
                String userReviewQuery = "SELECT rating, comment FROM reviews WHERE product_id = ? AND user_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(userReviewQuery)) {
                    stmt.setInt(1, Integer.parseInt(pageId));
                    stmt.setInt(2, userId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            request.setAttribute("userReview", new Object[]{rs.getInt("rating"), rs.getString("comment")});
                        }
                    }
                }
            }

            // Fetch all other reviews
            String reviewQuery = "SELECT rating, comment, username FROM reviews JOIN users ON reviews.user_id = users.id WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(reviewQuery)) {
                stmt.setInt(1, Integer.parseInt(pageId));
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Object[]> reviews = new ArrayList<>();
                    while (rs.next()) {
                        reviews.add(new Object[]{rs.getInt("rating"), rs.getString("comment"), rs.getString("username")});
                    }
                    request.setAttribute("reviews", reviews);
                }
            }

            // Forward to JSP
            request.getRequestDispatcher("viewPage.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading product details. Please try again later.");
        }
    }
}


