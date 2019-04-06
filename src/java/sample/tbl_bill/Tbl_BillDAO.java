/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.tbl_bill;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import javax.naming.NamingException;
import sample.tbl_Mobile.Tbl_MobileDAO;
import sample.utils.DBUtils;

/**
 *
 * @author KHANHBQSE63463
 */
public class Tbl_BillDAO implements Serializable {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void openConnection()
            throws NamingException, SQLException {

        connection = DBUtils.getConnection();
    }

    private void closeConnection()
            throws SQLException {

        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
        System.out.println(9);
        
    }

    public boolean writeBill(Map<String, Integer> cart, String userId, Timestamp dateTime)
            throws NamingException, SQLException {

        try {
            openConnection();
            String url = "INSERT INTO tbl_Bill(userId, mobileId, quantity, datetime) VALUES(?,?,?,?)";

            connection.setAutoCommit(false);
            try {
                for (Map.Entry<String, Integer> mobile : cart.entrySet()) {

                    System.out.println(userId + " - " + mobile.getKey() + " - " + mobile.getValue() + " - " + dateTime);
                    preparedStatement = connection.prepareStatement(url);
                    preparedStatement.setString(1, userId);
                    preparedStatement.setString(2, mobile.getKey());
                    preparedStatement.setInt(3, mobile.getValue());
                    preparedStatement.setTimestamp(4, dateTime);
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                return false;
            }
        } finally {
            closeConnection();
        }
        return true;
    }
}
