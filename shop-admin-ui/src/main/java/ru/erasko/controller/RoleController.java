package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.erasko.model.Role;
import ru.erasko.rest.NotFoundException;
import ru.erasko.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleController {

    private RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public RoleController (RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String rolePage(Model model) {
        logger.info("Role list");
        model.addAttribute("roles", roleService.findAll());
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

        roleService.saveRole(role);
        return "redirect:/role";
    }

    @GetMapping("edit")
    public String editRoles(@RequestParam("id") long id, Model model) {
        logger.info("Edit role width id {} ", id);

        model.addAttribute("role", roleService.findById(id)
                .orElseThrow(() ->new NotFoundException("Not found role by Id")));
        return "role";
    }

    @DeleteMapping
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete role width id {} ", id);

        roleService.delete(id);
        return "redirect:/role";
    }
}
