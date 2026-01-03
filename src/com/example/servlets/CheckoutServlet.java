package com.example.servlets;

import com.example.dao.OrderDAO;
import com.example.cart.Cart;
import com.example.models.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckoutServlet extends HttpServlet {
    // todo doGet?

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("empty-cart.jsp");
            return;
        }

        int orderId;

        try {
            orderId = OrderDAO.create(cart);
        } catch (Exception e) {
            // log caught exception
            response.sendRedirect("error.jsp");
            return;
        }

        session.removeAttribute("cart");

        Order order = OrderDAO.findById(orderId);

        request.setAttribute("order", order);
        request.getRequestDispatcher("success.jsp").forward(request, response);
    }
}
