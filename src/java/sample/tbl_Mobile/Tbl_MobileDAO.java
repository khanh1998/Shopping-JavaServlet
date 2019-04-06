/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.tbl_Mobile;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author KHANHBQSE63463
 */
public class Tbl_MobileDAO implements Serializable {

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
    }

    public boolean isExistedMobileID(String mobileID)
            throws NamingException, SQLException {
        try {
            Connection connection = DBUtils.getConnection();
            String query = "SELECT mobileId FROM tbl_Mobile WHERE mobileId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mobileID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return false;
    }

    public int minusQuantityOfMobile(String mobileId, int minusQuantity) throws NamingException, SQLException {
        try {
            Connection connection = DBUtils.getConnection();
            int updatedRow = 0;
            String url = "UPDATE tbl_Mobile SET quantity=? WHERE mobileId=?";

            int oldQuantity = getQuantityOfMobile(mobileId);
            int newQuantity = oldQuantity - minusQuantity;
            if (oldQuantity >= minusQuantity) {
                preparedStatement = connection.prepareStatement(url);
                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setString(2, mobileId);
                updatedRow = preparedStatement.executeUpdate();
            }
            return updatedRow;
        } finally {
            closeConnection();
        }
    }

    public int getQuantityOfMobile(String mobileId) throws NamingException, SQLException {
        int quantity = 0;
        try {
            openConnection();
            String url = "SELECT quantity FROM tbl_Mobile WHERE mobileId=?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setString(1, mobileId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                quantity = resultSet.getInt("quantity");

            }
        } finally {
            closeConnection();
        }
        return quantity;
    }

    public ArrayList<Tbl_MobileDTO> getSelectedMobileList(Set<String> mobileIdSet)
            throws NamingException, SQLException {

        ArrayList<Tbl_MobileDTO> selectedMobileList = new ArrayList<>();
        try {
            openConnection();
            Tbl_MobileDTO mobileDTO = null;
            Iterator<String> iterator = mobileIdSet.iterator();
            while (iterator.hasNext()) {
                String mobileId = iterator.next();
                mobileDTO = getMobileObject(mobileId);
                selectedMobileList.add(mobileDTO);
            }
        } finally {
            closeConnection();
        }
        return selectedMobileList;
    }

    private Tbl_MobileDTO getMobileObject(String mobileId)
            throws SQLException {
        Tbl_MobileDTO mobileDTO = null;
        String url = "SELECT * FROM tbl_Mobile WHERE mobileId = ?";
        preparedStatement = connection.prepareStatement(url);
        preparedStatement.setString(1, mobileId);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String description = resultSet.getString("description");
            float price = resultSet.getFloat("price");
            String mobileName = resultSet.getString("mobileName");
            int yearOfProduction = resultSet.getInt("yearOfProduction");
            int quantity = resultSet.getInt("quantity");
            boolean notSale = resultSet.getBoolean("notSale");
            mobileDTO
                    = new Tbl_MobileDTO(mobileId, description, price, mobileName, yearOfProduction, quantity, notSale);
        }
        return mobileDTO;
    }

    public boolean insert(Tbl_MobileDTO mobileDTO)
            throws NamingException, SQLException {
        try {
            openConnection();
            String url = "INSERT INTO tbl_Mobile VALUES(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setString(1, mobileDTO.getMobileId());
            preparedStatement.setString(2, mobileDTO.getDescription());
            preparedStatement.setFloat(3, mobileDTO.getPrice());
            preparedStatement.setString(4, mobileDTO.getMobileName());
            preparedStatement.setInt(5, mobileDTO.getYearOfProduction());
            preparedStatement.setInt(6, mobileDTO.getQuantity());
            preparedStatement.setBoolean(7, mobileDTO.isNotSale());

            int insertedRow = preparedStatement.executeUpdate();
            if (insertedRow > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean update(String mobileId, String description, float price, int quantity, boolean notSale)
            throws NamingException, SQLException {
        try {
            openConnection();
            String url = "UPDATE tbl_Mobile SET description=?,price=?,quantity=?,notSale=? WHERE mobileId=?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setString(1, description);
            preparedStatement.setFloat(2, price);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setBoolean(4, notSale);
            preparedStatement.setString(5, mobileId);

            int updatedRow = preparedStatement.executeUpdate();
            if (updatedRow > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean deleteById(String moblieId) throws NamingException, SQLException {
        try {
            openConnection();
            String url = "DELETE FROM tbl_Mobile WHERE mobileId = ?";
            preparedStatement = connection.prepareCall(url);
            preparedStatement.setString(1, moblieId);
            int deletedRow = preparedStatement.executeUpdate();
            if (deletedRow > 0) {
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public ArrayList<Tbl_MobileDTO> searchByMinPrice(float minPrice)
            throws NamingException, SQLException {
        ArrayList<Tbl_MobileDTO> deviceList = new ArrayList<>();
        try {
            openConnection();
            String url = "SELECT * FROM tbl_Mobile WHERE price >= ?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setFloat(1, minPrice);
            deviceList = executeQueryAndConvertToList();
        } finally {
            closeConnection();
        }
        return deviceList;
    }

    public ArrayList<Tbl_MobileDTO> searchByMaxPrice(float maxPrice)
            throws NamingException, SQLException {
        ArrayList<Tbl_MobileDTO> deviceList = new ArrayList<>();
        try {
            openConnection();
            String url = "SELECT * FROM tbl_Mobile WHERE price <= ?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setFloat(1, maxPrice);
            deviceList = executeQueryAndConvertToList();
        } finally {
            closeConnection();
        }
        return deviceList;
    }

    public ArrayList<Tbl_MobileDTO> searchByRangeOfPrice(float minPrice, float maxPrice)
            throws NamingException, SQLException {
        ArrayList<Tbl_MobileDTO> deviceList = new ArrayList<>();
        try {
            openConnection();
            String url = "SELECT * FROM tbl_Mobile WHERE price >= ? AND price <= ?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setFloat(1, minPrice);
            preparedStatement.setFloat(2, maxPrice);
            deviceList = executeQueryAndConvertToList();
        } finally {
            closeConnection();
        }
        return deviceList;
    }

    private ArrayList<Tbl_MobileDTO> executeQueryAndConvertToList()
            throws SQLException {

        ArrayList<Tbl_MobileDTO> deviceList = new ArrayList<>();
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String mobileId = resultSet.getString("mobileId");
            String description = resultSet.getString("description");
            float price = resultSet.getFloat("price");
            String name = resultSet.getString("mobileName");
            int yearOfProduction = resultSet.getInt("yearOfProduction");
            int quantity = resultSet.getInt("quantity");
            boolean notSale = resultSet.getBoolean("notSale");
            deviceList.add(new Tbl_MobileDTO(mobileId, description, price, name, yearOfProduction, quantity, notSale));
        }
        return deviceList;
    }

    public ArrayList<Tbl_MobileDTO> searchByIdOrName(String idOrName)
            throws NamingException, SQLException {
        ArrayList<Tbl_MobileDTO> deviceList = new ArrayList<>();
        try {
            openConnection();
            String url = "SELECT * FROM tbl_Mobile WHERE mobileId LIKE ? OR mobileName LIKE ?";
            preparedStatement = connection.prepareStatement(url);
            preparedStatement.setString(1, "%" + idOrName + "%");
            preparedStatement.setString(2, "%" + idOrName + "%");

            deviceList = executeQueryAndConvertToList();
        } finally {
            closeConnection();
        }
        return deviceList;
    }
}
