package com.oslab.agent.repository.organization.master;

import com.oslab.agent.model.entity.orgEntity.Contributor;
import com.oslab.agent.model.entity.orgEntity.*;
import com.oslab.agent.model.transfer.orgDto.AddOrgReqDto;
import com.oslab.agent.model.transfer.orgDto.OrgReqDto;
import com.oslab.agent.model.transfer.orgDto.RegOrgReqDto;
import com.oslab.agent.repository.organization.mapper.OrgMasterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrgMasterRepository {
    private final OrgMasterMapper orgMasterMapper;

    public Long registerOrganizationToPlatform(RegOrgReqDto regOrgReqDto) throws SQLException {
        orgMasterMapper.registerOrganizationToPlatform(regOrgReqDto);
        return regOrgReqDto.getOrg_id();
    }

    public void deleteOrganizationFromPlatform(RegOrgReqDto regOrgReqDto) throws SQLException{
        orgMasterMapper.deleteOrganizationFromPlatform(regOrgReqDto);
    }

    public boolean addOrganizationToUser(OrgReqDto orgReqDto) throws SQLException{
        return orgMasterMapper.addOrganizationToUser(orgReqDto);
    }

    public void deleteOrganizationFromUser(OrgReqDto orgReqDto) throws SQLException{
        orgMasterMapper.deleteOrganizationFromUser(orgReqDto);
    }

    public boolean addOrganizationToContributor(AddOrgReqDto addOrgReqDto) throws SQLException{
        return orgMasterMapper.addOrganizationToContributor(addOrgReqDto);
    }

    public OrgInfo getOrgInfo(Integer user_id, Integer org_id) throws SQLException {

        List<ProjectInfo> projectInfoList = orgMasterMapper.getProjectInfo(org_id);
        List<Integer> orgUserServerPrivileges = orgMasterMapper.getOrgUserPrivileges(user_id);

        Map<Integer, String> projectIdToProjectName = new HashMap<>();
        Map<Integer, Map<String, List<String>>> projectIdToCategoryMap = new HashMap<>();
        Map<Integer, Map<String, List<OrgServer>>> projectIdToCategoryIdToServer = new HashMap<>();

        projectInfoList.stream().forEach(projectInfo -> {
            Integer projectId = projectInfo.getProject_id();
            String projectName = projectInfo.getProject_name();

            projectIdToProjectName.put(projectId, projectName); // projectId 와 projectName 매핑
            if(projectInfo.getCategory_id() == null){
                return;
            }

            Integer categoryId = projectInfo.getCategory_id();
            String prev = projectInfo.getPrev();
            String next = projectInfo.getNext();
            Integer serverId = projectInfo.getServer_id();
            String serverName = projectInfo.getServer_name();
            String serverDesc = projectInfo.getServer_desc();

            if(!projectIdToCategoryMap.containsKey(projectId)){ //카테고리 맵을 만들때 projectId 검사
                projectIdToCategoryMap.put(projectId, new HashMap<>()); //projectId 없으면 프로젝트 아이디를 키, value엔 hashmap넣는다.
            }

            if(!projectIdToCategoryIdToServer.containsKey(projectId)){
                projectIdToCategoryIdToServer.put(projectId, new HashMap<>());
            }

            //프로젝트아이디가 카테고리맵에 있다는 뜻이니까 value를 넣어준다.
            Map<String, List<String>> categoryMap = projectIdToCategoryMap.get(projectId);
            Map<String, List<OrgServer>> categoryIdToServer = projectIdToCategoryIdToServer.get(projectId);

            String categoryKey = categoryId.toString() + ":" + prev + ":" + next;

            System.out.println("categoryMap: "+categoryMap);
            System.out.println("categoryIdToServer: "+categoryIdToServer);


            if(!categoryMap.containsKey(prev)){ //카테고리맵의 prev키가 존재하지 않으면 new ArrayList 추가
                categoryMap.put(prev, new ArrayList<>());
            }


            if(serverId != null){ //server_id가 있을때
                OrgServer server = OrgServer.builder()
                        .server_id(serverId)
                        .server_name(serverName)
                        .server_desc(serverDesc)
                        .build();
                if(!categoryIdToServer.containsKey(categoryKey)){
                    categoryIdToServer.put(categoryKey, new ArrayList<>());
                }
                categoryIdToServer.get(categoryKey).add(server);
            }
            else {
                categoryIdToServer.put(categoryKey,null);
            }

            if(!categoryMap.get(prev).contains(next)){
                categoryMap.get(prev).add(next); //존재하면 prev를 기준으로한 arraylist에 push
            }
        });
        log.info(projectIdToProjectName.toString());
        log.info(projectIdToCategoryMap.toString());
        log.info(projectIdToCategoryIdToServer.toString());

        OrgInfo orgInfo = assembleOrgInfo(projectIdToProjectName, projectIdToCategoryMap, projectIdToCategoryIdToServer, orgUserServerPrivileges);

        return orgInfo;
    }

    private OrgInfo assembleOrgInfo(Map<Integer, String> fMap, Map<Integer, Map<String, List<String>>> sMap, Map<Integer, Map<String, List<OrgServer>>> tMap, List<Integer> orgUserServerPrivileges){
        Map<Integer, String> projectIdToProjectName = fMap;
        Map<Integer, Map<String, List<String>>> projectIdToCategoryMap = sMap;
        Map<Integer, Map<String, List<OrgServer>>> projectIdToCategoryIdToServer = tMap;

        List<OrgProject> orgProjectList = new ArrayList<>();

        projectIdToProjectName.forEach((projectId,projectName)->{
            Map<String, List<String>> category = projectIdToCategoryMap.get(projectId);
            Map<String, List<OrgServer>> project_server = projectIdToCategoryIdToServer.get(projectId);

            OrgProject orgProject = OrgProject.builder()
                    .project_id(projectId)
                    .project_name(projectName)
                    .category(category)
                    .project_server(project_server)
                    .build();

            orgProjectList.add(orgProject);
        });

        OrgInfo orgInfo = OrgInfo.builder()
                .projects(orgProjectList)
                .org_user_privileges(orgUserServerPrivileges)
                .build();

        return orgInfo;
    }

    public OrgMembers getOrgMembers(Integer org_id) throws SQLException{
        List<OrgContributor> contributors = orgMasterMapper.getOrgContributors(org_id);
        Contributor admin_contributor = new Contributor();
        List<Contributor> normal_contributors = new ArrayList<>();
        List<Contributor> pending_contributors = new ArrayList<>();
        for(OrgContributor orgContributor:contributors){
            String state = orgContributor.getState();
            Contributor contributor = Contributor.builder()
                    .user_id(orgContributor.getContributor_id())
                    .contributor_email(orgContributor.getContributor_email())
                    .build();

            if(state.equals("admin")){
                admin_contributor = contributor;
            }
            else if(state.equals("contributor")){
                normal_contributors.add(contributor);
            }
            else if(state.equals("pending")){
                pending_contributors.add(contributor);
            }
        }
        OrgMembers orgMembers = OrgMembers.builder()
                .admin_contributor(admin_contributor)
                .contributors(normal_contributors)
                .pending_contributors(pending_contributors)
                .build();

        return orgMembers;
    }

    public Integer getOrgAdmin(Integer org_id) throws SQLException{
        return orgMasterMapper.getOrgAdmin(org_id);
    }
}
