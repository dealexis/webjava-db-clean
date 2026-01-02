package com.example.servlets;

import com.example.models.Product;
import com.example.utils.DBUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setSize(rs.getString("size"));
                p.setColor(rs.getString("color"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));

                products.add(p);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        request.setAttribute("products", products);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}
