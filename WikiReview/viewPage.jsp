<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${name}</title>
</head>
<body>
    <h1>${name}</h1>
    <p><strong>Category:</strong> ${category}</p>
    <p><strong>UPC:</strong> ${upc}</p>
    <p><strong>Description:</strong> ${description}</p>
    <c:if test="${imagePath != null}">
        <p><img src="${imagePath}" alt="${name}" style="max-width: 300px; max-height: 300px;"></p>
    </c:if>
    <p><a href="index.jsp">Back to Home</a></p>
</body>
</html>
