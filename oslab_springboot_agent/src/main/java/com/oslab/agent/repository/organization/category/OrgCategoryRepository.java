package com.oslab.agent.repository.organization.category;

import com.oslab.agent.model.entity.orgEntity.OrgServer;
import com.oslab.agent.model.entity.orgEntity.ProjectInfo;
import com.oslab.agent.model.transfer.categoryDto.*;
import com.oslab.agent.repository.organization.mapper.OrgCategoryMapper;
import com.oslab.agent.repository.organization.mapper.OrgMasterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrgCategoryRepository {
    private final OrgCategoryMapper orgCategoryMapper;

    private final OrgMasterMapper orgMasterMapper;

    public Long addNewCategoryL1(CategoryDto categoryDto) {
        System.out.println(categoryDto.toString());
        orgCategoryMapper.addNewCategoryL1(categoryDto);
        return categoryDto.getCategory_id();
    }

    public Long addNewCategoryL2(CategoryDtoL2 categoryDtoL2) {
        CategoryDto categoryDto = orgCategoryMapper.getNext(categoryDtoL2.getCategory_id());
        System.out.println(categoryDto.getNext());
        if(categoryDto.getNext() == null){
            orgCategoryMapper.addNewCategoryL2(categoryDtoL2);
        }
        else {
            categoryDto.setNext(categoryDtoL2.getNext());
            orgCategoryMapper.insertNewCategoryL2(categoryDto);
        }
        return categoryDtoL2.getCategory_id();
    }

    public Long editCategory(EditCategoryDto editCategoryDto) {
        for (String categoryKey : editCategoryDto.getCategoryKeys()) {
            String category_id = categoryKey.split(":")[0];
            String prev = categoryKey.split(":")[1];
            String next = categoryKey.split(":")[2];
            boolean typeCheck = editCategoryDto.getType().equals("L1");
            EditCategory editCategory = EditCategory.builder()
                    .category_id(Long.parseLong(category_id))
                    .prev(typeCheck ? editCategoryDto.getPrev() : prev)
                    .next(typeCheck ? next : editCategoryDto.getNext())
                    .build();
            orgCategoryMapper.editCategory(editCategory);
        }
        return null;
    }

    public Long delCategory(Long categoryId) {
        return orgCategoryMapper.delCategory(categoryId);
    }

    public Long delCategoryL2(Long categoryId) {
        return orgCategoryMapper.delCategoryL2(categoryId);
    }

    public ReFetchCategoryDto reFetchCategory(Integer org_id, Integer project_id) throws SQLException {

        List<ProjectInfo> projectInfoList = orgMasterMapper.getProjectInfo(org_id);

        Map<Integer, Map<String, List<String>>> projectIdToCategoryMap = new HashMap<>();
        Map<Integer, Map<String, List<OrgServer>>> projectIdToCategoryIdToServer = new HashMap<>();

        projectInfoList.stream().forEach(projectInfo -> {
            Integer projectId = projectInfo.getProject_id();
            if (projectId == project_id) {
                Integer categoryId = projectInfo.getCategory_id();
                String prev = projectInfo.getPrev();
                String next = projectInfo.getNext();
                Integer serverId = projectInfo.getServer_id();
                String serverName = projectInfo.getServer_name();
                String serverDesc = projectInfo.getServer_desc();

                if (!projectIdToCategoryMap.containsKey(projectId)) { //카테고리 맵을 만들때 projectId 검사
                    projectIdToCategoryMap.put(projectId, new HashMap<>()); //projectId 없으면 프로젝트 아이디를 키, value엔 hashmap넣는다.
                }

                if (!projectIdToCategoryIdToServer.containsKey(projectId)) {
                    projectIdToCategoryIdToServer.put(projectId, new HashMap<>());
                }

                //프로젝트아이디가 카테고리맵에 있다는 뜻이니까 value를 넣어준다.
                Map<String, List<String>> categoryMap = projectIdToCategoryMap.get(projectId);
                Map<String, List<OrgServer>> categoryIdToServer = projectIdToCategoryIdToServer.get(projectId);

                String categoryKey = categoryId.toString() + ":" + prev + ":" + next;

                System.out.println("categoryMap: " + categoryMap);
                System.out.println("categoryIdToServer: " + categoryIdToServer);


                if (!categoryMap.containsKey(prev)) { //카테고리맵의 prev키가 존재하지 않으면 new ArrayList 추가
                    categoryMap.put(prev, new ArrayList<>());
                }


                if (serverId != null) { //server_id가 있을때
                    OrgServer server = OrgServer.builder()
                            .server_id(serverId)
                            .server_name(serverName)
                            .server_desc(serverDesc)
                            .build();
                    if (!categoryIdToServer.containsKey(categoryKey)) {
                        categoryIdToServer.put(categoryKey, new ArrayList<>());
                    }
                    categoryIdToServer.get(categoryKey).add(server);
                } else {
                    categoryIdToServer.put(categoryKey, null);
                }

                if (!categoryMap.get(prev).contains(next)) {
                    categoryMap.get(prev).add(next); //존재하면 prev를 기준으로한 arraylist에 push
                }
            }

        });
        return ReFetchCategoryDto.builder()
                .category(projectIdToCategoryMap.get(project_id))
                .project_server(projectIdToCategoryIdToServer.get(project_id))
                .build();
    }

    public boolean updateCategory(CategoryDto categoryDto) {
        return orgCategoryMapper.updateCategory(categoryDto);
    }

    public boolean deleteCategory(CategoryDto categoryDto) {
        return orgCategoryMapper.deleteCategory(categoryDto);
    }
}
