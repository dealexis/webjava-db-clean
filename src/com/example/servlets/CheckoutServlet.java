package com.example.servlets;

import com.example.dao.OrderDAO;
import com.example.cart.Cart;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("empty-cart.jsp");
            return;
        }

        try {
            OrderDAO.create(cart);
        } catch (Exception e) {
            // log caught exception
            response.sendRedirect("error.jsp");
            return;
        }

        session.removeAttribute("cart");

        response.sendRedirect("success.jsp");
    }
}
