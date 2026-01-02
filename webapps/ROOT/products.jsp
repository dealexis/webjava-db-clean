<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.Product" %>
<html>
<head>
    <title>T-Shirts Orders</title>
</head>
<body>

<h2>Available T-Shirts</h2>

<table border="1" cellpadding="8">
    <tr>
        <th>Name</th>
        <th>Size</th>
        <th>Color</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Action</th>
    </tr>

    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null) {
            for (Product p : products) {
    %>
    <tr>
        <td><%= p.getName() %>
        </td>
        <td><%= p.getSize() %>
        </td>
        <td><%= p.getColor() %>
        </td>
        <td>$<%= p.getPrice() %>
        </td>
        <td><%= p.getStock() %>
        </td>
        <td>
            <a href="add-to-cart?id=<%= p.getId() %>">
                <button>
                    Add to 🛒 Cart
                </button>
            </a>
        </td>
    </tr>
    <%
            }
        }
    %>

</table>

</body>
</html>
