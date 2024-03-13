package com.oslab.cmanager.model.transfer.containerDto;

import lombok.*;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Container {
    private String container_name;
    private String container_id;
    private File key_file;
}
