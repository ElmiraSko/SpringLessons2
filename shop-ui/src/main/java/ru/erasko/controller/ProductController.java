package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erasko.exception.NotFoundException;
import ru.erasko.model.Product;
import ru.erasko.repo.ProductRepository;



@Controller
@RequestMapping
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;
    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/shop-single/{productId}")
    public String productList(@PathVariable("productId") Long id, Model model) {
        logger.info("Picture {}", id);

        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("product", product);
        model.addAttribute("products", productRepository.findAll());
    return "shop-single";
    }

    @GetMapping("/shop")
    public String products(Model model) {
        logger.info("Products");

        model.addAttribute("products", productRepository.findAll());
        return "shop";
    }
}
