package com.communicate_craft;

import com.communicate_craft.admin.AdminService;
import com.communicate_craft.authentication.AuthenticationService;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.location.Location;
import com.communicate_craft.location.LocationServiceImpl;
import com.communicate_craft.skill_feature.categories.SkillCategory;
import com.communicate_craft.skill_feature.categories.SkillCategoryService;
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
    public CommandLineRunner initDatabase(LocationServiceImpl locationService, SkillCategoryService skillCategoryService, AdminService adminService) {
        return args -> {
            log.info("---------- The application has started on port 1218 ----------");
            // add 4 locations
            String country = "Palestine";
            locationService.saveLocation(new Location("Nablus", "West-Bank", country));
            locationService.saveLocation(new Location("Jerusalem", "West-Bank", country));
            locationService.saveLocation(new Location("Gaza", "Gaza-Stripe", country));
            locationService.saveLocation(new Location("Rafah", "Gaza-Stripe", country));
            log.info("locations were added successfully :)");

            // add Amjad as admin
            adminService.registerNewAdmin(new RegisterRequest("admin1", "Amjad", "Kayed", "admin1@communicraft.com",
                    "admin1234", "0599887766", 1, Role.ADMIN));
            log.info("Amjad is an admin now o_o");

            // add 11 skill categories
            skillCategoryService.addCategory(new SkillCategory("Woodworking"));
            skillCategoryService.addCategory(new SkillCategory("Metalworking"));
            skillCategoryService.addCategory(new SkillCategory("Textiles"));
            skillCategoryService.addCategory(new SkillCategory("Pottery and Ceramics"));
            skillCategoryService.addCategory(new SkillCategory("Jewelry Making"));
            skillCategoryService.addCategory(new SkillCategory("Visual Arts"));
            skillCategoryService.addCategory(new SkillCategory("Glass work"));
            skillCategoryService.addCategory(new SkillCategory("Beauty and Cosmetics"));
            skillCategoryService.addCategory(new SkillCategory("Culinary Arts"));
            skillCategoryService.addCategory(new SkillCategory("Paper Crafts"));
            skillCategoryService.addCategory(new SkillCategory("Floral Crafts"));
            log.info("skill categories were added successfully :)");

        };
    }
}
