package ru.erasko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.erasko.controller.repr.ProductRepr;
import ru.erasko.model.Brand;
import ru.erasko.model.Category;
import ru.erasko.model.Product;
import ru.erasko.repo.ProductRepository;
import ru.erasko.service.ProductService;
import ru.erasko.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl();
        ((ProductServiceImpl)productService).setProductRepository(productRepository);

    }

    @Test
    public void testFindById() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category1");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Brand1");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product1");
        expectedProduct.setPrice(new BigDecimal(245));
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(new ArrayList<>());

        when(productRepository.findById(eq(1L)))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductRepr> product = productService.findById(1L);

        assertTrue(product.isPresent());
        assertEquals(expectedProduct.getId(), product.get().getId());
        assertEquals(expectedProduct.getName(), product.get().getName());
        assertEquals(expectedProduct.getPrice(), product.get().getPrice());
        assertEquals(expectedCategory.getName(), product.get().getCategoryName());
        assertEquals(expectedBrand.getName(), product.get().getBrandName());

    }
}
