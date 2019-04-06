/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author KHANHBQSE63463
 */
public class Cart implements Serializable {
    private String userId;
    private Map<String, Integer> list;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Integer> getList() {
        return list;
    }
    
    public void addItem(String mobileId) {
        if (list == null) {
            list = new HashMap<>();
        }
        int quantity = 1;
        if (list.containsKey(mobileId)) {
            quantity = list.get(mobileId) + 1;
        }
        list.put(mobileId, quantity);
    }
    
    public void removeItem(String mobileId) {
        if (list == null)
            return;
        if (list.containsKey(mobileId)) {
            list.remove(mobileId);
        }
        if (list.isEmpty()) {
            list = null;
        }
    }
    
    public int getQuantityOfItem(String mobileId) {
        if (list != null && list.containsKey(mobileId)) {
            return list.get(mobileId);
        }
        return 0;
    }
    
    public void removeAllItems() {
        list.clear();
        list = null;
    }
}
