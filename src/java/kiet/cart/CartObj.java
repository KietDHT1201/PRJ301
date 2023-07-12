package kiet.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CartObj implements Serializable{
    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }
    
    public void addItemToCart(String sku){
        if(sku == null){
            return;
        }
        if(sku.trim().isEmpty()){
            return;
        }
        //1. Check existed items (kiểm tra xem ngăn chứa đồ có tồn tại chưa)
        if(this.items == null){
            this.items = new HashMap<>();
        }// items have not existed
        //2. Check existed item (Kiểm tra trong ngăn chứa đồ chưa)
        int quantity = 1;
        if(this.items.containsKey(sku)){
            quantity = this.items.get(sku) + 1; 
        }// end item has existed in items
        //3. Update items
        this.items.put(sku, quantity);
    }
    
    public void removeItemFromCart(String sku){
        //1. Check existed items 
        if(this.items == null){
            return;
        }
        //2. Check existed item
        if(this.items.containsKey(sku)){
            this.items.remove(sku);
            if(this.items.isEmpty()){
                this.items = null;
            }//Không chấp nhận 1 thứ gì không chứa gì bên trong
        }//end item has existed in items
    }
}
