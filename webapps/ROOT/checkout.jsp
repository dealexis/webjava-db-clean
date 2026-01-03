<%@ page import="java.util.List" %>
<%@ page import="com.example.cart.Cart" %>
<%@ page import="com.example.cart.CartItem" %>
<html>
<head>
    <title>Checkout</title>
</head>
<body>

<h2>Checkout</h2>

<%
    Cart cart = (Cart) session.getAttribute("cart");

    if (cart == null || cart.getItems().isEmpty()) {
%>
<p>Your cart is empty.</p>
<a href="index.jsp">Back to shop</a>
<%
} else {
%>

<table border="1" cellpadding="5">
    <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
    </tr>

    <%
        for (CartItem item : cart.getItems()) {
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

<p><strong>Total: $<%= cart.getTotal() %></strong></p>

<form action="checkout" method="post">
    <input type="submit" value="Confirm Order">
</form>

<br>

<a href="cart.jsp">Back to Cart</a>

<%
    }
%>

</body>
</html>
