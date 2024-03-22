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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkillCategoryServiceTest {

    @Mock
    private SkillCategoryRepository repository;

    @InjectMocks
    private SkillCategoryService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSkillCategoryById_returnsCategoryWhenExists() {
        SkillCategory skillCategory = new SkillCategory();
        when(repository.findById(1)).thenReturn(Optional.of(skillCategory));

        Optional<SkillCategory> result = service.getSkillCategoryById(1);

        assertTrue(result.isPresent());
        assertEquals(skillCategory, result.get());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void getSkillCategoryById_throwsExceptionWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getSkillCategoryById(1));
        verify(repository, times(1)).findById(1);
    }

    @Test
    void saveSkillCategory_returnsSavedCategory() {
        SkillCategory skillCategory = new SkillCategory();
        when(repository.save(skillCategory)).thenReturn(skillCategory);

        SkillCategory result = service.saveSkillCategory(skillCategory);

        assertEquals(skillCategory, result);
        verify(repository, times(1)).save(skillCategory);
    }

    @Test
    void saveSkillCategory_throwsExceptionWhenDuplicated() {
        SkillCategory skillCategory = new SkillCategory();
        when(repository.save(skillCategory)).thenThrow(new RuntimeException());

        assertThrows(DuplicatedFieldException.class, () -> service.saveSkillCategory(skillCategory));
        verify(repository, times(1)).save(skillCategory);
    }

    @Test
    void updateSkillCategory_returnsUpdatedCategoryWhenExists() {
        SkillCategory skillCategory = new SkillCategory();
        skillCategory.setSkillsCategoryId(1);
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(skillCategory)).thenReturn(skillCategory);

        SkillCategory result = service.updateSkillCategory(skillCategory);

        assertEquals(skillCategory, result);
        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).save(skillCategory);
    }

    @Test
    void updateSkillCategory_throwsExceptionWhenNotExists() {
        SkillCategory skillCategory = new SkillCategory();
        skillCategory.setSkillsCategoryId(1);
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.updateSkillCategory(skillCategory));
        verify(repository, times(1)).existsById(1);
    }

    @Test
    void deleteSkillCategory_deletesWhenExists() {
        when(repository.existsById(1)).thenReturn(true);

        service.deleteSkillCategory(1);

        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void deleteSkillCategory_throwsExceptionWhenNotExists() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deleteSkillCategory(1));
        verify(repository, times(1)).existsById(1);
    }
}