package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.Skill;
import com.communicatecraft.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkillServiceTest {

    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSkillsByCategoryId_returnsAllSkills() {
        skillService.getAllSkillsByCategoryId(1);
        verify(skillRepository, times(1)).getAllSkillsBySkillCategoryId(1);
    }

    @Test
    void getSkillById_returnsSkillWhenExists() {
        Skill skill = new Skill();
        skill.setSkillId(1);
        when(skillRepository.findById(1)).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillById(1);

        assertEquals(skill, result);
    }

    @Test
    void getSkillById_throwsExceptionWhenNotExists() {
        when(skillRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> skillService.getSkillById(1));
    }

    @Test
    void saveSkill_savesSkill() {
        Skill skill = new Skill();
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill result = skillService.saveSkill(skill);

        assertEquals(skill, result);
    }

    @Test
    void saveSkill_throwsExceptionWhenDuplicated() {
        Skill skill = new Skill();
        when(skillRepository.save(skill)).thenThrow(new RuntimeException());

        assertThrows(DuplicatedFieldException.class, () -> skillService.saveSkill(skill));
    }

    @Test
    void updateSkill_updatesSkillWhenExists() {
        Skill skill = new Skill();
        skill.setSkillId(1);
        when(skillRepository.existsById(1)).thenReturn(true);
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill result = skillService.updateSkill(skill);

        assertEquals(skill, result);
    }

    @Test
    void updateSkill_throwsExceptionWhenNotExists() {
        Skill skill = new Skill();
        skill.setSkillId(1);
        when(skillRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> skillService.updateSkill(skill));
    }

    @Test
    void deleteSkill_deletesSkillWhenExists() {
        when(skillRepository.existsById(1)).thenReturn(true);

        skillService.deleteSkill(1);

        verify(skillRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteSkill_throwsExceptionWhenNotExists() {
        when(skillRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> skillService.deleteSkill(1));
    }
}