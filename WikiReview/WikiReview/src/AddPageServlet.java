import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AddPageServlet extends HttpServlet {
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            // Look up the DataSource in the JNDI environment
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/UserDB"); // Replace "UserDB" with your DataSource name
        } catch (NamingException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form fields
        String productName = request.getParameter("productName");
        String upc = request.getParameter("upc");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        Part filePart = request.getPart("productImage");

        // Validate input
        if (productName == null || upc == null || filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("Missing product information or image.");
            return;
        }

        // Handle file upload
        String fileName = extractFileName(filePart);
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        // Create uploads directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Save the uploaded file
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Save product details in the database
        try (Connection conn = dataSource.getConnection()) {
            String insertProduct = "INSERT INTO pages (name, upc, category, description, image_path) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertProduct)) {
                stmt.setString(1, productName);
                stmt.setString(2, upc);
                stmt.setString(3, category);
                stmt.setString(4, description);
                stmt.setString(5, "uploads/" + fileName); // Save relative file path

                stmt.executeUpdate();
                response.getWriter().println("Product added successfully!");
            }
        } catch (SQLException e) {
             e.printStackTrace(); // Logs the full stack trace for debugging
    	     response.getWriter().println("Error adding product: " + e.getMessage());
        }
    }

    // Utility method to extract file name from HTTP header content-disposition
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String cd : contentDisp.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}

