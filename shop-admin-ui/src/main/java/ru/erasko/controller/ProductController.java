package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.erasko.controller.repr.ProductRepr;
import ru.erasko.repo.BrandRepository;
import ru.erasko.repo.CategoryRepository;
import ru.erasko.rest.NotFoundException;
import ru.erasko.service.ProductServiceImpl;

@RequestMapping
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductServiceImpl productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;


    @Autowired
    public ProductController(ProductServiceImpl productService, CategoryRepository categoryRepository,
                             BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @GetMapping("/products")
    public String productList(Model model) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/product/create")
    public String createProduct(Model model) {
        logger.info("Create product form");
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", new ProductRepr());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }

    @PostMapping("/product")
    public String saveProduct(Model model, RedirectAttributes redirectAttributes, ProductRepr product) {
        logger.info("Save product method " + product.toString());
        model.addAttribute("activePage", "Products");

        try {
            productService.save(product);
        } catch (Exception ex) {
            logger.error("Problem with creating or updating product", ex);
            redirectAttributes.addFlashAttribute("error", true);
            if (product.getId() == null) {
                return "redirect:/product/new";
            }
            return "redirect:/product" + "/edit/" + product.getId();
        }
        return "redirect:/product";
    }

    @GetMapping(value="/product/edit/{id}")
    public String findProductById(Model model, @PathVariable(value="id") long id) {
        logger.info("Find product by Id method");
        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }

    @DeleteMapping("/product")
    public String delete(Model model, @PathVariable("id") Long id) {
        logger.info("Delete product width id {} ", id);
        model.addAttribute("activePage", "Products");
        productService.deleteById(id);
        return "redirect:/product";
    }
}
