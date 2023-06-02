package com.oslab.agent.model.transfer.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long org_id;
    private Long project_id;
    private String prev;
    private String next;
}
