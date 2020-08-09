package ru.erasko.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.controller.repr.ProductRepr;
import ru.erasko.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceTest.class);
    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testEmptyCart() {
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddFirstProduct() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setPrice(new BigDecimal(4500));

        cartService.addProductQty(expectedProduct, "black", "metal", 3);
        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("black", lineItem.getColor());
        assertEquals("metal", lineItem.getMaterial());
        assertEquals(3, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductRepr());
        assertEquals(expectedProduct.getId(), lineItem.getProductRepr().getId());
        assertEquals(expectedProduct.getName(), lineItem.getProductRepr().getName());
    }

    @Test
    public void testAddOtherProductQty() {
// Добавиление первого продукта
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product1");
        expectedProduct.setPrice(new BigDecimal(150));
        cartService.addProductQty(expectedProduct, "color", "material", 1);
// Добавиление второго продукта
        ProductRepr expProduct2 = new ProductRepr();
        expProduct2.setId(2L);
        expProduct2.setName("Product2");
        expProduct2.setPrice(new BigDecimal(110));
        cartService.addProductQty(expProduct2, "color2", "material2", 5);

        List<LineItem> lineItems = cartService.getLineItems();

        assertEquals(2, lineItems.size(),"2 lineItem должно быть в корзине");
        assertEquals(5, lineItems.get(1).getQty(), "Количество Product2 должно быть 5 шт");
        assertEquals(1,lineItems.get(0).getQty(),"Количество товара1 в корзине 1 шт");
        assertEquals(5, lineItems.get(1).getQty(),"Количество товара2 в корзине 5 шт");

        assertEquals(expectedProduct.getName(), lineItems.get(0).getProductRepr().getName());
        assertEquals(expProduct2.getName(), lineItems.get(1).getProductRepr().getName());
        assertEquals(expProduct2.getPrice(), lineItems.get(1).getProductRepr().getPrice());
        assertEquals(expProduct2.getBrandName(), lineItems.get(1).getProductRepr().getBrandName());
    }

    @Test
    public void testRemoveProduct() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Name1");
        expectedProduct.setPrice(new BigDecimal(500));
        cartService.addProductQty(expectedProduct, "grey", "metal", 2);

        ProductRepr expectedProduct2 = new ProductRepr();
        expectedProduct2.setId(2L);
        expectedProduct2.setName("Name2");
        expectedProduct2.setPrice(new BigDecimal(400));
        cartService.addProductQty(expectedProduct2, "yellow", "metal", 4);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(2, lineItems.size());
 // Удалили lineItem
        LineItem lineItem = lineItems.get(0);

        cartService.removeProduct(lineItem);

        lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        assertEquals(expectedProduct.getId(), lineItems.get(0).getProductRepr().getId());
        assertEquals(expectedProduct.getName(), lineItems.get(0).getProductRepr().getName());
        assertEquals(expectedProduct.getPrice(), lineItems.get(0).getProductRepr().getPrice());
        assertEquals(expectedProduct.getCountInStock(), lineItems.get(0).getProductRepr().getCountInStock());
    }

    @Test
    public void testGetSubTotal() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Name1");
        expectedProduct.setPrice(new BigDecimal(500));
        cartService.addProductQty(expectedProduct, "grey", "metal", 2);

        assertNotNull(cartService.getLineItems());
        assertEquals(1, cartService.getLineItems().size());
        assertEquals(expectedProduct.getPrice().multiply(new BigDecimal(2)),cartService.getSubTotal());
    }

    @Test
    public void testUpdateCart() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Name");
        expectedProduct.setPrice(new BigDecimal(70));
        cartService.addProductQty(expectedProduct, "green", "metal", 3);

        assertNotNull(cartService.getLineItems());
        assertEquals(1, cartService.getLineItems().size());
        assertEquals(expectedProduct.getId(),cartService.getLineItems().get(0).getProductRepr().getId());
        assertEquals(expectedProduct.getName(),cartService.getLineItems().get(0).getProductRepr().getName());
        assertEquals(expectedProduct.getPrice(),cartService.getLineItems().get(0).getProductRepr().getPrice());
        assertEquals(3,cartService.getLineItems().get(0).getQty());

        LineItem lineItem = new LineItem(expectedProduct, "green", "metal");
        lineItem.setQty(15);

        cartService.updateCart(lineItem);

        assertNotNull(cartService.getLineItems());
        assertEquals(1, cartService.getLineItems().size());
        assertEquals(expectedProduct.getId(),cartService.getLineItems().get(0).getProductRepr().getId());
        assertEquals(expectedProduct.getName(),cartService.getLineItems().get(0).getProductRepr().getName());
        assertEquals(expectedProduct.getPrice(),cartService.getLineItems().get(0).getProductRepr().getPrice());
        assertEquals(15,cartService.getLineItems().get(0).getQty());
    }
}
