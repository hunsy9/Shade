package com.oslab.agent.model.transfer.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DelCategoryDto {
    private Long org_id;
    private Long project_id;
    private List<String> categoryKeys;
    private List<Long> targetServerList;
    private String type;
}
