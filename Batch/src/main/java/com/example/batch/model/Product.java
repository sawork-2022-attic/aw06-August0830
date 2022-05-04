package com.example.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "amazon_products")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class)
    //@TypeDef(name = "json", typeClass = JsonType.class)
})
public class Product implements Serializable{

    /**
     *
     */
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

    //@OneToMany(targetEntity = String.class, mappedBy = "Product", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ElementCollection
    @Column(name = "category",columnDefinition = "json")
    @Type(type = "json")
    @Getter
    @Setter
    private List<String> category;


    // //@OneToMany(targetEntity = String.class, mappedBy = "Product",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ElementCollection
    @Column(name = "imageURL",columnDefinition = "json")
    @Type(type = "json")
    @Getter
    @Setter
    private List<String> imageURLHighRes;

    public void showProduct(){
        System.out.println("category "+main_cat+"\ntitle "+title+" \nasin "+asin);
        for(String str: category)
            System.out.print(str+" ");
        System.out.println();
        for(String str:imageURLHighRes)
            System.out.print(str+" ");
    }
}
