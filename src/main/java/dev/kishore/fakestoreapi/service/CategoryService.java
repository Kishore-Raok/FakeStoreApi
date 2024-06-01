package dev.kishore.fakestoreapi.service;

import dev.kishore.fakestoreapi.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO[] getCategoryById(String id);
    List<String> getAllCategories();
}
