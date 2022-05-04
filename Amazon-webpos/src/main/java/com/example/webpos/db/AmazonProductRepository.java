package com.example.webpos.db;

import com.example.webpos.model.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("AmazonProductRepository")
public interface AmazonProductRepository extends CrudRepository<AmazonProduct,Integer>{

}