package dev.kishore.fakestoreapi.service;

import dev.kishore.fakestoreapi.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId);
    List<Product> getAllProducts();
    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
    Page<Product> getProducts(int page, int size, String sortField, String sortDirection);
}
