package com.example.dao;

import com.example.cart.Cart;
import com.example.cart.CartItem;
import com.example.models.Order;
import com.example.models.OrderItem;
import com.example.models.Product;
import com.example.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static int __legacy_create(Cart cart) throws Exception {
        Connection conn = DBUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders(order_date, total_price) VALUES (NOW(), ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setDouble(1, cart.getTotal());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int orderId = rs.getInt(1);

        for (CartItem item : cart.getItems()) {
            PreparedStatement itemPs = conn.prepareStatement(
                    "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)"
            );
            itemPs.setInt(1, orderId);
            itemPs.setInt(2, item.getProduct().getId());
            itemPs.setInt(3, item.getQuantity());
            itemPs.setDouble(4, item.getProduct().getPrice());
            itemPs.executeUpdate();

            Product product = item.getProduct();
            int stock = product.getStock() - item.getQuantity();

            PreparedStatement productPs = conn.prepareStatement("UPDATE products SET stock = ? WHERE id = ?");
            productPs.setInt(1, stock);
            productPs.setInt(2, product.getId());
            productPs.executeUpdate();
        }

        return orderId;
    }

    @SuppressWarnings("DuplicatedCode")
    public static int create(Cart cart) throws Exception {
        Connection conn = null;
        int orderId;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // START TRANSACTION

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO orders(order_date, total_price) VALUES (NOW(), ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setDouble(1, cart.getTotal());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            orderId = rs.getInt(1);

            for (CartItem item : cart.getItems()) {
                PreparedStatement itemPs = conn.prepareStatement(
                        "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)"
                );
                itemPs.setInt(1, orderId);
                itemPs.setInt(2, item.getProduct().getId());
                itemPs.setInt(3, item.getQuantity());
                itemPs.setDouble(4, item.getProduct().getPrice());
                itemPs.executeUpdate();

                // Update stock
                Product product = item.getProduct();
                int newStock = product.getStock() - item.getQuantity();

                if (newStock < 0) {
                    throw new Exception("Not enough stock for product: " + product.getName());
                }

                PreparedStatement productPs = conn.prepareStatement(
                        "UPDATE products SET stock = ? WHERE id = ?"
                );
                productPs.setInt(1, newStock);
                productPs.setInt(2, product.getId());
                productPs.executeUpdate();
            }

            conn.commit();
            return orderId;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static Order findById(int id) {
        Order o = null;

        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                o = new Order();
                o.setId(rs.getInt("id"));
                o.setTotalPrice(rs.getDouble("total_price"));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return o;
    }

    public static List<OrderItem> findItemsByOrderId(Order order) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem(ProductDAO.findById(rs.getInt("product_id")));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setPrice(rs.getDouble("price"));
                list.add(orderItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
