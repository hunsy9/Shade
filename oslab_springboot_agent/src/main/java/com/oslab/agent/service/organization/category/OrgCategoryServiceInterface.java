package com.oslab.agent.service.organization.category;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;

public interface OrgCategoryServiceInterface {
    boolean addNewCategory(CategoryDto categoryDto);
    boolean updateCategory(CategoryDto categoryDto);
    boolean deleteCategory(CategoryDto categoryDto);
}
