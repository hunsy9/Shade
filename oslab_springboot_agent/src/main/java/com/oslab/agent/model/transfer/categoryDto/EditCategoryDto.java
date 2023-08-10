package com.oslab.agent.model.transfer.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCategoryDto {
    private List<String> categoryKeys;
    private String prev;
    private String next;
    private String type;
}
