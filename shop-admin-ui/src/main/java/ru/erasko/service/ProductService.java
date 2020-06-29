package ru.erasko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erasko.model.Product;
import ru.erasko.repo.ProductRepository;
import ru.erasko.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<Product> productFilter(BigDecimal minCost, BigDecimal maxCost,
                                       String productTitle, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.trueLiteral();

        if (minCost != null) {
            specification = specification.and(ProductSpecification.costGreaterThanOrEqual(minCost));
        }

        if (maxCost != null) {
            specification = specification.and(ProductSpecification.costLessThanOrEqual(maxCost));
        }
        if (productTitle != null) {
            specification = specification.and(ProductSpecification.findByProductTitle(productTitle));
            }
            return productRepository.findAll(specification, pageable);
    }

    @Transactional
    public void saveProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    @Transactional(readOnly = true)
    public Optional<Product> productById(long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
