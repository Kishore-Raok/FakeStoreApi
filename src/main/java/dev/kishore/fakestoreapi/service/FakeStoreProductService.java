package dev.kishore.fakestoreapi.service;

import dev.kishore.fakestoreapi.dto.CategoryDTO;
import dev.kishore.fakestoreapi.dto.ProductDTO;
import dev.kishore.fakestoreapi.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements ProductService{
    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        ProductDTO fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                ProductDTO.class
        );

        System.out.printf(fakeStoreProductDto.toString());

        return fakeStoreProductDto != null ? fakeStoreProductDto.toProduct() : null;
    }

    @Override
    public List<Product> getAllProducts() {
        ProductDTO[] fakeStoreProductDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                ProductDTO[].class
        );

        if (fakeStoreProductDtos != null) {
            return Arrays.stream(fakeStoreProductDtos)
                    .map(ProductDTO::toProduct)
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        ProductDTO fs = new ProductDTO();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setCategory(product.getCategory());
        fs.setImageUrl(product.getImageUrl());
        fs.setDescription(product.getDescription());
        fs.setPrice(product.getPrice());

        ProductDTO response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fs,
                ProductDTO.class
        );

        return response != null ? response.toProduct() : null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        ProductDTO fs = new ProductDTO();
        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setCategory(product.getCategory());
        fs.setImageUrl(product.getImageUrl());
        fs.setDescription(product.getDescription());
        fs.setPrice(product.getPrice());

        restTemplate.put(
                "https://fakestoreapi.com/products/" + id,
                fs
        );

        // Assuming the API returns the updated product on PUT, you might need to fetch it again
        return getSingleProduct(id);
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }
}
