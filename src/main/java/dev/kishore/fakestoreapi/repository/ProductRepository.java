package dev.kishore.fakestoreapi.repository;

import dev.kishore.fakestoreapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
