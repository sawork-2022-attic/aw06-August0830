package com.example.webpos.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "amazon_products")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class AmazonProduct {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "main_cat")
    @Getter
    @Setter
    private String main_cat;


    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Column(name = "asin")
    @Getter
    @Setter
    private String asin;

    @ElementCollection
    @Column(name = "category",columnDefinition = "json")
    @Type(type = "json")
    @Getter
    @Setter
    private List<String> category;

    @ElementCollection
    @Column(name = "imageURL",columnDefinition = "json")
    @Type(type = "json")
    @Getter
    @Setter
    private List<String> imageURLHighRes;

    public String getImageURL(){
        if(!imageURLHighRes.isEmpty())
            return imageURLHighRes.get(0);
        else
            return "https://images-na.ssl-images-amazon.com/images/I/51wFybYp4vL.jpg";
    }

    @Override
    public String toString()
    {
        return id+"\t"+asin+"\t"+main_cat+"\t"+title+"\t"+getImageURL();
    }
}