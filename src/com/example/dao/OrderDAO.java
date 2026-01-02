package com.example.dao;

import com.example.cart.Cart;
import com.example.cart.CartItem;
import com.example.utils.DBUtil;

import java.sql.*;

public class OrderDAO {

    public static void create(Cart order) throws Exception {
        Connection conn = DBUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders(order_date, total_price) VALUES (NOW(), ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setDouble(1, order.getTotal());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int orderId = rs.getInt(1);

        for (CartItem item : order.getItems()) {
            PreparedStatement itemPs = conn.prepareStatement(
                    "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)"
            );
            itemPs.setInt(1, orderId);
            itemPs.setInt(2, item.getProduct().getId());
            itemPs.setInt(3, item.getQuantity());
            itemPs.setDouble(4, item.getProduct().getPrice());
            itemPs.executeUpdate();
        }
    }
}
