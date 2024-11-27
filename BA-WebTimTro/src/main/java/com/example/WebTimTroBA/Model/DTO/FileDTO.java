package com.example.WebTimTroBA.Model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FileDTO {

    private Integer id;

    private String name;

    private String fileUrl;

    private String fileId;

}
