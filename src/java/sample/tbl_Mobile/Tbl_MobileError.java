/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.tbl_Mobile;

import java.io.Serializable;

/**
 *
 * @author KHANHBQSE63463
 */
public class Tbl_MobileError implements Serializable{
    private String mobileIdLengthError;
    private String mobileIdExisted;
    private String descriptionLengthError;
    private String priceFormatError;
    private String mobileNameLengthError;
    private String yearOfProductionError;
    private String quantityError;

    public Tbl_MobileError() {
    }

    public String getMobileIdLengthError() {
        return mobileIdLengthError;
    }

    public void setMobileIdLengthError(String mobileIdLengthError) {
        this.mobileIdLengthError = mobileIdLengthError;
    }

    public String getMobileIdExisted() {
        return mobileIdExisted;
    }

    public void setMobileIdExisted(String mobileIdExisted) {
        this.mobileIdExisted = mobileIdExisted;
    }

    public String getDescriptionLengthError() {
        return descriptionLengthError;
    }

    public void setDescriptionLengthError(String descriptionLengthError) {
        this.descriptionLengthError = descriptionLengthError;
    }

    public String getPriceFormatError() {
        return priceFormatError;
    }

    public void setPriceFormatError(String priceFormatError) {
        this.priceFormatError = priceFormatError;
    }

    public String getMobileNameLengthError() {
        return mobileNameLengthError;
    }

    public void setMobileNameLengthError(String mobileNameLengthError) {
        this.mobileNameLengthError = mobileNameLengthError;
    }

    public String getYearOfProductionError() {
        return yearOfProductionError;
    }

    public void setYearOfProductionError(String yearOfProductionError) {
        this.yearOfProductionError = yearOfProductionError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }
    
}
