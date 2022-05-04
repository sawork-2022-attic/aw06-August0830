package com.example.webpos.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item implements Serializable{
    private Product product;
    private int quantity;

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    public boolean hasSameProduct(Item item){
        return this.product.equals(item.getProduct());
    }

    public boolean modifyItself(int quantity){
        if(quantity<0)
            return false;
        this.quantity = quantity;
        return true;
    }

    @Override
    public String toString(){
        return product.toString() +"\t" + quantity;
    }

	public double value() {
		return product.getPrice() * this.quantity;
	}
}
