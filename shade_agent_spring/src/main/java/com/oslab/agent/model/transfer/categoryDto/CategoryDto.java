package com.oslab.agent.model.transfer.categoryDto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @Nullable
    private Long org_id;
    @Nullable
    private Long project_id;
    private String prev;
    @Nullable
    private String next;
    private Long category_id;
}
