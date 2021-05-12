package com.emosoft.cloud.filesharing.services.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
public class FilePropertiesServiceModel {
    @Getter
    @Setter
    private String fname;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private Long size;
}
