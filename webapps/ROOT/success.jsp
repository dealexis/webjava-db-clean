<%@ page import="com.example.models.Order" %>
<%@ page import="com.example.models.OrderItem" %>
<%@ page import="com.example.dao.OrderDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Order page</title>
</head>
<body>
<%
    Order order = (Order) request.getAttribute("order");

    if (order == null) {
%>
<p>No order details available.</p>
<a href="index.jsp">Back to shop</a>
<%
} else {
%>
<h2>Order Placed Successfully</h2>

<p><strong>Order ID:</strong> <%= order.getId() %></p>
<p><strong>Total:</strong> $<%= order.getTotalPrice() %></p>

<table border="1" cellpadding="5">
    <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
    </tr>

    <%
        for (OrderItem item : OrderDAO.findItemsByOrderId(order)) {
    %>
    <tr>
        <td><%= item.getProduct().getName() %></td>
        <td><%= item.getQuantity() %></td>
        <td>$<%= item.getProduct().getPrice() %></td>
        <td>$<%= item.getSubtotal() %></td>
    </tr>
    <%
        }
    %>
</table>
<% } %>
<a href="index.jsp">Continue shopping</a>
</body>
</html>