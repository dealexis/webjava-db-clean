<%@ page import="com.example.models.Product" %><%@ page import="com.example.dao.ProductDAO" %><%@ page import="java.util.List" %><%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>T-Shirts Orders</title>
</head>
<body>
<%
    List<Product> products = ProductDAO.findAll();
    request.setAttribute("products", products);
    request.getRequestDispatcher("products.jsp").forward(request, response);
%>
</body>
</html>
