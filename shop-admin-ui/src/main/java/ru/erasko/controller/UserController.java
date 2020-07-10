package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.erasko.controller.repr.UserRepr;
import ru.erasko.repo.RoleRepository;
import ru.erasko.rest.NotFoundException;
import ru.erasko.service.UserServiceImpl;

import javax.validation.Valid;


@RequestMapping
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    public String userList(Model model) {
        logger.info("User list");
        model.addAttribute("activePage", "Users");
        model.addAttribute("users", userServiceImpl.findAll());
        return "users";
    }

    @GetMapping("/user/create")
    public String createUser(Model model) {
        logger.info("Create user form");
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Users");
        model.addAttribute("user", new UserRepr());
        model.addAttribute("roles", roleRepository.findAll());

        logger.info("Create user form - " + model.getAttribute("roles").toString());
        return "user_form";
    }

    @PostMapping("/user/save")
    public String saveUser(@Valid UserRepr user, Model model, BindingResult bindingResult) {
        logger.info("Save user method  " + user.getRoles().toString());
        model.addAttribute("activePage", "Users");
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        logger.info("Save user method  " + user.toString());
        userServiceImpl.save(user);
        return "redirect:/users";
    }


    @GetMapping("/user/edit")
    public String createUser(@RequestParam("id") Long id, Model model) {
        logger.info("Edit user width id {} ", id);

        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Users");
        model.addAttribute("user", userServiceImpl.findById(id)
                .orElseThrow(() ->new NotFoundException()));
        model.addAttribute("roles", roleRepository.findAll());
        return "user_form";
    }

    @DeleteMapping("/users")
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete user width id {} ", id);

        userServiceImpl.delete(id);
        return "redirect:/users";
    }
}