package dev.kishore.fakestoreapi.service;

import dev.kishore.fakestoreapi.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final RestTemplate restTemplate;

    @Autowired
    public CategoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CategoryDTO[] getCategoryById(String id) {
        return restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + id,
                CategoryDTO[].class
        );
    }

    @Override
    public List<String> getAllCategories() {
        String[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );
        return Arrays.asList(categories);
    }

}
