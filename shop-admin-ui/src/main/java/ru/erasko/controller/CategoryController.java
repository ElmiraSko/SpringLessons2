package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.erasko.model.Category;
import ru.erasko.repo.CategoryRepository;
import ru.erasko.rest.NotFoundException;

@RequestMapping
@Controller
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public String categoryList(Model model) {
        logger.info("Category list");
        model.addAttribute("activePage", "Categories");
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/category/create")
    public String createCategory(Model model) {
        logger.info("Create new category");
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Categories");
        model.addAttribute("category", new Category());
        return "category_form";
    }

    @PostMapping("/category/save")
    public String saveCategory(Model model, RedirectAttributes redirectAttributes, Category category) {
        logger.info("Save category method");

        model.addAttribute("activePage", "Categories");
        try {
            categoryRepository.save(category);
        } catch (Exception ex) {
            logger.error("Problem with creating or updating category", ex);
            redirectAttributes.addFlashAttribute("error", true);
            if (category.getId() == null) {
                return "redirect:/category/create";
            }
            return "redirect:/category/edit";
        }
        return "redirect:/categories";
    }

    @GetMapping("/category/edit")
    public String editCategory(@RequestParam("id") long id, Model model) {
        logger.info("Edit category width id {} ", id);
        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Categories");
        model.addAttribute("category", categoryRepository.findById(id)
                .orElseThrow(() ->new NotFoundException()));
        return "category_form";
    }

    @DeleteMapping("/category")
    public String delete(Model model, @RequestParam("id") long id) {
        logger.info("Delete category width id {} ", id);
        model.addAttribute("activePage", "Categories");
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }

}
