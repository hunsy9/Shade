package com.oslab.agent.model.transfer.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EditCategory {
    private Long category_id;
    private String prev;
    private String next;
}
