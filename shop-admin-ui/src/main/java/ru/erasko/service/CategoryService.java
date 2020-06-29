package ru.erasko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erasko.model.Category;
import ru.erasko.model.Role;
import ru.erasko.model.User;
import ru.erasko.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Optional<Category> findCategoryByName(String categoryTitle) {
        return categoryRepository.findByTitle(categoryTitle);
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

}
