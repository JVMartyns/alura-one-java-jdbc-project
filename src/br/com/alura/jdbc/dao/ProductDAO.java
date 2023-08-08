package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.models.Category;
import br.com.alura.jdbc.models.Product;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Product product) {
        String sql = "INSERT INTO products (name, description, category_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, product.getName());
            pstm.setString(2, product.getDescription());
            pstm.setInt(3, product.getCategoryId());

            pstm.execute();

            try (ResultSet rst = pstm.getGeneratedKeys()) {
                while (rst.next()) {
                    product.setId(rst.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> list() {
        List<Product> products = new ArrayList<Product>();
        String sql = "SELECT id, name, description FROM products";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();
            transformResultSetToProduct(products, pstm);
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> list(Category ct) {
        List<Product> products = new ArrayList<Product>();
        String sql = "SELECT id, name, description FROM products WHERE category_id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, ct.getId());
            pstm.execute();
            transformResultSetToProduct(products, pstm);
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String name, String description, Integer id) {
        String sql = "UPDATE products SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement stm = connection
                .prepareStatement(sql)) {
            stm.setString(1, name);
            stm.setString(2, description);
            stm.setInt(3, id);
            stm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void transformResultSetToProduct(List<Product> products, PreparedStatement pstm) {
        try (ResultSet rst = pstm.getResultSet()) {
            while (rst.next()) {
                Product product = new Product(rst.getInt(1), rst.getString(2), rst.getString(3));

                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
