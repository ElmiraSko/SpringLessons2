package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.erasko.service.CartService;
import ru.erasko.service.ProductService;
import ru.erasko.service.model.LineItem;

@RequestMapping("/cart")
@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("lineItems", cartService.getLineItems());
        model.addAttribute("subTotal", cartService.getSubTotal());
        return "cart";
    }

    @PostMapping
    public String updateCart(LineItem lineItem) {
        lineItem.setProductRepr(productService.findById(lineItem.getProductId())
                .orElseThrow(IllegalArgumentException::new));
        cartService.updateCart(lineItem);
        return "redirect:/cart";
    }

    @DeleteMapping
    public String deleteLineItem(@ModelAttribute LineItem lineItem) {
        logger.info("delete LineItem method " + lineItem.getProductId());
        cartService.removeProduct(lineItem);
        return "redirect:/cart";
    }

    @GetMapping(value="/delete/{productId}")
    public String findProductById(@PathVariable(value="productId") Long productId, LineItem lineItem) {
        logger.info("Delete lineItem width productId " + productId);
        lineItem.setProductRepr(productService.findById(productId).orElseThrow(IllegalArgumentException::new));
        cartService.removeProduct(lineItem);
        return "redirect:/cart";
    }

// @ModelAttribute("lineItem") LineItem lineItems возвращает null
    @PostMapping(value="/minus")
    public String updateCartQty(@ModelAttribute("lineItem") LineItem lineItems) {
        logger.info("Метод для уменьшения количества товара " + lineItems.getQty());
// код метода
        return "redirect:/cart";
    }

}

