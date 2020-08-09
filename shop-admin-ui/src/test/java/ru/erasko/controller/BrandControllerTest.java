package ru.erasko.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.erasko.model.Brand;
import ru.erasko.repo.BrandRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class BrandControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BrandRepository brandRepository;

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testNewBrand() throws Exception {
        mvc.perform(post("/brand")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "-1")
                .param("name", "New brand")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brands"));

        Brand brand = new Brand();
        brand.setName("New brand");
        Optional<Brand> actualBrand = brandRepository.findOne(Example.of(brand));

        assertTrue(actualBrand.isPresent());
        assertEquals("New brand", actualBrand.get().getName());

    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testBrandsPage() throws Exception {
        Brand brand = new Brand();
        brand.setName("New brand");
        brandRepository.save(brand);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/brands"));
        result.andExpect(status().isOk())
                .andExpect(view().name("brands"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attributeExists("brands"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testBrandCreatePage() throws Exception {

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/brand/create"));
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("create"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attributeExists("brand"))
                .andExpect(view().name("brand_form"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testEditBrand() throws Exception {
        Brand brand = new Brand();
        brand.setName("Editable");
        brandRepository.save(brand);

        ResultActions result = mvc.perform(MockMvcRequestBuilders.get("/brand/{id}/edit", 1));
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("edit"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attributeExists("brand"))
                .andExpect(view().name("brand_form"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testDeleteBrand() throws Exception {
        Brand brand = new Brand();
        brand.setName("Removable");
        brandRepository.save(brand);

        Brand brand2 = new Brand();
        brand2.setName("Removable2");
        brandRepository.save(brand2);

        ResultActions result = mvc.perform(delete("/brand/{id}/delete", brand.getId()));
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/brands"));
    }

}
