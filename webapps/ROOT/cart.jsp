<%@ page import="com.example.models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.cart.Cart" %>
<%@ page import="com.example.cart.CartItem" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");

    if (cart == null || cart.getItems().isEmpty()) {
%>
<h2>Your cart is empty.</h2>
<a href="index.jsp">Continue shopping</a>
<%
} else {
%>

<table border="1">
    <tr>
        <th>Product</th>
        <th>Qty</th>
        <th>Subtotal</th>
    </tr>

    <%
        for (CartItem item : cart.getItems()) {
    %>
    <tr>
        <td><%= item.getProduct().getName() %></td>
        <td><%= item.getQuantity() %></td>
        <td>$<%= item.getSubtotal() %></td>
    </tr>
    <%
        }
    %>
</table>

<p>Total: $<%= cart.getTotal() %></p>

<a href="index.jsp">Continue shopping</a> |
<a href="checkout.jsp">Proceed to checkout</a>

<%
    }
%>
