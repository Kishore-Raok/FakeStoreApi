package dev.kishore.fakestoreapi.service;

import dev.kishore.fakestoreapi.configs.RedisTemplateConfig;
import dev.kishore.fakestoreapi.dto.CategoryDTO;
import dev.kishore.fakestoreapi.dto.ProductDTO;
import dev.kishore.fakestoreapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements ProductService{
    private  RestTemplate restTemplate;
    private RedisTemplate redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        // Attempt to fetch product from Redis cache
        Product productFromRedis = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);
        if (productFromRedis != null) {
            return productFromRedis;
        }

        // If product not found in cache, fetch from remote API
        ProductDTO fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                ProductDTO.class
        );

        // Check if the product fetched from the API is not null
        if (fakeStoreProductDto != null) {
            Product product = fakeStoreProductDto.toProduct();
            // Store the product in Redis cache
            redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);
            // Optionally set an expiration time for the cache
            redisTemplate.expire("PRODUCTS", Duration.ofHours(1)); // Adjust duration as needed
            return product;
        }

        // Return null if the product was not found in the API
        return null;
    }

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
 public Page<Product> getProducts (int page, int size, String sortField, String sortDirection) {
     // Fetch all products from the external API
     ProductDTO[] fakeStoreProductDtos = restTemplate.getForObject(
             "https://fakestoreapi.com/products",
             ProductDTO[].class
     );

     if (fakeStoreProductDtos == null) {
         return Page.empty();
     }

     List<Product> allProducts = Arrays.stream(fakeStoreProductDtos)
             .map(ProductDTO::toProduct)
             .collect(Collectors.toList());

     // Sort the products
     Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
             ? Sort.by(sortField).ascending()
             : Sort.by(sortField).descending();

     // Apply pagination
     int start = Math.min((int) PageRequest.of(page, size).getOffset(), allProducts.size());
     int end = Math.min((start + size), allProducts.size());

     List<Product> paginatedProducts = allProducts.subList(start, end);

     return new PageImpl<>(paginatedProducts, PageRequest.of(page, size, sort), allProducts.size());
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
