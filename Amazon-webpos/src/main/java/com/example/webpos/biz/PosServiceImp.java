package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

@Component
public class PosServiceImp implements PosService {

    @Resource(name = "Amazon")
    private PosDB posDB;
    private double checkoutAmount;
    private Cart cart;

    //@Autowired
    @Resource(name = "Amazon")
    public void setPosDB( PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {

        if (this.cart == null)
            return this.newCart();
        return this.cart;
    }

    @Override
    public Cart newCart() {
        this.cart = new Cart();
        return this.cart;
    }

    @Override
    public double checkout(Cart cart) {
        double amount = cart.total();
        cart.removeAll();
        return amount;
    }

    @Override
    public void total(Cart cart) {

    }

    @Override
    public boolean add(Cart cart, Product product, int amount) {
        return false;
    }

    @Override
    public boolean add(Cart cart, String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return false;

        cart.addItem(new Item(product, amount));
        return true;
    }

    @Override
    public List<Product> products() {
        if(posDB!=null)
            return posDB.getProducts();
        else{
            System.out.println("empty posDB");
            return null;
        }
    }

    @Override
    public boolean modify(Cart cart, String productId, int amount) {
        Product product = posDB.getProduct(productId);
        if (product == null) return false;

        return cart.modifyItem(product,amount);
    }

    @Override
    public boolean remove(Cart cart, String productId) {
        Product product = posDB.getProduct(productId);
        if (product == null)
            return false;
        return cart.modifyItem(product, 0);
    }

    @Override
    public boolean empty(Cart cart) {
        return cart.removeAll();
    }

    
}
