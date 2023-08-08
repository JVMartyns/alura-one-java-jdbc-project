package br.com.alura.jdbc.controllers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.dao.CategoryDAO;
import br.com.alura.jdbc.factories.DatabaseConnectionFactory;
import br.com.alura.jdbc.models.Category;

public class CategoryController {

    private CategoryDAO categoryDAO;

    public CategoryController() {
        Connection conn = DatabaseConnectionFactory.getInstance().getConnection();
        this.categoryDAO = new CategoryDAO(conn);
    }

    public List<Category> list() {
        return this.categoryDAO.list();
    }
}
