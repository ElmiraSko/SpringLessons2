package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.erasko.model.User;
import ru.erasko.repo.RoleRepository;
import ru.erasko.rest.NotFoundException;
import ru.erasko.service.RoleService;
import ru.erasko.service.UserService;

import javax.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService  userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String userList(Model model) {
        logger.info("User list");

        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        logger.info("Create user form");

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());

        logger.info("Create user form 2 - " + model.getAttribute("roles").toString());
        return "user";
    }

    @PostMapping("save")
    public String saveUser(@Valid User user, BindingResult bindingResult) {

        logger.info("Save user method " + user.getRoles().toString());

        // стандартная (внутренняя) валидация
        if (bindingResult.hasErrors()) {
            return "user";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("edit")
    public String createUser(@RequestParam("id") Long id, Model model) {
        logger.info("Edit user width id {} ", id);

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() ->new NotFoundException("Not found user by Id")));
        model.addAttribute("roles", roleService.findAll());
        return "user";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete user width id {} ", id);

        userService.delete(id);
        return "redirect:/user";
    }
}