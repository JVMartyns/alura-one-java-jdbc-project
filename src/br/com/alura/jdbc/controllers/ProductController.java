package br.com.alura.jdbc.controllers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.dao.ProductDAO;
import br.com.alura.jdbc.factories.DatabaseConnectionFactory;
import br.com.alura.jdbc.models.Product;

public class ProductController {

    private ProductDAO productDAO;

    public ProductController() {
        Connection conn = DatabaseConnectionFactory.getInstance().getConnection();
        this.productDAO = new ProductDAO(conn);
    }

    public void insert(Product product) {
        this.productDAO.insert(product);
    }

    public List<Product> list() {
        return this.productDAO.list();
    }

    public void update(String name, String description, Integer id) {
        this.productDAO.update(name, description, id);
    }

    public void delete(Integer id) {
        this.productDAO.delete(id);
    }
}
