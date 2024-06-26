package dev.kishore.fakestoreapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private Long id;
    public String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
}
