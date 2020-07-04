package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.erasko.model.Product;
import ru.erasko.rest.NotFoundException;
import ru.erasko.service.CategoryService;
import ru.erasko.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RequestMapping("/product")
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;

    }

    @GetMapping
    public String getProductList(Model model,
                                 @RequestParam(name = "minCost", required = false) BigDecimal minCost,
                                 @RequestParam(name = "maxCost", required = false) BigDecimal maxCost,
                                 @RequestParam(name = "productTitle", required = false) String productTitle,
                                 @RequestParam(name = "page") Optional<Integer> page,
                                 @RequestParam(name = "size") Optional<Integer> size) {

        Page<Product> productPage = productService.productFilter(minCost, maxCost, productTitle,
                PageRequest.of(page.orElse(1)-1, size.orElse(5)));

        model.addAttribute("productsPage", productPage);
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute("productTitle", productTitle);
        model.addAttribute("prevPageNumber", productPage.hasPrevious() ? productPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", productPage.hasNext() ? productPage.nextPageable().getPageNumber() + 1 : -1);
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("Create product form");

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "product";
    }

    @PostMapping
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        logger.info("Save product method " + product.toString());

        if (bindingResult.hasErrors()) {
            logger.info("Error save");
            return "product";
        }

        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping(value="/edit/{id}")
    public String findProductById(Model model, @PathVariable(value="id") long id) {
        logger.info("Find product by Id method");

        Product editProduct = productService.productById(id)
                .orElseThrow(() ->new NotFoundException("Not found product by Id"));

        model.addAttribute("editProduct", editProduct);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "edit-product";
    }

    @PostMapping(value="/save/{id}")
    public String saveProductById(@ModelAttribute("editProduct") @Valid Product product,
                                  BindingResult bindingResult) {
        logger.info("Save product by id");

        if (bindingResult.hasErrors()) {
            logger.info("Errors!");
            return "edit-product";
        }
        productService.saveProduct(product);
        return "redirect:/product";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete product width id {} ", id);

        productService.delete(id);
        return "redirect:/product";
    }
}
