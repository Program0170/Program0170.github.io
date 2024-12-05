<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add a Product</title>
</head>
<body>
    <h2>Add a Product to WikiReview</h2>
    <form action="AddPageServlet" method="post" enctype="multipart/form-data">
        <!-- Product Name -->
        <label for="product_name">Product Name:</label>
        <input type="text" id="product_name" name="productName" required>
        <br><br>

        <!-- UPC -->
        <label for="upc">UPC:</label>
        <input type="text" id="upc" name="upc" maxlength="13" required>
        <br><br>

        <!-- Category -->
        <label for="category">Category:</label>
        <input type="text" id="category" name="category">
        <br><br>

        <!-- Description -->
        <label for="description">Description:</label><br>
        <textarea id="description" name="description" rows="5" cols="50"></textarea>
        <br><br>

        <!-- Product Image Upload -->
        <label for="product_image">Upload Product Image:</label>
        <input type="file" id="product_image" name="productImage" accept=".png, .jpeg, .jpg" required>
        <br><br>

        <!-- Submit Button -->
        <button type="submit">Add Product</button>
    </form>
</body>
</html>


