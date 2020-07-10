package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.erasko.model.Role;
import ru.erasko.repo.RoleRepository;
import ru.erasko.rest.NotFoundException;

@RequestMapping
@Controller
public class RoleController {

    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public RoleController (RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles")
    public String rolePage(Model model) {
        logger.info("Role list");
        model.addAttribute("activePage", "Roles");
        model.addAttribute("roles", roleRepository.findAll());
        return "roles";
    }

    @GetMapping("/role/create")
    public String createRole(Model model) {
        logger.info("Create new role form");
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Roles");
        model.addAttribute("role", new Role());
        return "role_form";
    }

    @PostMapping("/role/save")
    public String saveNewRole(Model model, RedirectAttributes redirectAttributes, Role role) {
        logger.info("Save new role");
        model.addAttribute("activePage", "Roles");

        try {
            roleRepository.save(role);
        } catch (Exception ex) {
            logger.error("Problem with creating or updating product", ex);
            redirectAttributes.addFlashAttribute("error", true);
            if (role.getId() == null) {
                return "redirect:/role/create";
            }
            return "redirect:/role/edit/";
        }
        return "redirect:/roles";
    }

    @GetMapping("/role/edit")
    public String editRoles(@RequestParam("id") long id, Model model) {
        logger.info("Edit role width id {} ", id);
        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Roles");
        model.addAttribute("role", roleRepository.findById(id)
                .orElseThrow(() ->new NotFoundException()));
        return "role_form";
    }

    @DeleteMapping("/role")
    public String delete(@RequestParam("id") long id) {
        logger.info("Delete role width id {} ", id);

        roleRepository.deleteById(id);
        return "redirect:/roles";
    }
}
