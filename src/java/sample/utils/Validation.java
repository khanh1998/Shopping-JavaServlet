/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

/**
 *
 * @author KHANHBQSE63463
 */
public class Validation {
    public static boolean isValidLengthMobileId(String mobileId) {
        if (mobileId.trim().length() < 1 || mobileId.trim().length() > 10) {
            return false;
        }
        return true;
    }
    public static boolean isValidLengthDescription(String description) {
        if (description.trim().length() < 1 || description.trim().length() > 250) {
            return false;
        }
        return true;
    }
    public static boolean isValidPriceValue(String price) {
        try {
            float priceFloat = Float.parseFloat(price);
            if (priceFloat < 0)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isValidLengthMobileName(String mobileName) {
        if (mobileName.trim().length() < 1 || mobileName.trim().length() > 20) {
            return false;
        }
        return true;
    }
    public static boolean isValidYearOfProductionValue(String yearOfProduction) {
        try {
            int yearInt = Integer.parseInt(yearOfProduction);
            if (yearInt < 0)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isValidQuantityValue(String quantity) {
        try {
            int quantityInt = Integer.parseInt(quantity);
            if (quantityInt < 0)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isValidLengthUserId(String userId) {
        if (userId.trim().length() < 1 || userId.trim().length() > 20) {
            return false;
        }
        return true;
    }
    public static boolean isValidPasswordValue(String password) {
        try {
            int passwordInt = Integer.parseInt(password);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isValidLengthFullName(String fullName) {
        if (fullName.trim().length() < 1 || fullName.trim().length() > 50) {
            return false;
        }
        return true;
    }
}
