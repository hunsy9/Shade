package com.oslab.agent.repository.organization.category;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.repository.organization.mapper.OrgCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrgCategoryRepository {
    private final OrgCategoryMapper orgCategoryMapper;

    public boolean addNewCategory(CategoryDto categoryDto){
        return orgCategoryMapper.addNewCategory(categoryDto);
    }

    public boolean updateCategory(CategoryDto categoryDto){
        return orgCategoryMapper.updateCategory(categoryDto);
    }

    public boolean deleteCategory(CategoryDto categoryDto){
        return orgCategoryMapper.deleteCategory(categoryDto);
    }
}
