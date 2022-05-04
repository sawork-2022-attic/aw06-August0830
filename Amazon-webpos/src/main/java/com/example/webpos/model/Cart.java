package com.example.webpos.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart implements Serializable{

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        for(Item i:items){
            if(i.hasSameProduct(item)){
                return i.modifyItself(item.getQuantity()+i.getQuantity());
            }
        }
        return items.add(item);
    }

    public boolean modifyItem(Product product,int amount){
        for(Iterator<Item> it = items.iterator();it.hasNext();){
            Item item = it.next();
            if(item.getProduct().equals(product)){
                if(amount==0)
                    it.remove();
                else
                    return item.modifyItself(amount);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total );

        return stringBuilder.toString();
    }

	public boolean removeAll() {
		return items.removeAll(items);
    }
    
    public double total(){
        double sum = 0;
        for(Item item:items){
            sum += item.value();
        }
        return sum;
    }
}
