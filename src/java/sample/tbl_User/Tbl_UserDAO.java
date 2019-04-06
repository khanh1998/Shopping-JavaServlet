/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.tbl_User;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author KHANHBQSE63463
 */
public class Tbl_UserDAO implements Serializable {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void openConnection() throws NamingException, SQLException {
        connection = DBUtils.getConnection();
    }
    
    public int isRegistratedAccount(String userId, int password) throws NamingException, SQLException {
        try {
            openConnection();
            String url = "SELECT role FROM tbl_User WHERE userId=? AND password=?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setString(1, userId);
            preparedStatement.setInt(2, password);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next())
                return resultSet.getInt("role");
        } finally {
            closeConnection();
        }
        return -1;
    }
}
