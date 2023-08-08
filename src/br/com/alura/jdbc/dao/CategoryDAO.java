package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.models.Category;
import br.com.alura.jdbc.models.Product;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO(Connection connection) {
        this.conn = connection;
    }

    public List<Category> list() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.execute();

            try (ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    Category category = new Category(rst.getInt(1), rst.getString(2));
                    categories.add(category);
                }
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> listWithProducts() throws SQLException {
        String sql = "SELECT c.id, c.name, p.id, p.name, p.description " + "FROM categories c "
                + "INNER JOIN products p ON c.id = p.category_id ORDER BY c.id";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                Category category = null;
                while (rs.next()) {
                    if (category == null || category.getId() != rs.getInt(1)) {
                        category = new Category(rs.getInt(1), rs.getString(2));
                        categories.add(category);
                    }
                    category.addProduct(new Product(rs.getInt(3), rs.getString(4), rs.getString(5)));
                }
                return categories;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
