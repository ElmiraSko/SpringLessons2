package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.erasko.model.Category;
import ru.erasko.repo.CategoryRepository;
import ru.erasko.rest.NotFoundException;

@RequestMapping("/category")
@Controller
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String categoryList(Model model) {
        logger.info("Category list");

        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("new")
    public String createCategory(Model model) {
        logger.info("Create new category");

        model.addAttribute("category", new Category());
        return "category";
    }

    @PostMapping("save")
    public String saveCategory(Category category) {
        logger.info("Save category method");

        categoryRepository.save(category);
        return "redirect:/category";
    }

    @GetMapping("edit")
    public String editCategory(@RequestParam("id") long id, Model model) {
        logger.info("Edit category width id {} ", id);

        model.addAttribute("category", categoryRepository.findById(id)
                .orElseThrow(() ->new NotFoundException()));
        return "category";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete category width id {} ", id);

        categoryRepository.deleteById(id);
        return "redirect:/category";
    }

}
