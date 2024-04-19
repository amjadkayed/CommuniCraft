package com.communicate_craft;

import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.dto.SkillDTO;
import com.communicate_craft.dto.UserPersonalInfo;
import com.communicate_craft.enums.Role;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.SkillCategory;
import com.communicate_craft.service.AdminService;
import com.communicate_craft.service.SkillCategoryService;
import com.communicate_craft.service.SkillService;
import com.communicate_craft.service_implementation.LocationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class CommunicateCraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunicateCraftApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(LocationServiceImpl locationService, SkillCategoryService skillCategoryService, AdminService adminService, SkillService skillService) {
        return args -> {
            log.info("---------- The application has started on port 1218 ----------");
            // add 4 locations
            String country = "Palestine";
            locationService.saveLocation(new Location("Nablus", "West-Bank", country));
            locationService.saveLocation(new Location("Jerusalem", "West-Bank", country));
            locationService.saveLocation(new Location("Gaza", "Gaza-Stripe", country));
            locationService.saveLocation(new Location("Rafah", "Gaza-Stripe", country));
            log.info("locations were added successfully :)");

            // add 10 skill categories
            skillCategoryService.addCategory(new SkillCategory("Woodworking"));
            skillCategoryService.addCategory(new SkillCategory("Metalworking"));
            skillCategoryService.addCategory(new SkillCategory("Textiles"));
            skillCategoryService.addCategory(new SkillCategory("Pottery and Ceramics"));
            skillCategoryService.addCategory(new SkillCategory("Jewelry Making"));
            skillCategoryService.addCategory(new SkillCategory("Visual Arts"));
            skillCategoryService.addCategory(new SkillCategory("Glass work"));
            skillCategoryService.addCategory(new SkillCategory("Culinary Arts"));
            skillCategoryService.addCategory(new SkillCategory("Paper Crafts"));
            skillCategoryService.addCategory(new SkillCategory("Floral Crafts"));
            log.info("skill categories were added successfully :)");

            // add skills for each category
            skillService.addSkill(new SkillDTO("Furniture Design", 1L));
            skillService.addSkill(new SkillDTO("Joinery", 1L));

            skillService.addSkill(new SkillDTO("Blacksmithing", 2L));

            skillService.addSkill(new SkillDTO("Sewing", 3L));
            skillService.addSkill(new SkillDTO("Embroidery", 3L));

            skillService.addSkill(new SkillDTO("Hand Building", 4L));

            skillService.addSkill(new SkillDTO("Chain Making", 5L));
            skillService.addSkill(new SkillDTO("Stone Setting", 5L));

            skillService.addSkill(new SkillDTO("Painting", 6L));
            skillService.addSkill(new SkillDTO("Sketching", 6L));
            skillService.addSkill(new SkillDTO("Digital Art", 6L));

            skillService.addSkill(new SkillDTO("Stained Glass", 7L));
            skillService.addSkill(new SkillDTO("Glass Etching", 7L));

            skillService.addSkill(new SkillDTO("Baking", 8L));
            skillService.addSkill(new SkillDTO("Chocolate Making", 8L));

            skillService.addSkill(new SkillDTO("origami", 9L));
            skillService.addSkill(new SkillDTO("scrapbooking", 9L));

            skillService.addSkill(new SkillDTO("decorative wreath making", 10L));
            skillService.addSkill(new SkillDTO("arrangement of flowers", 10L));

            // add Amjad as admin
            adminService.register(new RegisterRequest(new UserPersonalInfo("admin1", "Amjad", "Kayed", "admin1@communicraft.com",
                    "admin1234", "0599887766"), 1L, Role.ADMIN));
            log.info("Amjad is an admin now o_o");
        };
    }
}
