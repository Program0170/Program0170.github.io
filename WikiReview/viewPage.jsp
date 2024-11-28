<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Product</title>
    <style>
        .hidden {
            display: none;
        }
        .review-form {
            margin-top: 20px;
        }
    </style>
    <script>
        function toggleReviewForm() {
            const form = document.getElementById('reviewForm');
            form.classList.toggle('hidden');
        }
    </script>
</head>
<body>
    <h1><%= request.getAttribute("name") %></h1>
    <p>UPC: <%= request.getAttribute("upc") %></p>
    <p>Category: <%= request.getAttribute("category") %></p>
    <p>Description: <%= request.getAttribute("description") %></p>
    <img src="<%= request.getAttribute("imagePath") %>" alt="Product Image" />

    <%
        // Use the pre-declared JSP session variable
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        Integer userId = (session != null) ? (Integer) session.getAttribute("user_id") : null;
    %>

    <% if (username != null) { %>
        <!-- Button to toggle review form -->
        <button onclick="toggleReviewForm()">Write a Review</button>

        <!-- Review form -->
        <form id="reviewForm" class="hidden review-form" action="AddReviewServlet" method="post">
            <input type="hidden" name="product_id" value="<%= request.getParameter("id") %>">
            <label for="rating">Rating (1 to 5):</label>
            <input type="number" id="rating" name="rating" min="1" max="5" required>
            <br><br>
            <label for="comment">Comment:</label><br>
            <textarea id="comment" name="comment" rows="4" cols="50" required></textarea>
            <br><br>
            <button type="submit">Submit Review</button>
        </form>
    <% } else { %>
        <p><a href="login.jsp">Log in</a> to write a review.</p>
    <% } %>

    <h2>Reviews</h2>
    <div class="reviews">
        <%
            List<Object[]> reviews = (List<Object[]>) request.getAttribute("reviews");
            if (reviews == null || reviews.isEmpty()) {
        %>
            <p>No reviews yet.</p>
        <%
            } else {
                for (Object[] review : reviews) {
                    int rating = (int) review[0];
                    String comment = (String) review[1];
                    String reviewer = (String) review[2];
        %>
            <div class="review-item">
                <p>Rating: <%= rating %> / 5</p>
                <p>Comment: <%= comment %></p>
                <p>By: <%= reviewer %></p>
            </div>
        <%
                }
            }
        %>
    </div>
</body>
</html>


