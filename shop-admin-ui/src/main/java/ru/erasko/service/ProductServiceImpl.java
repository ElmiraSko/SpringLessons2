package ru.erasko.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.erasko.controller.repr.ProductRepr;
import ru.erasko.model.Picture;
import ru.erasko.model.PictureData;
import ru.erasko.model.Product;
import ru.erasko.repo.ProductRepository;
import ru.erasko.repo.ProductSpecification;
import ru.erasko.rest.NotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, Serializable {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id).map(ProductRepr::new);
    }

    @Override
    @Transactional
    public void save(ProductRepr productRepr) throws IOException {
        Product product = (productRepr.getId() != null) ? productRepository.findById(productRepr.getId())
                .orElseThrow(NotFoundException::new) : new Product();
        System.out.println("При сохранении продукта " + product.toString());
        product.setTitle(productRepr.getTitle());
        product.setCost(productRepr.getCost());
        product.setCategory(productRepr.getCategory());
//        product.setBrand(productRepr.getBrand());

        if (productRepr.getNewPictures() != null) {
            for (MultipartFile newPicture : productRepr.getNewPictures()) {
                logger.info("Product {} file {} size {}", product.getId(),
                        newPicture.getOriginalFilename(), newPicture.getSize());

                if (product.getPictures() == null) {
                    product.setPictures(new ArrayList<>());
                }

                product.getPictures().add(new Picture(newPicture.getOriginalFilename(),
                        newPicture.getContentType(), new PictureData(newPicture.getBytes())));
            }
        }
        System.out.println("При сохранении продукта " + product.toString());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductRepr> findAllByFilter(BigDecimal minCost, BigDecimal maxCost,
                                       String productTitle) {
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
        return productRepository.findAll(specification).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }


}
