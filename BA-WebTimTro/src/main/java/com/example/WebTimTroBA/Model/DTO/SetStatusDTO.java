package com.example.WebTimTroBA.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SetStatusDTO {
    private Integer status;
    private List<Integer> id;
}
