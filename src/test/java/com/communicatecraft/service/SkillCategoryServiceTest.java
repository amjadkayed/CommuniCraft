package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.repository.SkillCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SkillCategoryServiceTest {

    @Mock
    SkillCategoryRepository repository;

    @InjectMocks
    SkillCategoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSkillCategories_returnsAllCategories() {
        service.getAllSkillCategories();
        verify(repository, times(1)).findAll();
    }

    @Test
    void getSkillCategoryById_returnsCategoryWhenExists() {
        SkillCategory skillCategory = new SkillCategory();
        skillCategory.setSkillsCategoryId(1);
        when(repository.findById(1)).thenReturn(Optional.of(skillCategory));

        SkillCategory result = service.getSkillCategoryById(1);

        assertEquals(skillCategory, result);
    }

    @Test
    void getSkillCategoryById_throwsExceptionWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getSkillCategoryById(1));
    }

    @Test
    void saveSkillCategory_savesCategory() {
        SkillCategory skillCategory = new SkillCategory();
        when(repository.save(skillCategory)).thenReturn(skillCategory);

        SkillCategory result = service.saveSkillCategory(skillCategory);

        assertEquals(skillCategory, result);
    }

    @Test
    void saveSkillCategory_throwsExceptionWhenDuplicated() {
        SkillCategory skillCategory = new SkillCategory();
        when(repository.save(skillCategory)).thenThrow(new RuntimeException());

        assertThrows(DuplicatedFieldException.class, () -> service.saveSkillCategory(skillCategory));
    }

    @Test
    void updateSkillCategory_updatesCategoryWhenExists() {
        SkillCategory skillCategory = new SkillCategory();
        skillCategory.setSkillsCategoryId(1);
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(skillCategory)).thenReturn(skillCategory);

        SkillCategory result = service.updateSkillCategory(skillCategory);

        assertEquals(skillCategory, result);
    }

    @Test
    void updateSkillCategory_throwsExceptionWhenNotExists() {
        SkillCategory skillCategory = new SkillCategory();
        skillCategory.setSkillsCategoryId(1);
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.updateSkillCategory(skillCategory));
    }

    @Test
    void deleteSkillCategory_deletesCategoryWhenExists() {
        when(repository.existsById(1)).thenReturn(true);

        service.deleteSkillCategory(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void deleteSkillCategory_throwsExceptionWhenNotExists() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deleteSkillCategory(1));
    }
}