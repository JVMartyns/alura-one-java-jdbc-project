package br.com.alura.jdbc.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.alura.jdbc.controllers.CategoryController;
import br.com.alura.jdbc.controllers.ProductController;
import br.com.alura.jdbc.models.Category;
import br.com.alura.jdbc.models.Product;

public class ProductCategoryFrameView extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel nameLabel, descriptionLabel, categoryLabel;
    private JTextField nameText, categoryText;
    private JComboBox<Category> categoryComboBox;
    private JButton saveButton, updateButton, clearButton, deleteButton;
    private JTable table;
    private DefaultTableModel model;
    private ProductController productController;
    private CategoryController categoryController;

    public ProductCategoryFrameView() {
        super("Products");
        Container container = getContentPane();
        setLayout(null);

        categoryController = new CategoryController();
        productController = new ProductController();

        nameLabel = new JLabel("Nome do Produto");
        descriptionLabel = new JLabel("Descrição do Produto");
        categoryLabel = new JLabel("Categoria do Produto");

        nameLabel.setBounds(10, 10, 240, 15);
        descriptionLabel.setBounds(10, 50, 240, 15);
        categoryLabel.setBounds(10, 90, 240, 15);

        nameLabel.setForeground(Color.BLACK);
        descriptionLabel.setForeground(Color.BLACK);
        categoryLabel.setForeground(Color.BLACK);

        container.add(nameLabel);
        container.add(descriptionLabel);
        container.add(categoryLabel);

        nameText = new JTextField();
        categoryText = new JTextField();
        categoryComboBox = new JComboBox<Category>();

        categoryComboBox.addItem(new Category(0, "Selecione"));
        List<Category> categories = this.listCategoria();
        for (Category category : categories) {
            categoryComboBox.addItem(category);
        }

        nameText.setBounds(10, 25, 265, 20);
        categoryText.setBounds(10, 65, 265, 20);
        categoryComboBox.setBounds(10, 105, 265, 20);

        container.add(nameText);
        container.add(categoryText);
        container.add(categoryComboBox);

        saveButton = new JButton("Salvar");
        clearButton = new JButton("Limpar");

        saveButton.setBounds(10, 145, 80, 20);
        clearButton.setBounds(100, 145, 80, 20);

        container.add(saveButton);
        container.add(clearButton);

        table = new JTable();
        model = (DefaultTableModel) table.getModel();

        model.addColumn("Identificador do Produto");
        model.addColumn("Nome do Produto");
        model.addColumn("Descrição do Produto");

        fillTable();

        table.setBounds(10, 185, 760, 300);
        container.add(table);

        deleteButton = new JButton("Excluir");
        updateButton = new JButton("Alterar");

        deleteButton.setBounds(10, 500, 80, 20);
        updateButton.setBounds(100, 500, 80, 20);

        container.add(deleteButton);
        container.add(updateButton);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insert();
                clearTable();
                fillTable();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
                clearTable();
                fillTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                clearTable();
                fillTable();
            }
        });
    }

    private void clearTable() {
        model.getDataVector().clear();
    }

    private void update() {
        Object lineObject = (Object) model.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
        if (lineObject instanceof Integer) {
            Integer id = (Integer) lineObject;
            String name = (String) model.getValueAt(table.getSelectedRow(), 1);
            String description = (String) model.getValueAt(table.getSelectedRow(), 2);
            this.productController.update(name, description, id);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
        }
    }

    private void delete() {
        Object lineObject = (Object) model.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
        if (lineObject instanceof Integer) {
            Integer id = (Integer) lineObject;
            this.productController.delete(id);
            model.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
        }
    }

    private void fillTable() {
        List<Product> products = listProduct();
        try {
            model.addRow(new Object[] { "ID", "NOME", "DESCRIÇÃO" });
            for (Product product : products) {
                model.addRow(new Object[] { product.getId(), product.getName(), product.getDescription() });
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private List<Category> listCategoria() {
        return this.categoryController.list();
    }

    private void insert() {
        if (!nameText.getText().equals("") && !categoryText.getText().equals("")) {
            Product product = new Product(nameText.getText(), categoryText.getText());
            Category category = (Category) categoryComboBox.getSelectedItem();
            product.setCategoryId(category.getId());
            this.productController.insert(product);
            JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
            this.clear();
        } else {
            JOptionPane.showMessageDialog(this, "Nome e Descrição devem ser informados.");
        }
    }

    private List<Product> listProduct() {
        return this.productController.list();
    }

    private void clear() {
        this.nameText.setText("");
        this.categoryText.setText("");
        this.categoryComboBox.setSelectedIndex(0);
    }
}
