package com.oslab.agent.repository.organization.mapper;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgCategoryMapper {
    boolean addNewCategory(CategoryDto categoryDto);
    boolean updateCategory(CategoryDto categoryDto);
    boolean deleteCategory(CategoryDto categoryDto);
}
