package com.zelin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Field("id")
    private int pid;
    @Field("product_name")
    private String name;
    @Field("product_catalog_name")
    private String catelog_name;
    @Field("product_price")
    private float price;
    private int number;
    @Field("product_description")
    private String description;
    @Field("product_picture")
    private String picture;
    private String release_time;

    public Product(String name, String catelog_name, float price, int number, String description, String picture, String release_time) {
        this.name = name;
        this.catelog_name = catelog_name;
        this.price = price;
        this.number = number;
        this.description = description;
        this.picture = picture;
        this.release_time = release_time;
    }

}
