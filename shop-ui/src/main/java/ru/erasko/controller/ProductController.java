package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.erasko.controller.repr.ProductRepr;
import ru.erasko.repo.ProductRepository;
import ru.erasko.service.CartService;
import ru.erasko.service.ProductService;
import ru.erasko.service.StockService;

@Controller
@RequestMapping
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final StockService stockService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService,
                             CartService cartService, StockService stockService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.cartService = cartService;
        this.stockService = stockService;
    }

    @GetMapping("/product_details/{productId}")
    public String productList(@PathVariable("productId") Long id, Model model) {
        logger.info("Picture {}", id);
        ProductRepr productRepr = productService.findById(id).orElseThrow(IllegalArgumentException::new);
        productRepr.setCountInStock(stockService.getStockInfo(productRepr.getId()).getCount());
        model.addAttribute("product", productRepr);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("subTotal", cartService.getSubTotal());
    return "product_details";
    }

    @GetMapping("/products")
    public String products(Model model) {
        logger.info("Products");
        model.addAttribute("products", productService.findAllAndSplitProductsBy(3));
        model.addAttribute("subTotal", cartService.getSubTotal());
        return "products";
    }
}
