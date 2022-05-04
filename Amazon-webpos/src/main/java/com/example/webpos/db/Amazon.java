package com.example.webpos.db;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.example.webpos.model.AmazonProduct;
import com.example.webpos.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("Amazon")
public class Amazon implements PosDB {

    @Resource(name = "AmazonProductRepository")
    private AmazonProductRepository amazonProductRepository;

    private List<Product> products = null;

    @Override
    public List<Product> getProducts() {
        //System.out.println("Amazon-getProducts");
        products = new ArrayList<Product>();
        loadAll();
        return products;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    private void loadAll(){
        Iterable<AmazonProduct> it = amazonProductRepository.findAll();
        // if(it==null){
        //     System.out.println("repository findAll failed");
        // }      
        // System.out.println("loadAll");
        //System.out.println(it.iterator().hasNext());
        it.forEach(e -> {
            Product p = new Product(e.getAsin(),e.getTitle(),0,e.getImageURL());
            products.add(p);
            //System.out.println(p.toString());
        });
    }

    
}
