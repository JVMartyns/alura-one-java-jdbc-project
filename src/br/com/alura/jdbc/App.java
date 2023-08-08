package br.com.alura.jdbc;

import javax.swing.JFrame;

import br.com.alura.jdbc.migrations.Migration;
import br.com.alura.jdbc.views.ProductCategoryFrameView;

public class App {
    public static void main(String[] args) throws Exception {
        Migration.run();
        ProductCategoryFrameView view = new ProductCategoryFrameView();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
