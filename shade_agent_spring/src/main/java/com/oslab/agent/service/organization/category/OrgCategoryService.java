package com.oslab.agent.service.organization.category;

import com.oslab.agent.model.transfer.categoryDto.*;
import com.oslab.agent.repository.organization.category.OrgCategoryRepository;
import com.oslab.agent.service.server.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrgCategoryService implements OrgCategoryServiceInterface{

    private final OrgCategoryRepository orgCategoryRepository;
    private final ServerService serverService;

    public Long addNewCategoryL1(CategoryDto categoryDto) {
        return orgCategoryRepository.addNewCategoryL1(categoryDto);
    }

    public Long addNewCategoryL2(CategoryDtoL2 categoryDtoL2) {
        return orgCategoryRepository.addNewCategoryL2(categoryDtoL2);
    }

    public Long editCategory(EditCategoryDto editCategoryDto) {
        return orgCategoryRepository.editCategory(editCategoryDto);
    }

    public Long delCategory(DelCategoryDto delCategoryDto) throws SQLException {
        List<String> categoryKeys = delCategoryDto.getCategoryKeys();
        String delType = delCategoryDto.getType();
        System.out.println("Service/Delete Type: " + delType);
        if(delType.equals("L2")){
            String matchedKey = categoryKeys.get(0);
            String categoryId = matchedKey.split(":")[0];
            System.out.println("L2 MatchedKey: " + matchedKey);
            orgCategoryRepository.delCategoryL2(Long.parseLong(categoryId));
        }
        else{
            System.out.println("DelType: L1");
            System.out.println(categoryKeys.toString());
            for(String matchedKey : categoryKeys){
                String categoryId = matchedKey.split(":")[0];
                orgCategoryRepository.delCategory(Long.parseLong(categoryId));
            }
        }
        System.out.println(delCategoryDto.getTargetServerList());
        for(Long server_id : delCategoryDto.getTargetServerList()){
            serverService.deleteServer(server_id.toString());
        }
        return null;
    }

    public ReFetchCategoryDto reFetchCategory(Integer org_id,Integer project_id) throws SQLException {
        return orgCategoryRepository.reFetchCategory(org_id,project_id);
    }

    public boolean updateCategory(CategoryDto categoryDto) {return orgCategoryRepository.updateCategory(categoryDto);}

    public boolean deleteCategory(CategoryDto categoryDto) {
        return orgCategoryRepository.deleteCategory(categoryDto);
    }
}
