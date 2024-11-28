<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WikiReview - Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('https://png.pngtree.com/thumb_back/fh260/back_our/20190621/ourmid/pngtree-light-fresh-and-fresh-literary-food-banner-background-image_184091.jpg');
            background-size: cover; 
            background-position: center;
            color: #333; 
            height: 100vh;
            position: relative;
        }

        h1 {
            font-size: 3em;
            text-align: center;
            position: absolute;
            top: 30%; 
            left: 50%;
            transform: translate(-50%, -50%);
            color: #1537e1;
            white-space: nowrap;
        }

        .nav-links {
            text-align: center;
            position: absolute;
            top: 50%; 
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .nav-links a {
            text-decoration: none;
            color: #333;
            font-size: 1.4em;
            margin: 0 15px;
        }

        .nav-links a:hover {
            color: #ed3a3a;
            text-decoration: underline;
        }

        @media (max-width: 768px) {
            h1 {
                font-size: 2em;
            }
            .nav-links a {
                font-size: 1em;
                display: block;
                margin: 10px 0;
            }
        }
    </style>
</head>
<body>
    <h1>Welcome to WikiReview</h1>
    <div class="nav-links">
        <a href="register.jsp">Register</a> |
        <a href="login.jsp">Login</a> |
        <a href="addPage.jsp">Add a Page</a> |
        <a href="ListPagesServlet">View All Products</a>

    </div>
</body>
</html>


