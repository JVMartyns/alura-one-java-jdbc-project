package br.com.alura.jdbc.migrations;

import java.sql.Connection;

import br.com.alura.jdbc.factories.DatabaseConnectionFactory;

public class Migration {
    public static void run() {
        createTableCategories();
        createTableProducts();
    }

    private static void createTableCategories() {
        try (Connection connection = DatabaseConnectionFactory.getInstance().getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS categories (id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50))";
            connection.createStatement().execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTableProducts() {
        try (Connection connection = DatabaseConnectionFactory.getInstance().getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), description VARCHAR(100), category_id INTEGER NOT NULL, FOREIGN KEY (category_id) REFERENCES categories (id))";
            connection.createStatement().execute(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
