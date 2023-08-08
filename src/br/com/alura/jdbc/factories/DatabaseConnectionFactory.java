package br.com.alura.jdbc.factories;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseConnectionFactory {
    public DataSource dataSource;
    private String connString = "jdbc:mysql://localhost/loja_virtual";
    private String queryParams = "?useTimezone=true&serverTimezone=UTC";
    private String user = "root";
    private String password = "root";
    private static DatabaseConnectionFactory instance;

    public DatabaseConnectionFactory() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(this.connString + this.queryParams);
        comboPooledDataSource.setUser(this.user);
        comboPooledDataSource.setPassword(this.password);

        this.dataSource = comboPooledDataSource;
    }

    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionFactory();
        }
        return instance;
    }
}
