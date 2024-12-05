<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }

        h1 {
            text-align: center;
            margin: 20px 0;
            color: #333;
        }

        .container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
            padding: 20px;
        }

        .card {
            width: 250px;
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            text-align: center;
        }

        .card img {
            max-width: 100%;
            height: auto;
        }

        .card-content {
            padding: 15px;
        }

        .card-content h2 {
            font-size: 16px;
            margin: 10px 0;
            color: #333;
        }

        .card-content .btn {
            display: inline-block;
            padding: 10px 20px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .card-content .btn:hover {
            background: #0056b3;
        }

        .footer {
            text-align: center;
            margin: 20px;
        }

        .footer a {
            color: #007bff;
            text-decoration: none;
        }

        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>List of Products</h1>

    <div class="container">
        <% 
            List<Object[]> pages = (List<Object[]>) request.getAttribute("pages");
            for (Object[] pageData : pages) {
                int id = (int) pageData[0];
                String name = (String) pageData[1];
                String imagePath = (String) pageData[2];
        %>
            <div class="card">
                <% if (imagePath != null && !imagePath.isEmpty()) { %>
                    <img src="<%= imagePath %>" alt="Image of <%= name %>" class="card-img">
                <% } else { %>
                    <img src="https://via.placeholder.com/250x150?text=No+Image" alt="Placeholder" class="card-img">
                <% } %>
                <div class="card-content">
                    <h2><%= name %></h2> <!-- Display product name -->
                    <a href="viewPage?id=<%= id %>" class="btn">View Details</a> <!-- Use ID for navigation -->
                </div>
            </div>
        <% 
            }
        %>
    </div>

    <div class="footer">
        <a href="index.jsp">Back to Home</a>
    </div>
</body>
</html>

