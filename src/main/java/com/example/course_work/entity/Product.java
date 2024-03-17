package com.example.course_work.entity;


import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=256,nullable = false,unique = true)
    private String name;
    @Column(length=512,nullable = false,name="short_description")
    private String shortDescription;

    private boolean enabled;

    @Column(name="in_stock")
    private boolean inStock;
    private float price;

    @Column(name="main_image",nullable = false)
    private String mainImage;

    @Column(name="category",nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<ProductImage> images=new HashSet<>();

    @Transient
    public String getMainImagePath(){
        if(id==null||mainImage==null)return "/images/image-thumbnail.png";
        return "/product-images/" + this.id + "/" + this.mainImage;
    }

    public Product(Long id) {
        this.id = id;
    }
}

