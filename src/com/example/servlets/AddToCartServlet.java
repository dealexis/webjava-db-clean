package com.example.servlets;

import com.example.dao.ProductDAO;
import com.example.cart.Cart;
import com.example.models.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddToCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = ProductDAO.findById(productId);

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        cart.addProduct(product);

        response.sendRedirect("cart.jsp");
    }
}
