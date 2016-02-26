package com.vajda.spockExamples.spring;

import org.jtransfo.DomainClass;

@DomainClass(domainClass = Product.class)
public class ProductDTO {

    private Integer id;
    private String name;
    private Integer price;
    private String description;

    public ProductDTO() {}

    public ProductDTO(String name, Integer price, String description) {
        this(null, name, price, description);
    }

    public ProductDTO(Integer id, String name, Integer price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
