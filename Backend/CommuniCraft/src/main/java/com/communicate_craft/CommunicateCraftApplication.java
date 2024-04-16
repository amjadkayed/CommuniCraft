package com.communicate_craft;

import com.communicate_craft.authentication.AuthenticationServiceImpl;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.location.Location;
import com.communicate_craft.location.LocationServiceImpl;
import com.communicate_craft.skill_category.SkillCategory;
import com.communicate_craft.skill_category.SkillCategoryService;
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
    public CommandLineRunner initDatabase(LocationServiceImpl locationService, SkillCategoryService skillCategoryService) {
        return args -> {
            log.info("---------- The application has started on port 1218 ----------");
            // add 5 locations
            locationService.saveLocation(new Location("Nablus", "West-Bank", "Palestine"));
            locationService.saveLocation(new Location("Jerusalem", "West-Bank", "Palestine"));
            locationService.saveLocation(new Location("Jerico", "West-Bank", "Palestine"));
            locationService.saveLocation(new Location("Gaza", "Gaza-Stripe", "Palestine"));
            locationService.saveLocation(new Location("Rafah", "Gaza-Stripe", "Palestine"));
            log.info("locations were added successfully :)");

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
