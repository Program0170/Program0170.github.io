<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('https://i.pinimg.com/736x/33/ef/8b/33ef8b9c0b902154a6cd4103a21275ef.jpg');
            background-size: cover;
            background-position: center; 
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 50vh;
            color: #333;
            position: relative;
        }

        h2 {
            text-align: center;
            color: #3204bb;
            margin-top: 100px;
            margin-bottom: 50px;
            font-size: 40px;
        }

        form {
            background-color: #fff;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            margin-top: 350px;
            position: absolute;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #333;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #f02525;
        }
    </style>
</head>
<body>
    <h2>Register with WikiReview</h2>
    <form action="register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Register</button>
    </form>
</body>
</html>