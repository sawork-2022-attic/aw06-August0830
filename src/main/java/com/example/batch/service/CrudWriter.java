package com.example.batch.service;

import java.util.List;

import com.example.batch.model.Product;

import org.springframework.batch.item.data.RepositoryItemWriter;

public class CrudWriter extends RepositoryItemWriter<Product> {

    @Override
    public void write(List<? extends Product> list) throws Exception {
    //    list.stream().forEach(System.out::println);
        super.write(list);
       for(Product p:list){
           p.showProduct();
       }
       System.out.println("chunk written");
    }
}