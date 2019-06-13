package DAO;

import dataConnection.DataConnection;
import dataModel.Product;
import servlets.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getProductFromDB(int idProduct, String name, double price) throws SQLException {
        List<Product> getProductArr = new ArrayList<>();
        Connection connection = DataConnection.getDBConnection();

        final String sqlQuery =
                String.format("SELECT * FROM Products WHERE idProduct %s AND name %s AND price %s",
                        idProduct == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + idProduct,
                        name.equals(Constants.SELECT_ALL_STR) ? "LIKE '%'" : "= '" + name + "'",
                        price == Constants.SELECT_ALL_INT ? "LIKE '%'" : "= " + price
                );

        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Product product = new Product(rs.getInt("idProduct"), rs.getString("name"), rs.getDouble("price"));
            getProductArr.add(product);
        }
        connection.close();
        return getProductArr;
    }
}
