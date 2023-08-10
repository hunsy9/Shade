package com.oslab.agent.repository.organization.mapper;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.model.transfer.categoryDto.CategoryDtoL2;
import com.oslab.agent.model.transfer.categoryDto.EditCategory;
import com.oslab.agent.model.transfer.categoryDto.EditCategoryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgCategoryMapper {
    Long addNewCategoryL1(CategoryDto categoryDto);
    Long addNewCategoryL2(CategoryDtoL2 categoryDtoL2);
    Long editCategory(EditCategory editCategory);
    CategoryDto getNext(Long category_id);
    Long insertNewCategoryL2(CategoryDto categoryDto);
    Long delCategory(Long categoryId);
    Long delCategoryL2(Long categoryId);
    boolean updateCategory(CategoryDto categoryDto);
    boolean deleteCategory(CategoryDto categoryDto);
}
