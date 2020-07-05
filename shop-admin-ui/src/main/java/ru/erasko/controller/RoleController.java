package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.erasko.model.Role;
import ru.erasko.repo.RoleRepository;
import ru.erasko.rest.NotFoundException;

@RequestMapping("/role")
@Controller
public class RoleController {

    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public RoleController (RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String rolePage(Model model) {
        logger.info("Role list");
        model.addAttribute("roles", roleRepository.findAll());
        return "roles";
    }

    @GetMapping("new")
    public String createRole(Model model) {
        logger.info("Create new role form");

        model.addAttribute("role", new Role());
        return "role";
    }

    @PostMapping("save")
    public String saveNewRole(Role role) {
        logger.info("Save new role");

        roleRepository.save(role);
        return "redirect:/role";
    }

    @GetMapping("edit")
    public String editRoles(@RequestParam("id") long id, Model model) {
        logger.info("Edit role width id {} ", id);

        model.addAttribute("role", roleRepository.findById(id)
                .orElseThrow(() ->new NotFoundException()));
        return "role";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete role width id {} ", id);

        roleRepository.deleteById(id);
        return "redirect:/role";
    }
}
