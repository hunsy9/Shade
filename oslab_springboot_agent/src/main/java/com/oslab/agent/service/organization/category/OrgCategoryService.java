package com.oslab.agent.service.organization.category;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.repository.organization.category.OrgCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrgCategoryService implements OrgCategoryServiceInterface{

    private final OrgCategoryRepository orgCategoryRepository;

    public boolean addNewCategory(CategoryDto categoryDto) {
        return orgCategoryRepository.addNewCategory(categoryDto);
    }

    public boolean updateCategory(CategoryDto categoryDto) {return orgCategoryRepository.updateCategory(categoryDto);}

    public boolean deleteCategory(CategoryDto categoryDto) {
        return orgCategoryRepository.deleteCategory(categoryDto);
    }
}
