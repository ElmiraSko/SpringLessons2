package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.erasko.controller.repr.ProductRepr;
import ru.erasko.repo.CategoryRepository;
import ru.erasko.rest.NotFoundException;
import ru.erasko.service.ProductServiceImpl;
import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/product")
@Controller
@ControllerAdvice
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductServiceImpl productService;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductController(ProductServiceImpl productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;

    }

    @GetMapping
    public String productList(Model model,
                                 @RequestParam(name = "minCost", required = false) BigDecimal minCost,
                                 @RequestParam(name = "maxCost", required = false) BigDecimal maxCost,
                                 @RequestParam(name = "productTitle", required = false) String productTitle) {

        List<ProductRepr> productList = productService.findAllByFilter(minCost, maxCost, productTitle);

        model.addAttribute("productsList", productList);
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute("productTitle", productTitle);
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("Create product form");
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", new ProductRepr());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product";
    }

    @PostMapping
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

    @GetMapping(value="/edit/{id}")
    public String findProductById(Model model, @PathVariable(value="id") long id) {
        logger.info("Find product by Id method");
        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
        model.addAttribute("categories", categoryRepository.findAll());
        return "product";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete product width id {} ", id);

        productService.deleteById(id);
        return "redirect:/product";
    }

//    @PostMapping(value="/save/{id}")
//    public String saveProductById(@ModelAttribute("editProduct") ProductRepr product,
//                                  BindingResult bindingResult) {
//        logger.info("Save product by id" + product.toString());
//
//        if (bindingResult.hasErrors()) {
//            return "edit-product";
//        }
//        try {
//            productService.save(product);
//        } catch (IOException e) {
//            logger.error("Problem with creating or updating product", e);
//            e.printStackTrace();
//        }
//        return "redirect:/product";
//    }
}
