package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.List;

public interface PosService {
    public Cart getCart();

    public Cart newCart();

    public double checkout(Cart cart);

    public void total(Cart cart);

    public boolean add(Cart cart, Product product, int amount);

    public boolean add(Cart cart, String productId, int amount);

    public boolean modify(Cart cart, String productId,int amount);

    public boolean remove(Cart cart, String productId);

    public List<Product> products();

	public boolean empty(Cart cart);
}
