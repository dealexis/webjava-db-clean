package com.example.dao;

import com.example.models.Product;
import com.example.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public static List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product();
                productDataSetUp(p, rs);

                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // Get single product by ID (for Buy action)
    public static Product findById(int id) {
        Product p = null;

        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Product();
                productDataSetUp(p, rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    private static void productDataSetUp(Product p, ResultSet rs) throws SQLException {
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setStock(rs.getInt("stock"));
        p.setSize(rs.getString("size"));
        p.setColor(rs.getString("color"));
    }
}
